package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LikeExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

public class StringAttributeDescriptor extends BaseExpressionAttributeDescriptor<String> {

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType) {
		super(entityDescriptor, attributeName, collectionType, String.class);
	}

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, String.class);
	}

	public IBooleanExpression eq(String value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new StringExpression(value));
	}

	public IBooleanExpression eqIgnoreCase(String value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS_NO_CASE, new StringExpression(value));
	}

	public IBooleanExpression caseInsensitiveLike(String value) {
		return new LikeExpression(entityFieldExpression, new StringExpression(value), true);
	}

	public IBooleanExpression like(String value) {
		return new LikeExpression(entityFieldExpression, new StringExpression(value), false);
	}

}