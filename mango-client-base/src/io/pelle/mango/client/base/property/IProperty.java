package io.pelle.mango.client.base.property;

public interface IProperty<VALUETYPE> {

	PROPERTY_TYPE getType();

	String getKey();

	IProperty<VALUETYPE> getFallback();

	VALUETYPE getDefaultValue();

	VALUETYPE parseValue(String valueString);

	String toString(VALUETYPE value);

	String getName();
}
