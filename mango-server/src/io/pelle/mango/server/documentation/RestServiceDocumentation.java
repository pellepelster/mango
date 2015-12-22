package io.pelle.mango.server.documentation;

public class RestServiceDocumentation {

	private final String className;

	public RestServiceDocumentation(String className) {
		super();
		this.className = className;
	}

	public String getServiceName() {
		return className.substring(className.lastIndexOf(".")+1);
	}
	
	public String getClassName() {
		return className;
	}

}