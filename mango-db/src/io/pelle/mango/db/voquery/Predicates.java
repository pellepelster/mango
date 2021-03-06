package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.db.voquery.predicates.AttributeDescriptorNamePredicate;
import io.pelle.mango.db.voquery.predicates.AttributeEqualsPredicate;

public class Predicates {

	public static AttributeDescriptorNamePredicate attributeDescriptorByName(String name) {
		return new AttributeDescriptorNamePredicate(name);
	}

	public static <T> AttributeEqualsPredicate<T> attributeEquals(IAttributeDescriptor<?> attributeDescriptor, Object value) {
		return new AttributeEqualsPredicate<T>(value, attributeDescriptor.getAttributeName());
	}

}
