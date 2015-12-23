package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

public class RestServiceDocumentation {

	private final String className;

	private final String[] paths;

	private final List<RestMethodDocumentation> methods;

	public RestServiceDocumentation(String className, String[] paths, List<RestMethodDocumentation> methods) {
		super();
		this.className = className;
		this.paths = paths;
		this.methods = methods;

		Ordering<RestMethodDocumentation> ordering = Ordering.natural().onResultOf(RestMethodDocumentation.ORDER_FUNCTION);
		Collections.sort(this.methods, ordering);
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

	public List<RestMethodDocumentation> getMethods() {
		return methods;
	}

}