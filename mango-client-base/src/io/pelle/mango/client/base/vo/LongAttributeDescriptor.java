package io.pelle.mango.client.base.vo;

@SuppressWarnings("serial")
public class LongAttributeDescriptor extends BaseNumberAttributeDescriptor<Long> {

	public LongAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName) {
		super(metaDescriptor, attributeName, Long.class);
	}

	public LongAttributeDescriptor cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		LongAttributeDescriptor clone = new LongAttributeDescriptor(parentAttributeDescriptor, getAttributeName());
		return clone;
	}

}