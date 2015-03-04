package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.EntityCompareExpression;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.NumberExpression;

@SuppressWarnings("serial")
public class EntityAttributeDescriptor<T extends IVOEntity> extends BaseExpressionAttributeDescriptor<T> {

	public EntityAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<T> entityType) {
		super(entityDescriptor, attributeName, entityType, entityType, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public EntityAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, String.class, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public IBooleanExpression eq(T value) {
		return new EntityCompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new NumberExpression(value.getId()));
	}
}