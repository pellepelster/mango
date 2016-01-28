package io.pelle.mango.server.documentation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class RestBaseDocumentation {

	private static final String NAME_KEY = "name";

	private static final String DESCRIPTION_KEY = "description";

	private static final String SHORT_DESCRIPTION_KEY = "description.short";

	private String description;

	private String shortDescription;

	private String name;

	private String bundleName;

	public RestBaseDocumentation(String bundleName) {
		this(bundleName, "");
	}

	public RestBaseDocumentation(String bundleName, String baseKey) {
		super();
		this.bundleName = bundleName;

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
			description = resourceBundle.getString(getBaseKey(baseKey, DESCRIPTION_KEY));
			shortDescription = resourceBundle.getString(getBaseKey(baseKey, SHORT_DESCRIPTION_KEY));
			name = resourceBundle.getString(getBaseKey(baseKey, NAME_KEY));
		} catch (MissingResourceException e) {
			// ignore
		}
	}

	private String getBaseKey(String baseKey, String key) {
		
		if (StringUtils.isEmpty(baseKey)) {
			return key;
		} else {
			return baseKey + "." + key;
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

	protected String getBundleName() {
		return bundleName;
	}
}