package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;

public class StringPropertyBuilder extends BasePropertyBuilder<String> {

	StringPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	@Override
	public String parseValue(String valueString) {
		return valueString;
	}

}
