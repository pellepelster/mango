package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PackageDocumentation {

	private final String packageName;

	private List<RestServiceDocumentation> serviceDocumentations = new ArrayList<RestServiceDocumentation>();

	private static final String DESCRIPTION_KEY = "description";

	private String description;

	public PackageDocumentation(String packageName) {
		super();
		this.packageName = packageName;

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(packageName + ".package");
			description = resourceBundle.getString(DESCRIPTION_KEY);
		} catch (MissingResourceException e) {
			// ignore
		}
	}

	public String getPackageName() {
		return packageName;
	}

	public List<RestServiceDocumentation> getServiceDocumentations() {
		return serviceDocumentations;
	}

	public String getDescription() {
		return description;
	}
}