package io.pelle.mango.server.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.dao.IBaseEntityDAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

public class PropertyServiceImpl implements IPropertyService {

	// public static Predicate<PropertyCategory> categoryByName(final String
	// categoryName) {
	// return new Predicate<PropertyCategory>() {
	// @Override
	// public boolean apply(PropertyCategory input) {
	// return Objects.equal(categoryName, input.getName());
	// }
	// };
	// }
	//
	// public static Predicate<PropertyDefinition> propertyByName(final String
	// propertyKey) {
	// return new Predicate<PropertyDefinition>() {
	// @Override
	// public boolean apply(PropertyDefinition input) {
	// return Objects.equal(propertyKey, input.getKey());
	// }
	// };
	// }

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	// private void updateValues(List<PropertyCategory> categories) {
	//
	// for (PropertyCategory category : categories) {
	// updatePropertyValues(category.getPropertyDefinitions());
	// }
	// }
	//
	// private void updatePropertyValues(List<PropertyDefinition> properties) {
	//
	// for (PropertyDefinition property : properties) {
	// property.setValue(getPropertyValue(property));
	// }
	// }
	//
	// public List<PropertyCategory> getRootPropertyCategory() {
	//
	// List<PropertyCategory> rootCategories =
	// PropertiesBuilder.getInstance().getRootCategories();
	//
	// updateValues(rootCategories);
	//
	// return rootCategories;
	// }

	@Override
	public <VALUETYPE> VALUETYPE getProperty(IProperty<VALUETYPE> property) {

		VALUETYPE value = null;

		switch (property.getType()) {
		case SYSTEM:
			value = property.parseValue(System.getProperty(property.getKey()));
			break;
		case DATABASE:

			Optional<PropertyValue> dbProperty = baseEntityDAO.read(SelectQuery.selectFrom(PropertyValue.class).where(PropertyValue.KEY.eq(property.getKey())));

			if (dbProperty.isPresent()) {
				value = property.parseValue(dbProperty.get().getValue());
			}
			break;

		default:
			throw new RuntimeException("unsupported property type '" + property.getType() + "'");
		}

		if (value == null) {
			if (property.getFallback() != null) {
				return getProperty(property.getFallback());
			} else if (property.getDefaultValue() != null) {
				return property.getDefaultValue();
			}
		}

		return value;
	}

	@Override
	public <VALUETYPE> void setProperty(IProperty<VALUETYPE> property, VALUETYPE value) {

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

}
