package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.IBooleanExpression;

import com.google.common.base.Optional;

public interface IAttributeDescriptor<AttributeType> extends IMetaDescriptor<AttributeType> {

	String getAttributeName();

	Class<?> getAttributeType();

	Class<?> getListAttributeType();

	boolean isMandatory();

	int getNaturalKeyOrder();

	<T> T path(T attributeDescriptor);

	IBooleanExpression eq(AttributeType value);

	Optional<IBooleanExpression> search(String value);
}
