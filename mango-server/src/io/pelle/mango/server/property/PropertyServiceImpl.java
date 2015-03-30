package io.pelle.mango.server.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.dao.IBaseEntityDAO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.google.common.base.Optional;

public class PropertyServiceImpl implements IPropertyService {

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

		switch (property.getType()) {
		case SYSTEM:
			System.setProperty(property.getKey(), property.toString(value));
			return;
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
			return;

		default:
			throw new RuntimeException("unsupported property type '" + property.getType() + "'");
		}
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

		return null;
	}

}
