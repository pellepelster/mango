package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.base.property.PROPERTY_VALUE_TYPE;

public class BooleanPropertyBuilder extends BasePropertyBuilder<Boolean> {

	public BooleanPropertyBuilder() {
	}

	public BooleanPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}
	
	public BooleanPropertyBuilder(String key, PROPERTY_TYPE type) {
		super(key, type);
	}
	
	@Override
	public Boolean parseValue(String valueString) {
		return Boolean.parseBoolean(valueString);
	}

	@Override
	public PROPERTY_VALUE_TYPE getValueType() {
		return PROPERTY_VALUE_TYPE.BOOLEAN;
	}

}
