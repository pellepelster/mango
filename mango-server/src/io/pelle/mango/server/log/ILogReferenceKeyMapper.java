package io.pelle.mango.server.log;

public interface ILogReferenceKeyMapper {

	String getReferenceKey(Object object);

	boolean supports(Class<?> referenceClass);

}
