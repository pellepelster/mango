package io.pelle.mango.client.base.property;

import java.io.Serializable;
import java.util.List;

public interface IProperty<VALUETYPE extends Serializable> {

	PROPERTY_TYPE getType();

	PROPERTY_VALUE_TYPE getValueType();

	String getKey();

	List<IProperty<VALUETYPE>> getFallbacks();

	VALUETYPE getDefaultValue();

	IProperty<VALUETYPE> getDefaultProperty();

	VALUETYPE parseValue(String valueString);

	String toString(VALUETYPE value);

	String getName();

	String getHumanName();
}
