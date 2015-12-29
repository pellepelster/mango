package io.pelle.mango.server.documentation;

public class RestResponseDocumentation extends RestBaseDocumentation {

	private static final String RESPONSE_KEY = "result";

	private RestTypeDocumentation type;

	public RestResponseDocumentation(RestMethodDocumentation parentMethod, RestTypeDocumentation type) {
		super(parentMethod.getBundleName(), parentMethod.getPaths().get(0) + "." + RESPONSE_KEY);
		this.type = type;
	}

	public RestTypeDocumentation getType() {
		return type;
	}
}