package io.pelle.mango.client.base.vo;

public class LongAttributeDescriptor extends BaseNumberAttributeDescriptor<Long> {

	public LongAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName) {
		super(metaDescriptor, attributeName, Long.class);
	}

	protected LongAttributeDescriptor cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		LongAttributeDescriptor clone = new LongAttributeDescriptor(parentAttributeDescriptor, getAttributeName());
		return clone;
	}

}