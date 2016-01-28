package io.pelle.mango.server.documentation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IValueObject;
import io.pelle.mango.db.voquery.ValueObjectClassQuery;
import io.pelle.mango.server.base.meta.Documented;

public class DocumentationReflectionFactory {

	private static Map<Class<?>, RestTypeDocumentation> types = new HashMap<Class<?>, RestTypeDocumentation>();

	public static RestTypeDocumentation getTypeDocumentation(Class<?> type) {

		if (!types.containsKey(type)) {

			if (IValueObject.class.isAssignableFrom(type) || IBaseVO.class.isAssignableFrom(type)) {
				
				List<RestAttributeDocumentation> attributeDocumentations = new ArrayList<>();

				for (IAttributeDescriptor<?> attributeDescriptor : ValueObjectClassQuery.createQuery((Class<? extends IValueObject>) type).attributesDescriptors().getCollection()) {
					
					RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(attributeDescriptor.getAttributeName(), attributeDescriptor.getListAttributeType(), attributeDescriptor.getListAttributeType() != attributeDescriptor.getAttributeType());
					attributeDocumentations.add(attributeDocumentation);

				}
				
				types.put(type, new RestTypeDocumentation(type, attributeDocumentations));
			} else {
				types.put(type, new RestTypeDocumentation(type));
			}

		}
		return types.get(type);

	}

	public static List<RestMethodDocumentation> getServiceMethods(RestServiceDocumentation service) {

		List<RestMethodDocumentation> methodDocumentations = new ArrayList<RestMethodDocumentation>();

		for (Method method : service.getServiceClass().getMethods()) {

			RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
			ResponseBody responseBody = method.getAnnotation(ResponseBody.class);

			if (methodRequestMapping != null && responseBody != null) {

				List<String> paths = Arrays.asList(ArrayUtils.addAll(methodRequestMapping.path(), methodRequestMapping.value()));

				RestMethodDocumentation methodDocumentation = new RestMethodDocumentation(service, paths, method, Arrays.asList(methodRequestMapping.method()));
				methodDocumentations.add(methodDocumentation);

			}

		}

		return methodDocumentations;
	}

	public static List<RestAttributeDocumentation> getMethodAttributes(RestMethodDocumentation method) {

		List<RestAttributeDocumentation> attributes = new ArrayList<RestAttributeDocumentation>();

		Annotation[][] annotations = method.getMethod().getParameterAnnotations();
		Class<?>[] parameterTypes = method.getMethod().getParameterTypes();

		// check parameters
		int parameterIndex = 0;
		for (Annotation[] outerAnnotation : annotations) {

			for (Annotation innerAnnotation : outerAnnotation) {

				Class<?> parameterType = parameterTypes[parameterIndex];

				if (innerAnnotation.annotationType().equals(RequestBody.class)) {

					RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(null, parameterType, false);
					attributes.add(attributeDocumentation);

				} else if (innerAnnotation.annotationType().equals(RequestParam.class)) {

					RequestParam requestParam = (RequestParam) innerAnnotation;

					RestAttributeDocumentation attributeDocumentation = new RestAttributeDocumentation(requestParam.value(), parameterType, false);
					attributes.add(attributeDocumentation);
				}

			}

			parameterIndex++;
		}

		return attributes;
	}

	public static RestServiceDocumentation getDocumentationFor(Class<?> serviceClass) {

		Documented documented = serviceClass.getAnnotation(Documented.class);
		RequestMapping serviceRequestMapping = serviceClass.getAnnotation(RequestMapping.class);

		if (documented != null && serviceRequestMapping != null) {

			RestServiceDocumentation service = new RestServiceDocumentation(serviceClass, ArrayUtils.addAll(serviceRequestMapping.path(), serviceRequestMapping.value()));

			return service;
		}

		return null;
	}

}
