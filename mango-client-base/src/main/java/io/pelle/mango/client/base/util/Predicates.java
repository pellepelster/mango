package io.pelle.mango.client.base.util;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

public class Predicates {

	public static <T extends IBaseVO> AttributeEqualsPredicate<T> attributeEquals(IAttributeDescriptor<?> attributeDescriptor, Object value) {
		return new AttributeEqualsPredicate<T>(value, attributeDescriptor.getAttributeName());
	}

}
