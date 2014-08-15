package io.pelle.mango.db.vo;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;

public class AttributeDescriptorAnnotation<T> {

	private final T annotation;

	private final IAttributeDescriptor attributeDescriptor;

	public AttributeDescriptorAnnotation(IAttributeDescriptor attributeDescriptor, T annotation) {
		super();
		this.attributeDescriptor = attributeDescriptor;
		this.annotation = annotation;
	}

	public T getAnnotation() {
		return annotation;
	}

	public IAttributeDescriptor getAttributeDescriptor() {
		return attributeDescriptor;
	}

}
