package io.pelle.mango.client.base.vo;

public class LongAttributeDescriptor extends BaseNumberAttributeDescriptor<Long> {

	public LongAttributeDescriptor(IEntityDescriptor<?> entityDescriptor, String attributeName) {
		super(entityDescriptor, attributeName, Long.class);
	}

}