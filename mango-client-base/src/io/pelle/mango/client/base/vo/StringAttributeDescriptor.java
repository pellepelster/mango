package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LikeExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

public class StringAttributeDescriptor extends BaseExpressionAttributeDescriptor<String> implements IAttributeDescriptor<String> {

	private final int maxLength;

	public static final int LENGTH_UNLIMITED = -1;
	
	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		this(entityDescriptor, attributeName, LENGTH_UNLIMITED);
	}
	
	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType) {
		this(entityDescriptor, attributeName, collectionType, LENGTH_UNLIMITED, NO_NATURAL_KEY);
	}

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType, int maxLength, int naturalKeyOrder) {
		super(entityDescriptor, attributeName, collectionType, String.class, naturalKeyOrder);
		this.maxLength = maxLength;
	}

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, int maxLength) {
		this(entityDescriptor, attributeName, String.class, maxLength, NO_NATURAL_KEY);
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

	public int getMaxLength() {
		return maxLength;
	}

}