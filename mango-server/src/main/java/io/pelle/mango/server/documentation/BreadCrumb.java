package io.pelle.mango.server.documentation;

public class BreadCrumb {

	private final String path;
	
	private final String name;

	public BreadCrumb(String path, String name) {
		super();
		this.path = path;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
}
