package io.pelle.mango.client.base.vo;


public class BooleanAttributeDescriptor extends BaseExpressionAttributeDescriptor<String> {

	public BooleanAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName, Class<?> collectionType) {
		super(entityDescriptor, attributeName, collectionType, String.class, AttributeDescriptor.NO_NATURAL_KEY);
	}

	public BooleanAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, String.class, AttributeDescriptor.NO_NATURAL_KEY);
	}

}