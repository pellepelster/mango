package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.PROPERTY_TYPE;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public abstract class BasePropertyBuilder<VALUETYPE extends Serializable, BUILDER_TYPE extends IProperty<VALUETYPE>> implements IProperty<VALUETYPE>, Serializable {

	private String key;

	private String name;

	private VALUETYPE defaultValue;

	private IProperty<VALUETYPE> defaultProperty;

	private BUILDER_TYPE fallback;

	private PROPERTY_TYPE type;

	public static String DEFAULT_VALUE_DEFAULT_POSTFIX = ".default";

	public BasePropertyBuilder() {
	}

	public BasePropertyBuilder(String key, String name, PROPERTY_TYPE type) {
		super();
		this.key = key;
		this.name = name;
		this.type = type;
	}

	public BasePropertyBuilder(String key) {
		super();
		this.key = key;
	}

	public BUILDER_TYPE defaultValue(VALUETYPE defaultValue) {
		this.defaultValue = defaultValue;
		return getBuilder();
	}

	public BUILDER_TYPE defaultValueWithPostfix() {
		return defaultValueWithPostfix(DEFAULT_VALUE_DEFAULT_POSTFIX);
	}

	public BUILDER_TYPE defaultValueWithPostfix(String defaultValuePostfix) {
		defaultProperty = clone(key + defaultValuePostfix);
		return getBuilder();
	}

	public BUILDER_TYPE database() {
		return setType(PROPERTY_TYPE.DATABASE);
	}

	public BUILDER_TYPE spring() {
		return setType(PROPERTY_TYPE.SPRING);
	}

	public BUILDER_TYPE system() {
		return setType(PROPERTY_TYPE.SYSTEM);
	}

	public BUILDER_TYPE name(String name) {
		this.name = name;
		return getBuilder();
	}

	public BUILDER_TYPE fallback(IProperty<VALUETYPE> fallback) {
		this.fallback = (BUILDER_TYPE) fallback;
		return (BUILDER_TYPE) fallback;
	}

	public BUILDER_TYPE fallbackToDatabase() {
		return clone(null).setType(PROPERTY_TYPE.DATABASE);
	}

	public BUILDER_TYPE fallbackToSpring() {
		return clone(null).setType(PROPERTY_TYPE.SPRING);
	}

	public BUILDER_TYPE fallbackToSystem() {
		return clone(null).setType(PROPERTY_TYPE.SYSTEM);
	}

	protected BUILDER_TYPE setType(PROPERTY_TYPE type) {
		this.type = type;
		return getBuilder();
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
	public IProperty<VALUETYPE> getDefaultProperty() {
		return defaultProperty;
	}

	@Override
	public String toString(VALUETYPE value) {
		return Objects.toString(value, null);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract BUILDER_TYPE getBuilder();

	protected abstract BasePropertyBuilder<VALUETYPE, BUILDER_TYPE> clone(String key);

}
