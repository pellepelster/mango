package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.base.property.PROPERTY_VALUE_TYPE;

public class IntegerPropertyBuilder extends BasePropertyBuilder<Integer> {

	public IntegerPropertyBuilder() {
	}

	public IntegerPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	public IntegerPropertyBuilder(String key, PROPERTY_TYPE type) {
		super(key, type);
	}
	
	@Override
	public Integer parseValue(String valueString) {
		return Integer.parseInt(valueString);
	}

	@Override
	public PROPERTY_VALUE_TYPE getValueType() {
		return PROPERTY_VALUE_TYPE.INTEGER;
	}

}
