package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

public class RestServiceDocumentation {

	private final String className;

	private final String[] paths;

	private final List<RestMethodDocumentation> methodDocumentations;

	public RestServiceDocumentation(String className, String[] paths, List<RestMethodDocumentation> methodDocumentations) {
		super();
		this.className = className;
		this.paths = paths;
		this.methodDocumentations = methodDocumentations;
		
		Ordering<RestMethodDocumentation> ordering = Ordering.natural().onResultOf(RestMethodDocumentation.ORDER_FUNCTION);
		Collections.sort(this.methodDocumentations, ordering);
	}

	public String getServiceName() {
		return className.substring(className.lastIndexOf(".") + 1);
	}

	public String getClassName() {
		return className;
	}

	public String[] getPaths() {
		return paths;
	}
	
	public List<RestMethodDocumentation> getMethodDocumentations() {
		return methodDocumentations;
	}

}