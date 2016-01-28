package io.pelle.mango.client.base.vo;

@SuppressWarnings("serial")
public class ValueObjectDescriptor<T extends IValueObject> implements IValueObjectDescriptor<T> {

	private Class<T> valueObjectClass;

	public ValueObjectDescriptor() {
	}

	public ValueObjectDescriptor(Class<T> valueObjectClass) {
		super();
		this.valueObjectClass = valueObjectClass;
	}

	@Override
	public IMetaDescriptor<?> getParent() {
		return null;
	}

	@Override
	public Class<T> getVOEntityClass() {
		return valueObjectClass;
	}

}
