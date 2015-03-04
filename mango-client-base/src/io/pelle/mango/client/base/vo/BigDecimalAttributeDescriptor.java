package io.pelle.mango.client.base.vo;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class BigDecimalAttributeDescriptor extends BaseNumberAttributeDescriptor<BigDecimal> {

	public BigDecimalAttributeDescriptor(IMetaDescriptor metaDescriptor, String attributeName) {
		super(metaDescriptor, attributeName, BigDecimal.class);
	}

	protected BigDecimalAttributeDescriptor cloneWithNewParent(IAttributeDescriptor<?> parentAttributeDescriptor) {
		BigDecimalAttributeDescriptor clone = new BigDecimalAttributeDescriptor(parentAttributeDescriptor, getAttributeName());
		return clone;
	}

}