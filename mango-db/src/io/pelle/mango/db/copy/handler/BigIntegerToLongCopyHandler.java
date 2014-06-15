package io.pelle.mango.db.copy.handler;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.IFieldCopyHandler;

import java.math.BigInteger;

import org.apache.commons.beanutils.PropertyUtils;

public class BigIntegerToLongCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(ObjectFieldDescriptor fieldDescriptor)
	{
		return BigInteger.class.isAssignableFrom(fieldDescriptor.getSourceType()) && long.class.isAssignableFrom(fieldDescriptor.getTargetType());
	}

	@Override
	public void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), Long.parseLong(((BigInteger) fieldDescriptor.getSourceValue()).toString()));
	}

}
