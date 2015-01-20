package io.pelle.mango.client.base.property;

import java.io.Serializable;

public interface IProperty<VALUETYPE extends Serializable> {

	PROPERTY_TYPE getType();

	PROPERTY_VALUE_TYPE getValueType();

	String getKey();

	IProperty<VALUETYPE> getFallback();

	VALUETYPE getDefaultValue();

	VALUETYPE parseValue(String valueString);

	String toString(VALUETYPE value);

	String getName();

}
