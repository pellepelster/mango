package io.pelle.mango.server.documentation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Ordering;

public class RestMethodDocumentation extends RestBaseDocumentation {

	private final List<String> paths;

	private final RestResponseDocumentation response;

	private final List<RequestMethod> httpMethods;

	private List<RestAttributeDocumentation> attributes = new ArrayList<RestAttributeDocumentation>();

	private Method method;
	
	public RestMethodDocumentation(RestServiceDocumentation parentService, List<String> paths, Method method, List<RequestMethod> methods) {

		super(parentService.getBundleName(), paths.get(0));
		
		this.paths = paths;
		Collections.sort(this.paths, Ordering.usingToString());
		
		this.method = method;
		
		RestTypeDocumentation responseType = DocumentationReflectionFactory.getTypeDocumentation(method.getReturnType());
		this.response = new RestResponseDocumentation(this, responseType);
		
		this.httpMethods = methods;
		Collections.sort(this.httpMethods, Ordering.usingToString());
		
		this.attributes = DocumentationReflectionFactory.getMethodAttributes(this);
		Collections.sort(this.attributes, Ordering.usingToString());
	}

	public List<String> getPaths() {
		return paths;
	}

	public RestResponseDocumentation getResponse() {
		return response;
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
	
	protected Method getMethod() {
		return method;
	}

}