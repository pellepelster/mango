package io.pelle.mango.server.property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

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

@Transactional
public class PropertyServiceImpl implements IPropertyService, InitializingBean {

	public CloseableHttpClient httpclient = HttpClients.createDefault();

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

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Autowired
	private Environment environment;

	public String getSpringProperty(String key) {
		return environment.getProperty(key);
	}

	@Override
	@Cacheable(value = "properties", key = "#property.key")
	public <VALUETYPE extends Serializable> VALUETYPE getProperty(IProperty<VALUETYPE> property) {

		VALUETYPE result = getValueInternal(property);

		if (result != null) {
			return result;
		}

		for (IProperty<VALUETYPE> fallback : property.getFallbacks()) {
			result = getValueInternal(fallback);

			if (result != null) {
				return result;
			}
		}

		return getPropertyDefault(property);

	}

	private <VALUETYPE extends Serializable> VALUETYPE getValueInternal(IProperty<VALUETYPE> property) {

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
		} else {
			return null;
		}
	}

	@Override
	@CacheEvict(value = "properties", key = "#property.key", allEntries = true)
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

		VALUETYPE result = getPropertyDefaultInternal(property);

		if (result != null) {
			return result;
		}

		for (IProperty<VALUETYPE> fallback : property.getFallbacks()) {
			result = getPropertyDefaultInternal(fallback);

			if (result != null) {
				return result;
			}
		}

		return null;
	}

	private <VALUETYPE extends Serializable> VALUETYPE getPropertyDefaultInternal(IProperty<VALUETYPE> property) {
		if (property.getDefaultValue() != null) {
			return property.getDefaultValue();
		} else if (property.getDefaultProperty() != null) {
			return getProperty(property.getDefaultProperty());
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

		if (getProperty(ConfigurationParameters.GRAPHITE_EVENTSAPI_URL) != null) {
			ObjectMapper mapper = new ObjectMapper();

			String graphiteUrl = getProperty(ConfigurationParameters.GRAPHITE_EVENTSAPI_URL);
			String graphiteEventString = null;
			try {
				graphiteEventString = mapper.writeValueAsString(graphiteEvent);

				HttpPost httpPost = new HttpPost(graphiteUrl);

				RequestConfig requestConfig = RequestConfig.copy(httpPost.getConfig()).setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
				httpPost.setConfig(requestConfig);

				httpPost.setEntity(new StringEntity(graphiteEventString, ContentType.TEXT_PLAIN));

				HttpResponse response = httpclient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() != 200) {
					LOG.error("error sending property change event to graphite (" + IOUtils.toString(response.getEntity().getContent()) + ")");
				}

			} catch (Exception e) {
				LOG.error("error sending property change event to graphite (" + graphiteEventString + ")", e);
			}
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
