package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.List;

public class PackageDocumentation {

	private final String packageName;

	private List<RestServiceDocumentation> serviceDocumentations = new ArrayList<RestServiceDocumentation>();

	public PackageDocumentation(String packageName) {
		super();
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<RestServiceDocumentation> getServiceDocumentations() {
		return serviceDocumentations;
	}
}