package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePropertiesBuilder {

	private final String name;
	
	private List<IProperty<?>> properties = new ArrayList<IProperty<?>>();

	public BasePropertiesBuilder(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void addProperty(IProperty<?> property) {
		properties.add(property);
	}

	public List<IProperty<?>> getProperties() {
		return properties;
	}
	
}
