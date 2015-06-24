package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.EntityCompareExpression;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.NumberExpression;

@SuppressWarnings("serial")
public class EntityAttributeDescriptor<T extends IVOEntity> extends BaseExpressionAttributeDescriptor<T> {

	public EntityAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName, Class<T> entityType, int naturalKeyOrder) {
		super(metaDescriptor, attributeName, entityType, entityType, naturalKeyOrder);
	}

	public EntityAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName, Class<T> entityType) {
		super(metaDescriptor, attributeName, entityType, entityType, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public EntityAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName) {
		super(metaDescriptor, attributeName, String.class, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public EntityAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName, int naturalKeyOrder) {
		super(metaDescriptor, attributeName, String.class, naturalKeyOrder);
	}

	@Override
	public IBooleanExpression eq(T value) {
		return new EntityCompareExpression(entityFieldExpression, ComparisonOperator.EQUALS, new NumberExpression(value.getId()));
	}

	protected EntityAttributeDescriptor cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		EntityAttributeDescriptor clone = new EntityAttributeDescriptor(parentAttributeDescriptor, getAttributeName(), getNaturalKeyOrder());
		return clone;
	}
}