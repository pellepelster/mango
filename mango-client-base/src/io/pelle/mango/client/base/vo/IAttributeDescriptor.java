package io.pelle.mango.client.base.vo;

public interface IAttributeDescriptor<AttributeType> {

	IEntityDescriptor<?> getParent();

	String getAttributeName();

	Class<?> getAttributeType();

	Class<?> getListAttributeType();

	boolean isMandatory();
	
	int getNaturalKeyOrder();
	
}
