package io.pelle.mango.client.base.vo;

public class IntegerAttributeDescriptor extends BaseNumberAttributeDescriptor<Integer> {

	public IntegerAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, Integer.class);
	}

}