package io.pelle.mango.server.documentation;

import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

public class RestMethodDocumentation {

	private final String[] paths;

	private final RestTypeDocumentation returnType;

	private final RequestMethod[] methods;

	public static final Function<RestMethodDocumentation, String> ORDER_FUNCTION = new Function<RestMethodDocumentation, String>() {
		public String apply(RestMethodDocumentation methodDocumentation) {
			return Joiner.on("").join(methodDocumentation.paths);
		}
	};

	public RestMethodDocumentation(String[] paths, RestTypeDocumentation returnType, RequestMethod[] methods) {
		super();
		this.paths = paths;
		this.returnType = returnType;
		this.methods = methods;
	}

	public String[] getPaths() {
		return paths;
	}

	public RestTypeDocumentation getReturnType() {
		return returnType;
	}

	public RequestMethod[] getMethods() {
		return methods;
	}
}