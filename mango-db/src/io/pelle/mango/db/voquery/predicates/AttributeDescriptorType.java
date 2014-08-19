package io.pelle.mango.db.voquery.predicates;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;

import com.google.common.base.Predicate;

public class AttributeDescriptorType implements Predicate<IAttributeDescriptor<?>> {

	private Class<?> clazz;

	AttributeDescriptorType(Class<?> clazz) {
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
