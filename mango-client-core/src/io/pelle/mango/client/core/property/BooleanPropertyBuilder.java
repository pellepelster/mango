package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;

public class BooleanPropertyBuilder extends BasePropertyBuilder<Boolean> {

	BooleanPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	@Override
	public Boolean parseValue(String valueString) {
		return Boolean.parseBoolean(valueString);
	}

}
