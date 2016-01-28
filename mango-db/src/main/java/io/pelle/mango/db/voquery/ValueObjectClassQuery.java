package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.IValueObject;
import io.pelle.mango.db.util.BeanUtils;

public class ValueObjectClassQuery {

	private Class<? extends IValueObject> valueObjectClass;

	public ValueObjectClassQuery(Class<? extends IValueObject> valueObjectClass) {
		this.valueObjectClass = valueObjectClass;
	}

	public static ValueObjectClassQuery createQuery(Class<? extends IValueObject> valueObjectClass) {
		return new ValueObjectClassQuery(valueObjectClass);
	}

	public static ValueObjectClassQuery createQuery(IValueObject valueObject) {
		return new ValueObjectClassQuery(valueObject.getClass());
	}

	public AttributesDescriptorQuery<?> attributesDescriptors() {
		return AttributesDescriptorQuery.createQuery(valueObjectClass, BeanUtils.getAttributeDescriptors(valueObjectClass));
	}

}
