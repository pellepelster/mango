package io.pelle.mango.db.voquery;

import io.pelle.mango.db.voquery.predicates.AttributeDescriptorNamePredicate;

public class Predicates {

	public static AttributeDescriptorNamePredicate attributeDescriptorByName(String name) {
		return new AttributeDescriptorNamePredicate(name);
	}
}
