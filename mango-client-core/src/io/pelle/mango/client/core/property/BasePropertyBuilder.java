package io.pelle.mango.client.core.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.PROPERTY_TYPE;

@SuppressWarnings("serial")
public abstract class BasePropertyBuilder<VALUETYPE extends Serializable, BUILDER_TYPE extends IProperty<VALUETYPE>> implements IProperty<VALUETYPE>, Serializable {

	private String key;

	private String name;

	private VALUETYPE defaultValue;

	private IProperty<VALUETYPE> defaultProperty;

	private List<IProperty<VALUETYPE>> fallbacks = new ArrayList<>();

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

	public IProperty<VALUETYPE> fallback(IProperty<VALUETYPE> fallback) {
		this.fallbacks.add(fallback);
		return getBuilder();
	}

	public BUILDER_TYPE fallbackToDatabase() {
		fallback(clone(null).setType(PROPERTY_TYPE.DATABASE));
		return getBuilder();
	}

	public BUILDER_TYPE fallbackToSpring() {
		fallback(clone(null).setType(PROPERTY_TYPE.SPRING));
		return getBuilder();
	}

	public BUILDER_TYPE fallbackToSystem() {
		fallback(clone(null).setType(PROPERTY_TYPE.SYSTEM));
		return getBuilder();
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
	public List<IProperty<VALUETYPE>> getFallbacks() {
		return fallbacks;
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

	@Override
	public String getHumanName() {
		if (getName() != null) {
			return name + " (" + getKey() + ")";
		}

		return getKey();

	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract BUILDER_TYPE getBuilder();

	protected abstract BasePropertyBuilder<VALUETYPE, BUILDER_TYPE> clone(String key);

}
