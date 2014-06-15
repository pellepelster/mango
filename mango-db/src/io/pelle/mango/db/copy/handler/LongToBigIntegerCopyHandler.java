package io.pelle.mango.db.copy.handler;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.IFieldCopyHandler;

import java.math.BigInteger;

import org.apache.commons.beanutils.PropertyUtils;

public class LongToBigIntegerCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(ObjectFieldDescriptor fieldDescriptor)
	{
		return long.class.isAssignableFrom(fieldDescriptor.getSourceType()) && BigInteger.class.isAssignableFrom(fieldDescriptor.getTargetType());
	}

	@Override
	public void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), BigInteger.valueOf((Long) fieldDescriptor.getSourceValue()));
	}

}
