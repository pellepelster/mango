package io.pelle.mango.db.vo;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;

import com.google.common.base.Predicate;

public class AttributeDescriptorAttributeTypePredicate implements Predicate<IAttributeDescriptor<?>> {

	private Class<?> clazz;

	AttributeDescriptorAttributeTypePredicate(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public boolean apply(IAttributeDescriptor<?> attributeDescriptor) {

		if (clazz.isAssignableFrom(attributeDescriptor.getAttributeType())) {
			return true;
		}

		return false;
	}
}
