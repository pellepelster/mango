package io.pelle.mango.client.base.vo;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class ValueObjectDescriptor<T extends IValueObject> implements IValueObjectDescriptor<T> {

	private Class<? extends IValueObject> valueObjectClass;

	public ValueObjectDescriptor() {
	}

	public ValueObjectDescriptor(Class<? extends IValueObject> valueObjectClass) {
		super();
		this.valueObjectClass = valueObjectClass;
	}

	@Override
	public IMetaDescriptor getParent() {
		return null;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("valueObjectClass", valueObjectClass).toString();
	}


}
