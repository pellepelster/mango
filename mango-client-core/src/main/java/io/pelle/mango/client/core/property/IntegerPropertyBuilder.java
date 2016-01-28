package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;
import io.pelle.mango.client.base.property.PROPERTY_VALUE_TYPE;

@SuppressWarnings("serial")
public class IntegerPropertyBuilder extends BasePropertyBuilder<Integer, IntegerPropertyBuilder> {

	public IntegerPropertyBuilder() {
		super();
	}

	public IntegerPropertyBuilder(String key) {
		super(key);
	}

	public IntegerPropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super(key, name, type);
	}

	@Override
	public Integer parseValue(String valueString) {
		return Integer.parseInt(valueString);
	}

	@Override
	public PROPERTY_VALUE_TYPE getValueType() {
		return PROPERTY_VALUE_TYPE.INTEGER;
	}

	@Override
	protected IntegerPropertyBuilder getBuilder() {
		return this;
	}

	@Override
	protected IntegerPropertyBuilder clone(String key) {
		return new IntegerPropertyBuilder((key != null) ? key : getKey(), getName(), getType());
	}

}
