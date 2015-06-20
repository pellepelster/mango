package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.IBooleanExpression;

public interface IAttributeDescriptor<AttributeType> extends IMetaDescriptor {

	String getAttributeName();

	Class<?> getAttributeType();

	Class<?> getListAttributeType();

	boolean isMandatory();

	int getNaturalKeyOrder();

	<T> T path(T attributeDescriptor);

	IBooleanExpression eq(AttributeType value);

}
