package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.List;

public class RestPackageDocumentation extends RestBaseDocumentation {

	private final String packageName;

	private List<RestServiceDocumentation> services = new ArrayList<RestServiceDocumentation>();

	public RestPackageDocumentation(String packageName) {
		super(packageName + ".package");
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<RestServiceDocumentation> getServices() {
		return services;
	}

}