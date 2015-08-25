package io.pelle.mango.db.copy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import com.google.common.base.Objects;

import io.pelle.mango.client.base.vo.IVOEntity;

public class ObjectFieldDescriptor {
	
	private final String fieldName;

	private final Class<?> sourceType;
	private Class<?> targetType;

	private final Method sourceReadMethod;
	private final Method sourceWriteMethod;

	private Method targetReadMethod;
	private Method targetWriteMethod;

	public ObjectFieldDescriptor(String fieldName, PropertyDescriptor sourcePropertyDescriptor, PropertyDescriptor targetPropertyDescriptor) {
		super();
		this.fieldName = fieldName;

		this.sourceReadMethod = sourcePropertyDescriptor.getReadMethod();
		this.sourceWriteMethod = sourcePropertyDescriptor.getWriteMethod();
		this.sourceType = sourcePropertyDescriptor.getPropertyType();

		if (targetPropertyDescriptor != null) {
			this.targetReadMethod = targetPropertyDescriptor.getReadMethod();
			this.targetWriteMethod = targetPropertyDescriptor.getWriteMethod();
			this.targetType = targetPropertyDescriptor.getPropertyType();
		}

	}

	public String getFieldName() {
		return this.fieldName;
	}

	@SuppressWarnings("rawtypes")
	public Class getSourceType() {
		return this.sourceType;
	}

	@SuppressWarnings("rawtypes")
	public Class getTargetType() {
		return this.targetType;
	}

	public boolean sourceHasReadMethod() {
		return this.sourceReadMethod != null;
	}

	public Object getSourceValue(Object object) {
		try {
			return sourceReadMethod.invoke(object, new Object[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object setSourceValue(Object object, Object value) {
		try {
			return sourceWriteMethod.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object getTargetValue(Object object) {
		try {
			return targetReadMethod.invoke(object, new Object[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Method getSourceWriteMethod() {
		return sourceWriteMethod;
	}

	public boolean targetHasWriteMethod() {
		return this.getTargetWriteMethod() != null;
	}

	public Method getTargetWriteMethod() {
		return this.targetWriteMethod;
	}

	public boolean targetHasReadMethod() {
		return this.targetReadMethod != null;
	}
//
//	public boolean sourceIsReference() {
//		return IBaseVO.class.asSubclass(sourceType) ||
//	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("fieldName", this.fieldName).toString();
	}

	public boolean sourceTypeIsReference() {
		return IVOEntity.class.isAssignableFrom(sourceType) || List.class.isAssignableFrom(sourceType);
	}

}