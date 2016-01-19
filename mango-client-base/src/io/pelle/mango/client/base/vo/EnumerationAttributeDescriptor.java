package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

@SuppressWarnings("serial")
public class EnumerationAttributeDescriptor<ENUM_TYPE> extends BaseExpressionAttributeDescriptor<ENUM_TYPE> implements IAttributeDescriptor<ENUM_TYPE> {

	public EnumerationAttributeDescriptor(IMetaDescriptor<?> parent, String attributeName, Class<?> attributeType, Class<?> collectionType, int naturalKeyOrder) {
		super(parent, attributeName, collectionType, attributeType, naturalKeyOrder);
	}

	@Override
	public IBooleanExpression eq(ENUM_TYPE value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new StringExpression(value.toString()));
	}

	public IBooleanExpression neq(ENUM_TYPE value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.NOT_EQUALS, new StringExpression(value.toString()));
	}

	protected EnumerationAttributeDescriptor<ENUM_TYPE> cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		EnumerationAttributeDescriptor<ENUM_TYPE> clone = new EnumerationAttributeDescriptor<ENUM_TYPE>(parentAttributeDescriptor, getAttributeName(), getAttributeType(), getListAttributeType(), getNaturalKeyOrder());
		return clone;
	}

}