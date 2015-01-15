package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;

public class IntegerPropertyBuilder extends BasePropertyBuilder<Integer> {

	IntegerPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	@Override
	public Integer parseValue(String valueString) {
		return Integer.parseInt(valueString);
	}

}