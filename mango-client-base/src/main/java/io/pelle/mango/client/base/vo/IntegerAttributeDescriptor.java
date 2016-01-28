package io.pelle.mango.client.base.vo;

@SuppressWarnings("serial")
public class IntegerAttributeDescriptor extends BaseNumberAttributeDescriptor<Integer> {

	public IntegerAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, Integer.class);
	}

}