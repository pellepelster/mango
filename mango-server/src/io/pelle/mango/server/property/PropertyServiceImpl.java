package io.pelle.mango.server.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.util.MessageFormat;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.core.property.PropertyBuilder;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.ConfigurationParameters;
import io.pelle.mango.server.Messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.scheduling.annotation.Async;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

@Transactional
public class PropertyServiceImpl implements IPropertyService, InitializingBean {

	public static final String PROPERTY_CHANGED_TAG = "property-changed";

	public static final String PROPERTY_TAG = "property";

	public static final String PROPERTY_CHANGED_MESSAGE = "property.changed.message";

	public static final String PROPERTY_HUMAN_NAME_KEY = "propertyHumanName";

	public static final String PROPERTY_OLD_VALUE_KEY = "oldValue";

	public static final String PROPERTY_NEW_VALUE_KEY = "newValue";

	private static final int TIMEOUT = 5 * 1000;

	private class GraphiteEvent {

		private String what;

		private String tags;

		public GraphiteEvent(String what, String[] tags) {
			super();
			this.what = what;
			this.tags = Joiner.on(" ").join(tags);
		}

		public String getWhat() {
			return what;
		}

		public void setWhat(String what) {
			this.what = what;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

	}

	private static final Logger LOG = Logger.getLogger(PropertyServiceImpl.class);

	private static final String PROPERTY_METRIC_VALUE_KEY = "value";

	private static final String PROPERTY_METRIC_DEFAULT_KEY = "default";

	private static final String PROPERTY_METRIC_NAMESPACE = "property";

	@Autowired
	private MetricRegistry metricRegistry;

	private final Map<String, String> cache = new ConcurrentHashMap<String, String>();

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Autowired
	private AbstractBeanFactory beanFactory;

	public String getSpringProperty(String key) {

		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		String value = null;
		try {
			value = beanFactory.resolveEmbeddedValue("${" + key.trim() + "}");

			if (value != null) {
				cache.put(key, value);
			}
		} catch (IllegalArgumentException e) {
			// ignore non existant values
		}

		return value;
	}

	@Override
	public <VALUETYPE extends Serializable> VALUETYPE getProperty(IProperty<VALUETYPE> property) {

		String valueString = null;

		switch (property.getType()) {
		case SYSTEM:
			valueString = System.getProperty(property.getKey());
			break;
		case DATABASE:

			Optional<PropertyValue> dbProperty = baseEntityDAO.read(SelectQuery.selectFrom(PropertyValue.class).where(PropertyValue.KEY.eq(property.getKey())));

			if (dbProperty.isPresent()) {
				valueString = dbProperty.get().getValue();
			}
			break;
		case SPRING:
			valueString = getSpringProperty(property.getKey());
			break;

		default:
			throw new RuntimeException("unsupported property type '" + property.getType() + "'");
		}

		if (valueString != null && !valueString.trim().isEmpty()) {
			return property.parseValue(valueString);
		}

		if (property.getFallback() != null) {
			return getProperty(property.getFallback());
		} else {
			return getPropertyDefault(property);
		}

	}

	@Override
	public <VALUETYPE extends Serializable> void setProperty(IProperty<VALUETYPE> property, VALUETYPE value) {

		VALUETYPE oldValue = getProperty(property);

		switch (property.getType()) {
		case SYSTEM:
			System.setProperty(property.getKey(), property.toString(value));
			break;
		case DATABASE:

			Optional<PropertyValue> dbProperty = baseEntityDAO.read(SelectQuery.selectFrom(PropertyValue.class).where(PropertyValue.KEY.eq(property.getKey())));

			if (dbProperty.isPresent()) {
				dbProperty.get().setValue(property.toString(value));
				baseEntityDAO.save(dbProperty.get());
			} else {
				PropertyValue propertyValue = new PropertyValue();
				propertyValue.setKey(property.getKey());
				propertyValue.setValue(property.toString(value));
				baseEntityDAO.create(propertyValue);
			}
			break;

		default:
			throw new RuntimeException("unsupported property type '" + property.getType() + "'");
		}

		String message = MessageFormat.format(Messages.getString(PROPERTY_CHANGED_MESSAGE), CollectionUtils.getMap(PROPERTY_HUMAN_NAME_KEY, property.getHumanName(), PROPERTY_OLD_VALUE_KEY, oldValue, PROPERTY_NEW_VALUE_KEY, value));
		GraphiteEvent graphiteEvent = new GraphiteEvent(message, new String[] { PROPERTY_CHANGED_TAG, PROPERTY_TAG });
		sendGraphiteEvent(graphiteEvent);
	}

	@Override
	public Map getPropertyValues(IPropertyCategory propertyCategory) {
		Map<IProperty<?>, Object> result = new HashMap<IProperty<?>, Object>();

		for (IProperty<?> property : propertyCategory.getAllProperties()) {
			result.put(property, getProperty(property));
		}

		return result;
	}

	@Override
	public <VALUETYPE extends Serializable> VALUETYPE getPropertyDefault(IProperty<VALUETYPE> property) {

		if (property.getDefaultValue() != null) {
			return property.getDefaultValue();
		} else if (property.getDefaultProperty() != null) {
			return getProperty(property.getDefaultProperty());
		}

		if (property.getFallback() != null) {
			return getPropertyDefault(property.getFallback());
		}

		return null;
	}

	private <T extends Serializable> void publishProperty(final IProperty<T> property) {

		metricRegistry.register(MetricRegistry.name(PROPERTY_METRIC_NAMESPACE, property.getKey(), PROPERTY_METRIC_VALUE_KEY), new Gauge<T>() {
			@Override
			public T getValue() {
				return (T) getProperty(property);
			}
		});

		metricRegistry.register(MetricRegistry.name(PROPERTY_METRIC_NAMESPACE, property.getKey(), PROPERTY_METRIC_DEFAULT_KEY), new Gauge<T>() {
			@Override
			public T getValue() {
				return (T) getPropertyDefault(property);
			}
		});
	}

	@Async
	private void sendGraphiteEvent(GraphiteEvent graphiteEvent) {

		ObjectMapper mapper = new ObjectMapper();

		String graphiteUrl = String.format("http://%s:%d/events/", getProperty(ConfigurationParameters.GRAPHITE_EVENTSAPI_HOST), getProperty(ConfigurationParameters.GRAPHITE_EVENTSAPI_PORT));
		String graphiteEventString = null;
		try {
			graphiteEventString = mapper.writeValueAsString(graphiteEvent);

			HttpResponse response = Request.Post(graphiteUrl).connectTimeout(TIMEOUT).socketTimeout(TIMEOUT).bodyString(graphiteEventString, ContentType.TEXT_PLAIN).execute().returnResponse();

			if (response.getStatusLine().getStatusCode() != 200) {
				LOG.error("error sending property change event to graphite (" + IOUtils.toString(response.getEntity().getContent()) + ")");
			}

		} catch (Exception e) {
			LOG.error("error sending property change event to graphite (" + graphiteEventString + ")", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		PropertyBuilder.getInstance().addPropertyCallback(new AsyncCallback<IProperty<?>>() {

			@Override
			public void onSuccess(final IProperty<?> property) {
				switch (property.getValueType()) {
				case INTEGER:
				case BOOLEAN:
					publishProperty(property);
					break;
				default:
					break;
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});

	}

}
