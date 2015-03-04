package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.BooleanValueExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;

@SuppressWarnings("serial")
public class BooleanAttributeDescriptor extends BaseExpressionAttributeDescriptor<Boolean> {

	public BooleanAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType) {
		super(entityDescriptor, attributeName, collectionType, Boolean.class, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public BooleanAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, Boolean.class, AttributeDescriptor.NO_NATURAL_KEY);
	}
	
	public IBooleanExpression eq(Boolean value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new BooleanValueExpression(value));
	}

}