package io.pelle.mango.server.documentation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IValueObject;
import io.pelle.mango.server.base.meta.Documented;

public class DocumentationReflectionFactory {

	private static Map<Class<?>, RestTypeDocumentation> types = new HashMap<Class<?>, RestTypeDocumentation>();

	public static RestTypeDocumentation getTypeDocumentation(Class<?> type) {

		if (!types.containsKey(type)) {

			if (IValueObject.class.isAssignableFrom(type) || IBaseVO.class.isAssignableFrom(type)) {
				List<RestAttributeDocumentation> attributeDocumentations = new ArrayList<>();

				try {
					BeanInfo beanInfo = Introspector.getBeanInfo(type);

					for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {

						if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {

							RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(propertyDescriptor.getName(), propertyDescriptor.getPropertyType());
							attributeDocumentations.add(attributeDocumentation);
						}
					}

					types.put(type, new RestTypeDocumentation(type, attributeDocumentations));
				} catch (IntrospectionException e) {
					throw new RuntimeException(e);
				}
			} else {
				types.put(type, new RestTypeDocumentation(type));
			}

		}
		return types.get(type);

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

					RestTypeDocumentation responseType = getTypeDocumentation(method.getReturnType());

					List<String> paths = Arrays.asList(ArrayUtils.addAll(methodRequestMapping.path(), methodRequestMapping.value()));
					List<RestAttributeDocumentation> attributes = new ArrayList<RestAttributeDocumentation>();
					
					Annotation[][] annotations = method.getParameterAnnotations();
					Class<?>[] parameterTypes = method.getParameterTypes();

					// check parameters
					int parameterIndex = 0;
					for (Annotation[] outerAnnotation : annotations) {

						for (Annotation innerAnnotation : outerAnnotation) {
							
							Class<?> parameterType = parameterTypes[parameterIndex];

							if (innerAnnotation.annotationType().equals(RequestBody.class)) {
								
								RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(null, parameterType);
								attributes.add(attributeDocumentation);
								
							} else 	if (innerAnnotation.annotationType().equals(RequestParam.class)) {
								
								RequestParam requestParam = (RequestParam) innerAnnotation;
								
								RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(requestParam.value(), parameterType);
								attributes.add(attributeDocumentation);
							}

						}

						parameterIndex++;
					}

					RestMethodDocumentation methodDocumentation = new RestMethodDocumentation(paths, responseType, Arrays.asList(methodRequestMapping.method()), attributes);
					methodDocumentations.add(methodDocumentation);

				}

			}

			RestServiceDocumentation restDocumentation = new RestServiceDocumentation(clazz.getName(), ArrayUtils.addAll(serviceRequestMapping.path(), serviceRequestMapping.value()), methodDocumentations);

			return restDocumentation;
		}

		return null;
	}

}
