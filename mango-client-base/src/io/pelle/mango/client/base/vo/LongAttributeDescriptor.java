package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.CompareExpression;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.LongExpression;

public class LongAttributeDescriptor extends BaseExpressionAttributeDescriptor<Long> {

	public LongAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, Long.class);
	}

	public IBooleanExpression eq(long value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new LongExpression(value));
	}

}