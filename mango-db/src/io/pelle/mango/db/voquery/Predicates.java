package io.pelle.mango.db.voquery;

import io.pelle.mango.db.voquery.predicates.AttributeDescriptorNamePredicate;
import io.pelle.mango.db.voquery.predicates.AttributeEqualsPredicate;

public class Predicates {

	public static AttributeDescriptorNamePredicate attributeDescriptorByName(String name) {
		return new AttributeDescriptorNamePredicate(name);
	}
	
	public static AttributeEqualsPredicate attributeDescriptorByName(String attributeName, Object value) {
		return new AttributeEqualsPredicate(value, attributeName);
	}
	
}
