package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.expressions.PathExpression;

public abstract class BaseExpressionAttributeDescriptor<T> extends AttributeDescriptor<T> {

	protected final PathExpression entityFieldExpression;

	public BaseExpressionAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> attributeType, Class<?> attributeListType,
			int naturalKeyOrder) {
		super(entityDescriptor, attributeName, attributeType, attributeListType, false, naturalKeyOrder);
		entityFieldExpression = new PathExpression(entityDescriptor.getVOEntityClass().getName(), attributeName);
	}

	public BaseExpressionAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> attributeType, int naturalKeyOrder) {
		this(entityDescriptor, attributeName, attributeType, attributeType, naturalKeyOrder);
	}

}