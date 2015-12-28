package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Ordering;

public class RestServiceDocumentation extends RestBaseDocumentation {

	private final String className;

	private final String[] paths;

	private final List<RestMethodDocumentation> methods;

	public RestServiceDocumentation(String className, String[] paths, List<RestMethodDocumentation> methods) {
		super(className.toLowerCase());
		this.className = className;
		this.paths = paths;
		
		this.methods = methods;
		Collections.sort(this.methods, Ordering.usingToString());
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
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("paths", Joiner.on(", ").join(paths)).add("methods", Joiner.on(", ").join(methods)).toString();
	}

}