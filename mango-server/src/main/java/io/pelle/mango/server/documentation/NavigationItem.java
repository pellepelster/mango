package io.pelle.mango.server.documentation;

public class NavigationItem {

	private final String name;

	private String path;

	public NavigationItem(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

}