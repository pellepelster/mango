package io.pelle.mango.dsl.query.functions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.google.common.base.Function;

public class FunctionEReferenceTypeSelect<T> implements Function<EObject, T>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> FunctionEReferenceTypeSelect<T> create(EReference eReference, Class<T> clazz)
	{
		return new FunctionEReferenceTypeSelect(eReference, clazz);
	}

	private Class<T> clazz;
	private EReference eReference;

	private FunctionEReferenceTypeSelect(EReference eReference, Class<T> clazz)
	{
		super();
		this.eReference = eReference;
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T apply(EObject eObject)
	{
		if (eObject.eIsSet(eReference))
		{
			Object o = eObject.eGet(eReference, true);

			if (o != null && clazz.isAssignableFrom(o.getClass()))
			{
				return (T) o;
			}
		}

		return null;
	}
}
