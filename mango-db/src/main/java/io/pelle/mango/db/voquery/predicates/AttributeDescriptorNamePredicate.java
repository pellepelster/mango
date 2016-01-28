package io.pelle.mango.db.voquery.predicates;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;

import com.google.common.base.Predicate;

public class AttributeDescriptorNamePredicate implements Predicate<IAttributeDescriptor<?>> {

	private String name;

	public AttributeDescriptorNamePredicate(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean apply(IAttributeDescriptor<?> attributeDescriptor) {

		if (attributeDescriptor.getAttributeName().toLowerCase().equals(name.toLowerCase())) {
			return true;
		}

		return false;
	}
}
