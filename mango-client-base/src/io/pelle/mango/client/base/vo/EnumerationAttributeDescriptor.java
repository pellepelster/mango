package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

public class EnumerationAttributeDescriptor<ENUM_TYPE> extends BaseExpressionAttributeDescriptor<String> implements IAttributeDescriptor<String> {

	public EnumerationAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> attributeType, Class<?> collectionType, int naturalKeyOrder) {
		super(entityDescriptor, attributeName, collectionType, attributeType, naturalKeyOrder);
	}

	public IBooleanExpression eq(ENUM_TYPE value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new StringExpression(value.toString()));
	}

	public IBooleanExpression neq(ENUM_TYPE value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.NOT_EQUALS, new StringExpression(value.toString()));
	}

}