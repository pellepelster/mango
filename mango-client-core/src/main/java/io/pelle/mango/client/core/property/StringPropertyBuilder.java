package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.base.property.PROPERTY_VALUE_TYPE;

@SuppressWarnings("serial")
public class StringPropertyBuilder extends BasePropertyBuilder<String, StringPropertyBuilder> {

	public StringPropertyBuilder() {
		super();
	}

	public StringPropertyBuilder(String key) {
		super(key);
	}

	public StringPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	@Override
	public String parseValue(String valueString) {
		return valueString;
	}

	@Override
	public PROPERTY_VALUE_TYPE getValueType() {
		return PROPERTY_VALUE_TYPE.STRING;
	}

	@Override
	protected StringPropertyBuilder getBuilder() {
		return this;
	}

	@Override
	protected StringPropertyBuilder clone(String key) {
		StringPropertyBuilder result = new StringPropertyBuilder((key != null) ? key : getKey(), getName(), getType());
		return result;
	}

}
