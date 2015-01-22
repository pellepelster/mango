package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public abstract class BasePropertyContainerBuilder {

	private String id;

	private String name;

	private List<IProperty<?>> properties = new ArrayList<IProperty<?>>();

	public BasePropertyContainerBuilder() {
	}

	public BasePropertyContainerBuilder(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void addProperty(IProperty<?> property) {
		properties.add(property);
	}

	public List<IProperty<?>> getProperties() {
		return properties;
	}

	public String getName() {
		return Objects.firstNonNull(name, id);
	}

	public void setName(String name) {
		this.name = name;
	}

}
