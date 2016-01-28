package io.pelle.mango.db.copy;
public interface IFieldCopyHandler
{

	boolean check(ObjectFieldDescriptor fieldDescriptor);

	void copy(ObjectFieldDescriptor fieldDescriptor, Object sourceObject, Object targetObject) throws Exception;

}