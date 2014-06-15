package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.PathExpression;

public abstract class BaseExpressionAttributeDescriptor<T> extends AttributeDescriptor<T> {

	protected final PathExpression entityFieldExpression;

	public BaseExpressionAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> attributeType, Class<?> attributeListType) {
		super(entityDescriptor, attributeName, attributeType, attributeListType);
		entityFieldExpression = new PathExpression(entityDescriptor.getVOEntityClass(), attributeName);
	}

	public BaseExpressionAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> attributeType) {
		this(entityDescriptor, attributeName, attributeType, attributeType);
	}

}