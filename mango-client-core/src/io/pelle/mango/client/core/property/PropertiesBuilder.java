package io.pelle.mango.client.core.property;

public class PropertiesBuilder extends BaseCategoryBuilder {

	private static PropertiesBuilder instance;

	private static final String ROOT_CATEGORY_NAME = "ROOT_CATEGORY";

	// private PropertiesBuilder() {
	// super(new PropertyCategory());
	// getCategory().setName(ROOT_CATEGORY_NAME);
	// }

	public static PropertiesBuilder getInstance() {

		if (instance == null) {
			instance = new PropertiesBuilder();
		}

		return instance;
	}
	//
	// public List<PropertyCategory> getRootCategories() {
	// return getCategory().getPropertyCategories();
	// }
}
