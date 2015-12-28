package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Ordering;

public class RestMethodDocumentation {

	private final List<String> paths;

	private final RestTypeDocumentation returnType;

	private final List<RequestMethod> httpMethods;

	private List<RestAttributeDocumentation> attributes = new ArrayList<RestAttributeDocumentation>();

	public RestMethodDocumentation(List<String> paths, RestTypeDocumentation returnType, List<RequestMethod> methods, List<RestAttributeDocumentation> attributes) {
		super();
		
		this.paths = paths;
		Collections.sort(this.paths, Ordering.usingToString());
		
		this.returnType = returnType;
		
		this.httpMethods = methods;
		Collections.sort(this.httpMethods, Ordering.usingToString());
		
		this.attributes = attributes; 
		Collections.sort(this.attributes, Ordering.usingToString());
	}

	public List<String> getPaths() {
		return paths;
	}

	public RestTypeDocumentation getReturnType() {
		return returnType;
	}

	public List<RequestMethod> getHttpMethods() {
		return httpMethods;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("paths", Joiner.on(", ").join(paths)).add("http methods", Joiner.on(", ").join(httpMethods)).toString();
	}

	public List<RestAttributeDocumentation> getAttributes() {
		return attributes ;
	}
}