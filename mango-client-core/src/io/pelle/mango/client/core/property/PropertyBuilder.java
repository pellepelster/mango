package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

import java.util.ArrayList;
import java.util.List;

public class PropertyBuilder {

	private static List<IProperty<?>> properties = new ArrayList<IProperty<?>>();

	public PropertyBuilder() {
	}

	public static StringPropertyBuilder createStringProperty(String key) {
		StringPropertyBuilder result = new StringPropertyBuilder(key);
		properties.add(result);
		return result;
	}

	public static IntegerPropertyBuilder createIntegerProperty(String key) {
		IntegerPropertyBuilder result = new IntegerPropertyBuilder(key);
		properties.add(result);
		return result;
	}

	public static BooleanPropertyBuilder createBooleanProperty(String key) {
		BooleanPropertyBuilder result = new BooleanPropertyBuilder(key);
		properties.add(result);
		return result;
	}

	public static List<IProperty<?>> getProperties() {
		return properties;
	}

}
