package io.pelle.mango.server.documentation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RestBaseDocumentation {

	private static final String NAME_KEY = "name";

	private static final String DESCRIPTION_KEY = "description";

	private static final String SHORT_DESCRIPTION_KEY = "description.short";

	private String description;

	private String shortDescription;

	private String name;

	public RestBaseDocumentation(String bundleName) {
		super();

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
			description = resourceBundle.getString(DESCRIPTION_KEY);
			shortDescription = resourceBundle.getString(SHORT_DESCRIPTION_KEY);
			name = resourceBundle.getString(NAME_KEY);
		} catch (MissingResourceException e) {
			// ignore
		}
	}

	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getName() {
		return name;
	}
}