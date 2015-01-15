package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.PROPERTY_TYPE;

public class PropertyBuilder {

	public static StringPropertyBuilder createStringSystemProperty(String key, String name) {
		return new StringPropertyBuilder(key, name, PROPERTY_TYPE.SYSTEM);
	}

	public static StringPropertyBuilder createStringDatabaseProperty(String key, String name) {
		return new StringPropertyBuilder(key, name, PROPERTY_TYPE.DATABASE);
	}

	public static BooleanPropertyBuilder createBooleanSystemProperty(String key, String name) {
		return new BooleanPropertyBuilder(key, name, PROPERTY_TYPE.SYSTEM);
	}

	public static BooleanPropertyBuilder createBooleanDatabaseProperty(String key, String name) {
		return new BooleanPropertyBuilder(key, name, PROPERTY_TYPE.DATABASE);
	}

	public static IntegerPropertyBuilder createIntegerSystemProperty(String key, String name) {
		return new IntegerPropertyBuilder(key, name, PROPERTY_TYPE.SYSTEM);
	}

	public static IntegerPropertyBuilder createIntegerDatabaseProperty(String key, String name) {
		return new IntegerPropertyBuilder(key, name, PROPERTY_TYPE.DATABASE);
	}

}
