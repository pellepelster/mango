package io.pelle.mango.db.copy.handler;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.IFieldCopyHandler;

import org.apache.commons.beanutils.PropertyUtils;

public class EnumCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(ObjectFieldDescriptor fieldDescriptor)
	{
		return Enum.class.isAssignableFrom(fieldDescriptor.getSourceType()) && Enum.class.isAssignableFrom(fieldDescriptor.getTargetType());
	}

	@Override
	public void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		@SuppressWarnings("unchecked")
		Object enumValue = Enum.valueOf(fieldDescriptor.getTargetType(), fieldDescriptor.getSourceValue().toString());
		PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), enumValue);
	}

}
