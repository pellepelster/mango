package io.pelle.mango.dsl.query.functions;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Function;

public class FunctionEObjectTypeSelect<T> implements Function<EObject, T>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> FunctionEObjectTypeSelect<T> getFunction(Class<T> clazz)
	{
		return new FunctionEObjectTypeSelect(clazz);
	}

	private Class<T> clazz;

	private FunctionEObjectTypeSelect(Class<T> clazz)
	{
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T apply(EObject eObject)
	{
		if (clazz.isAssignableFrom(eObject.getClass()))
		{
			return (T) eObject;
		}

		return null;
	}
}
