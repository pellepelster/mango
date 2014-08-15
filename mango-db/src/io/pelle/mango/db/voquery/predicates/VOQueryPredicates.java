package io.pelle.mango.db.voquery.predicates;

public class VOQueryPredicates {

	public static AttributeDescriptorAttributeTypePredicate attributeDescriptorAttributeType(Class<?> clazz) {
		return new AttributeDescriptorAttributeTypePredicate(clazz);
	}

	public static AttributeDescriptorAnnotationPredicate attributeDescriptorAnnotationType(Class<?> annotationClass) {
		return new AttributeDescriptorAnnotationPredicate(annotationClass);
	}

}
