package io.pelle.mango.server.documentation;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

public class RestMethodDocumentation {

	private final String[] paths;

	private final RestBodyDocumentation response;

	public static final Function<RestMethodDocumentation, String> ORDER_FUNCTION = new Function<RestMethodDocumentation, String>() {
		public String apply(RestMethodDocumentation methodDocumentation) {
			return Joiner.on("").join(methodDocumentation.paths);
		}
	};

	public RestMethodDocumentation(String[] paths, RestBodyDocumentation response) {
		super();
		this.paths = paths;
		this.response = response;
	}

	public String[] getPaths() {
		return paths;
	}

	public RestBodyDocumentation getResponse() {
		return response;
	}

}