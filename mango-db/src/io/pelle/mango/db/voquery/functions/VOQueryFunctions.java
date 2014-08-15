package io.pelle.mango.db.voquery.functions;

public class VOQueryFunctions {

	public static <A> AttributeDescriptorAnnotationFunction attributeDescriptorAnnotation(Class<?> voClass, Class<A> annotationClass) {
		return new AttributeDescriptorAnnotationFunction(voClass, annotationClass);
	}

}
