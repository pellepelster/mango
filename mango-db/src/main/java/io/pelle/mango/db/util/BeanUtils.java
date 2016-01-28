package io.pelle.mango.db.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

public class BeanUtils {

	@SuppressWarnings("unchecked")
	public static Set<Class<? extends IBaseVO>> getReferencedVOs(Class<?> clazz) {

		Set<Class<? extends IBaseVO>> voAttributeDescriptors = new HashSet<Class<? extends IBaseVO>>();

		for (IAttributeDescriptor<?> attributeDescriptor : getAttributeDescriptors(clazz)) {

			if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {
				voAttributeDescriptors.add((Class<? extends IBaseVO>) attributeDescriptor.getAttributeType());
			}

			if (attributeDescriptor.getListAttributeType() != null && IBaseVO.class.isAssignableFrom(attributeDescriptor.getListAttributeType())) {
				voAttributeDescriptors.add((Class<? extends IBaseVO>) attributeDescriptor.getListAttributeType());
			}
		}

		return voAttributeDescriptors;
	}

	public static IAttributeDescriptor<?> getAttributeDescriptor(IAttributeDescriptor<?>[] attributeDescriptors, String attributeName) {

		for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors) {

			if (attributeDescriptor.getAttributeName().equals(attributeName)) {
				return attributeDescriptor;
			}
		}

		return null;
	}
	
	public static Object getAttribute(Object object, String attributeName) {
		try {
			return PropertyUtils.getProperty(object, attributeName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setAttribute(Object object, String attributeName, Object attributeValue) {
		try {
			PropertyUtils.setProperty(object, attributeName, attributeValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static IAttributeDescriptor<?>[] getAttributeDescriptors(Class<?> clazz) {
		try {
			Object o = MethodUtils.invokeStaticMethod(clazz, "getAttributeDescriptors", null);
			return (IAttributeDescriptor<?>[]) o;
		} catch (Exception e) {
			throw new RuntimeException(String.format("error invoking method 'getAttributeDescriptors' on class '%s'", clazz.getName()), e);
		}

	}

	public static IAttributeDescriptor<?> getAttributeDescriptor(Class<?> clazz, String attributeName) {
		return getAttributeDescriptor(getAttributeDescriptors(clazz), attributeName);
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends IBaseVO> getVOClass(String voClassName) {
		Class<? extends IBaseVO> voClass;
		try {
			voClass = (Class<? extends IBaseVO>) Class.forName(voClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		return voClass;
	}

}
