package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

public class RestTypeDocumentation {

	private final Class<?> type;

	private List<RestAttributeDocumentation> attributes = Collections.emptyList();

	private boolean primitive;

	public RestTypeDocumentation(Class<?> type) {
		super();
		this.type = type;
		this.primitive = true;
	}

	public RestTypeDocumentation(Class<?> type, List<RestAttributeDocumentation> attributes) {
		this(type);
		primitive = false;
		this.attributes = attributes;
		Collections.sort(this.attributes, Ordering.natural().onResultOf(RestAttributeDocumentation.ORDER_FUNCTION));
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public String getTypeName() {
		return type.getSimpleName();
	}

	public List<RestAttributeDocumentation> getAttributes() {
		return attributes;
	}

	public Class<?> getType() {
		return type;
	}
}