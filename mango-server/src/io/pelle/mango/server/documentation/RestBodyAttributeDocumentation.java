package io.pelle.mango.server.documentation;

import com.google.common.base.Function;

public class RestBodyAttributeDocumentation {

	private final String name;

	public static final Function<RestBodyAttributeDocumentation, String> ORDER_FUNCTION = new Function<RestBodyAttributeDocumentation, String>() {
		public String apply(RestBodyAttributeDocumentation attributeDocumentation) {
			return attributeDocumentation.name;
		}
	};

	public RestBodyAttributeDocumentation(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}