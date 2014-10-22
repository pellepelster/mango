package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LikeExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

public class StringAttributeDescriptor extends BaseExpressionAttributeDescriptor<String> implements IAttributeDescriptor<String> {

	private final int maxLength;

	private final int minLength;

	public static final int NO_LENGTH_LIMIT = -1;
	
	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		this(entityDescriptor, attributeName, NO_LENGTH_LIMIT);
	}
	
	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType) {
		this(entityDescriptor, attributeName, collectionType, NO_LENGTH_LIMIT, NO_LENGTH_LIMIT, NO_NATURAL_KEY);
	}

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType, int minLength, int maxLength, int naturalKeyOrder) {
		super(entityDescriptor, attributeName, collectionType, String.class, naturalKeyOrder);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public StringAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, int maxLength) {
		this(entityDescriptor, attributeName, String.class, NO_LENGTH_LIMIT, maxLength, NO_NATURAL_KEY);
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

	public int getMinLength() {
		return minLength;
	}
}