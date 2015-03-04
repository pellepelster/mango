package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LikeExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

@SuppressWarnings("serial")
public class StringAttributeDescriptor extends BaseExpressionAttributeDescriptor<String> {

	private final int maxLength;

	private final int minLength;

	public static final int NO_LENGTH_LIMIT = -1;

	public StringAttributeDescriptor(IMetaDescriptor parent, String attributeName) {
		this(parent, attributeName, NO_LENGTH_LIMIT);
	}

	public StringAttributeDescriptor(IMetaDescriptor parent, String attributeName, Class<?> collectionType) {
		this(parent, attributeName, collectionType, NO_LENGTH_LIMIT, NO_LENGTH_LIMIT, NO_NATURAL_KEY);
	}

	public StringAttributeDescriptor(IMetaDescriptor parent, String attributeName, Class<?> collectionType, int minLength, int maxLength, int naturalKeyOrder) {
		super(parent, attributeName, collectionType, String.class, naturalKeyOrder);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public StringAttributeDescriptor(IMetaDescriptor parent, String attributeName, int maxLength) {
		this(parent, attributeName, String.class, NO_LENGTH_LIMIT, maxLength, NO_NATURAL_KEY);
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

	protected StringAttributeDescriptor cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		StringAttributeDescriptor clone = new StringAttributeDescriptor(parentAttributeDescriptor, getAttributeName(), getListAttributeType(), minLength, maxLength, getNaturalKeyOrder());
		return clone;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

}