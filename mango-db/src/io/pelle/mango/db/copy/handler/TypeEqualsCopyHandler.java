package io.pelle.mango.db.copy.handler;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.IFieldCopyHandler;

import java.util.List;

public class TypeEqualsCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(ObjectFieldDescriptor fieldDescriptor)
	{
		return fieldDescriptor.getSourceType().equals(fieldDescriptor.getTargetType()) && !List.class.isAssignableFrom(fieldDescriptor.getSourceType());
	}

	@Override
	public void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		fieldDescriptor.getTargetWriteMethod().invoke(targetObject, fieldDescriptor.getSourceValue());
	}

}
