package io.pelle.mango.client.base.property;

public class PropertyDefinition {

	public PropertyDefinition() {
	}

	public PropertyDefinition(java.lang.String name, java.lang.String key, java.lang.String value, java.lang.String defaultValue, PropertyDefinition fallback) {

		this.setName(name);
		this.setKey(key);
		this.setValue(value);
		this.setDefaultValue(defaultValue);
		this.setFallback(fallback);
	}

	private java.lang.String name;

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	private java.lang.String key;

	public java.lang.String getKey() {
		return this.key;
	}

	public void setKey(java.lang.String key) {
		this.key = key;
	}

	private java.lang.String value;

	public java.lang.String getValue() {
		return this.value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	private java.lang.String defaultValue;

	public java.lang.String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}

	private PropertyDefinition fallback;

	public PropertyDefinition getFallback() {
		return this.fallback;
	}

	public void setFallback(PropertyDefinition fallback) {
		this.fallback = fallback;
	}

}