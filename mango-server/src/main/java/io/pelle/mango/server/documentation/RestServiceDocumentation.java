package io.pelle.mango.server.documentation;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Ordering;

public class RestServiceDocumentation extends RestBaseDocumentation {

	private final Class<?> serviceClass;

	private final String[] paths;

	private final List<RestMethodDocumentation> methods;

	public RestServiceDocumentation(Class<?> serviceClass, String[] paths) {
		super(serviceClass.getName().toLowerCase());
		this.serviceClass = serviceClass;
		this.paths = paths;

		methods = DocumentationReflectionFactory.getServiceMethods(this);
		Collections.sort(this.methods, Ordering.usingToString());
	}

	public String getServiceName() {
		return serviceClass.getSimpleName();
	}

	public String getClassName() {
		return serviceClass.getName();
	}

	public String[] getPaths() {
		return paths;
	}

	public List<RestMethodDocumentation> getMethods() {
		return methods;
	}
	
	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("paths", Joiner.on(", ").join(paths)).add("methods", Joiner.on(", ").join(methods)).toString();
	}

}