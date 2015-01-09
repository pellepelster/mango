package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.NumberExpression;

public class BaseNumberAttributeDescriptor<T extends Number> extends BaseExpressionAttributeDescriptor<T> {

	public BaseNumberAttributeDescriptor(IMetaDescriptor parent, String attributeName, Class<T> numberClass) {
		super(parent, attributeName, numberClass, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public IBooleanExpression eq(T value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new NumberExpression(value));
	}

	public IBooleanExpression lessThan(T value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.LESS, new NumberExpression(value));
	}

	public IBooleanExpression greaterThan(T value) {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.GREATER, new NumberExpression(value));
	}

}