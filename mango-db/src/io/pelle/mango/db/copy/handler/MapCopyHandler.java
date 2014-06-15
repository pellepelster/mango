package io.pelle.mango.db.copy.handler;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.IFieldCopyHandler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class MapCopyHandler implements IFieldCopyHandler
{

	@Override
	public boolean check(ObjectFieldDescriptor fieldDescriptor)
	{
		return Map.class.isAssignableFrom(fieldDescriptor.getSourceType()) && Map.class.isAssignableFrom(fieldDescriptor.getTargetType());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception
	{
		HashMap<Object, Object> targetMap = new HashMap<Object, Object>();

		for (Map.Entry<Object, Object> entry1 : ((Map<Object, Object>) fieldDescriptor.getSourceValue()).entrySet())
		{
			targetMap.put(entry1.getKey(), entry1.getValue());
		}

		PropertyUtils.setProperty(targetObject, fieldDescriptor.getFieldName(), targetMap);
	}

}
