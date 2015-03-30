package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.PROPERTY_TYPE;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public abstract class BasePropertyBuilder<VALUETYPE extends Serializable, BUILDER_TYPE> implements IProperty<VALUETYPE>, Serializable {

	private String key;

	private String name;

	private VALUETYPE defaultValue;

	private IProperty<VALUETYPE> fallback;

	private PROPERTY_TYPE type;

	public BasePropertyBuilder() {
	}

	public BasePropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super();
		this.key = key;
		this.name = name;
		this.type = type;
	}

	public BasePropertyBuilder(String key, PROPERTY_TYPE type) {
		this(key, null, type);
	}

	public BUILDER_TYPE defaultValue(VALUETYPE defaultValue) {
		this.defaultValue = defaultValue;
		return getBuilder();
	}

	public BUILDER_TYPE fallback(IProperty<VALUETYPE> fallback) {
		this.fallback = fallback;
		return getBuilder();
	}

	public BUILDER_TYPE fallback(PROPERTY_TYPE type) {
		return fallback(cloneWithNewType(type));
	}

	@Override
	public PROPERTY_TYPE getType() {
		return type;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public IProperty<VALUETYPE> getFallback() {
		return fallback;
	}

	@Override
	public VALUETYPE getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String toString(VALUETYPE value) {
		return Objects.toString(value, null);
	}

	@Override
	public String getName() {
		return name;
	}

	protected abstract BUILDER_TYPE getBuilder();

	protected abstract IProperty<VALUETYPE> cloneWithNewType(PROPERTY_TYPE type);

}
