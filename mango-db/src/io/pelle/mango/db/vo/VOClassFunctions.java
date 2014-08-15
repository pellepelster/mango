package io.pelle.mango.db.vo;

public class VOClassFunctions {

	public static <A> AttributeDescriptorAnnotationFunction attributeDescriptorAnnotation(Class<?> voClass, Class<A> annotationClass) {
		return new AttributeDescriptorAnnotationFunction(voClass, annotationClass);
	}

}
