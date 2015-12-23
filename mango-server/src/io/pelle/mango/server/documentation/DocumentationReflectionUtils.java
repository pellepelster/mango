package io.pelle.mango.server.documentation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.pelle.mango.server.base.meta.Documented;

public class DocumentationReflectionUtils {

	public static RestBodyDocumentation getRestBodyDocumentation(Class<?> clazz) {

		if (ClassUtils.isPrimitiveOrWrapper(clazz)) {
			return new RestBodyDocumentation(clazz);
		}
		
		List<RestBodyAttributeDocumentation> attributeDocumentations = new ArrayList<>();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

			for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {

				if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {

					RestBodyAttributeDocumentation attributeDocumentation = new RestBodyAttributeDocumentation(propertyDescriptor.getName());
					attributeDocumentations.add(attributeDocumentation);
				}
			}

			return new RestBodyDocumentation(attributeDocumentations);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

	}

	public static RestServiceDocumentation getDocumentationFor(Class<?> clazz) {

		Documented documented = clazz.getAnnotation(Documented.class);
		RequestMapping serviceRequestMapping = clazz.getAnnotation(RequestMapping.class);

		if (documented != null && serviceRequestMapping != null) {

			List<RestMethodDocumentation> methodDocumentations = new ArrayList<RestMethodDocumentation>();

			for (Method method : clazz.getMethods()) {

				RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
				ResponseBody responseBody = method.getAnnotation(ResponseBody.class);

				if (methodRequestMapping != null && responseBody != null) {

					RestBodyDocumentation responseBodyDocumentation = getRestBodyDocumentation(method.getReturnType());

					RestMethodDocumentation methodDocumentation = new RestMethodDocumentation(ArrayUtils.addAll(methodRequestMapping.path(), methodRequestMapping.value()), responseBodyDocumentation);
					methodDocumentations.add(methodDocumentation);

					Annotation[][] annotations = method.getParameterAnnotations();
					Class<?>[] parameterTypes = method.getParameterTypes();

					int parameterIndex = 0;
					for (Annotation[] outerAnnotation : annotations) {

						for (Annotation innerAnnotation : outerAnnotation) {

							if (innerAnnotation.annotationType().equals(RequestBody.class)) {
								RequestBody requestBody = (RequestBody) innerAnnotation;
								Class<?> parameterType = parameterTypes[parameterIndex];
								parameterType.toString();

							}
						}

						parameterIndex++;
					}

					// RequestBody requestBody = method
					// @RequestBody
					// @RequestParam

				}

			}

			RestServiceDocumentation restDocumentation = new RestServiceDocumentation(clazz.getName(), ArrayUtils.addAll(serviceRequestMapping.path(), serviceRequestMapping.value()),
					methodDocumentations);

			return restDocumentation;
		}

		return null;
	}

}
