package io.pelle.mango.server.documentation;

import com.google.common.base.Function;
import com.google.common.base.Objects;

public class RestAttributeDocumentation {

	private final String name;

	private final RestTypeDocumentation type;

	private boolean isList = false;

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
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", name).add("type", type.toString()).toString();
	}

	public boolean isList() {
		return isList ;
	}

}