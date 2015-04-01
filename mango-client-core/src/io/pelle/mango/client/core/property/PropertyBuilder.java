package io.pelle.mango.client.core.property;

public class PropertyBuilder {

	public PropertyBuilder() {
	}

	public static StringPropertyBuilder createStringProperty(String key) {
		return new StringPropertyBuilder(key);
	}

	public static IntegerPropertyBuilder createIntegerProperty(String key) {
		return new IntegerPropertyBuilder(key);
	}

	public static BooleanPropertyBuilder createBooleanProperty(String key) {
		return new BooleanPropertyBuilder(key);
	}
}
