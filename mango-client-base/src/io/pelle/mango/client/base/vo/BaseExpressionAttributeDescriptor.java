package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;

@SuppressWarnings("serial")
public abstract class BaseExpressionAttributeDescriptor<T> extends AttributeDescriptor<T> {

	protected final PathExpression entityFieldExpression;

	public BaseExpressionAttributeDescriptor(IMetaDescriptor parent, String attributeName, Class<?> attributeType, Class<?> attributeListType, int naturalKeyOrder) {
		super(parent, attributeName, attributeType, attributeListType, false, naturalKeyOrder);

		String currentAttributeName = "";

		IAttributeDescriptor<?> currentAttributeDescriptor = this;
		IEntityDescriptor<?> entityDescriptor = null;
		String delimiter = "";

		while (entityDescriptor == null) {

			currentAttributeName = currentAttributeDescriptor.getAttributeName() + delimiter + currentAttributeName;
			delimiter = ".";

			if (currentAttributeDescriptor.getParent() instanceof IEntityDescriptor) {
				entityDescriptor = (IEntityDescriptor<?>) currentAttributeDescriptor.getParent();
			} else if (currentAttributeDescriptor.getParent() instanceof IAttributeDescriptor) {
				currentAttributeDescriptor = (IAttributeDescriptor<?>) currentAttributeDescriptor.getParent();
			}

		}

		entityFieldExpression = new PathExpression(entityDescriptor.getVOEntityClass().getName(), currentAttributeName);
	}

	public BaseExpressionAttributeDescriptor(IMetaDescriptor parent, String attributeName, Class<?> attributeType, int naturalKeyOrder) {
		this(parent, attributeName, attributeType, attributeType, naturalKeyOrder);
	}
	
	public IBooleanExpression isNull() {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.IS_NULL, new StringExpression(null));
	}

	public IBooleanExpression notNull() {
		return new CompareExpression(entityFieldExpression, ComparisonOperator.NOT_NULL, new StringExpression(null));
	}



}