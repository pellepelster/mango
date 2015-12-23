package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

public class RestBodyDocumentation {

	private List<RestBodyAttributeDocumentation> attributes = Collections.emptyList();

	private Class<?> primitiveType;
	
	public RestBodyDocumentation(List<RestBodyAttributeDocumentation> attributeDocumentations) {
		super();
		this.attributes = attributeDocumentations;
		
		Ordering<RestBodyAttributeDocumentation> ordering = Ordering.natural().onResultOf(RestBodyAttributeDocumentation.ORDER_FUNCTION);
		Collections.sort(this.attributes, ordering);
	}

	public RestBodyDocumentation(Class<?> primitiveType) {
		super();
		this.primitiveType = primitiveType;
		
		Ordering<RestBodyAttributeDocumentation> ordering = Ordering.natural().onResultOf(RestBodyAttributeDocumentation.ORDER_FUNCTION);
		Collections.sort(this.attributes, ordering);
	}

	public List<RestBodyAttributeDocumentation> getAttributes() {
		return attributes;
	}
	
	public boolean isPrimitive() {
		return primitiveType != null;
	}

	public Object getPrimitiveName() {
		return primitiveType.getSimpleName();
	}
	
}