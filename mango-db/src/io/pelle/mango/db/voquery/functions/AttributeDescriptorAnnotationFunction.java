package io.pelle.mango.db.voquery.functions;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.db.util.ClassUtils;
import io.pelle.mango.db.voquery.AttributeDescriptorAnnotation;

import java.lang.reflect.Field;

import com.google.common.base.Function;

public class AttributeDescriptorAnnotationFunction implements Function<IAttributeDescriptor<?>, AttributeDescriptorAnnotation<?>> {

	private Class<?> voClass;

	private Class annotationClass;

	AttributeDescriptorAnnotationFunction(Class<?> voClass, Class annotationClass) {
		super();
		this.voClass = voClass;
		this.annotationClass = annotationClass;
	}

	@Override
	public AttributeDescriptorAnnotation<?> apply(IAttributeDescriptor<?> attributeDescriptor) {

		Field field = ClassUtils.getDeclaredFieldsByName(voClass, attributeDescriptor.getAttributeName(), true);

		return new AttributeDescriptorAnnotation(attributeDescriptor, field.getAnnotation(annotationClass));
	}

}
