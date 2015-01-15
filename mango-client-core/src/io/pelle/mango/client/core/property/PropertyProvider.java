package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

import java.util.ArrayList;
import java.util.List;

public class PropertyProvider {

	private static PropertyProvider instance;

	private List<IProperty<?>> properties = new ArrayList<IProperty<?>>();

	private PropertyProvider() {
	}

	public static PropertyProvider getInstance() {

		if (instance == null) {
			instance = new PropertyProvider();
		}

		return instance;
	}

	public void addProperties(IProperty<?> property) {
		properties.add(property);
	}

	public List<IProperty<?>> getProperties() {
		return properties;
	}
}
