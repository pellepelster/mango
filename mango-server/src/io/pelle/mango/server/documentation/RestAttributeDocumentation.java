package io.pelle.mango.server.documentation;

import com.google.common.base.Function;

public class RestAttributeDocumentation {

	private final String name;

	private final RestTypeDocumentation type;

	public static final Function<RestAttributeDocumentation, String> ORDER_FUNCTION = new Function<RestAttributeDocumentation, String>() {
		public String apply(RestAttributeDocumentation attributeDocumentation) {
			return attributeDocumentation.name;
		}
	};

	public RestAttributeDocumentation(String name, Class<?> type) {
		super();
		this.name = name;
		this.type = DocumentationReflectionFactory.getTypeDocumentation(type);
	}

	public String getName() {
		return name;
	}

	public RestTypeDocumentation getType() {
		return type;
	}

}