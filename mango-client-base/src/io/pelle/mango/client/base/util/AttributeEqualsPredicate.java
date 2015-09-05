package io.pelle.mango.client.base.util;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import io.pelle.mango.client.base.vo.IBaseVO;

public class AttributeEqualsPredicate<T extends IBaseVO> implements Predicate<T> {

	private String attributeName;

	private Object value;

	public AttributeEqualsPredicate(Object value, String attributeName) {
		super();
		this.value = value;
		this.attributeName = attributeName;
	}

	@Override
	public boolean apply(T object) {
		return Objects.equal(value, object.get(attributeName));
	}
}
