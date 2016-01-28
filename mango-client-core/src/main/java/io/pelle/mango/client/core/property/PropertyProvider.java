package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

public class PropertyProvider {

	private static final String ROOT_CATEGORY_NAME = "ROOT_CATEGORY";

	private static PropertyProvider instance;

	private PropertyCategoryBuilder rootCategory;
	
	private PropertyProvider() {
		rootCategory = new PropertyCategoryBuilder(ROOT_CATEGORY_NAME);
	}

	public static PropertyProvider getInstance() {

		if (instance == null) {
			instance = new PropertyProvider();
		}

		return instance;
	}

	public PropertyCategoryBuilder createCategory(String name) {
		return rootCategory.createCategory(name);
	}

	public void addProperty(IProperty<?> property) {
		rootCategory.addProperty(property);
	}

	public PropertyGroupBuilder createGroup(String name) {
		return rootCategory.createGroup(name);
	}
	
	public PropertyCategoryBuilder getRootCategory() {
		return rootCategory;
	}
	
}
