package io.pelle.mango.dsl.query.functions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Function;

public class FunctionStructuralFeatureTypeSelect<T> implements Function<EObject, T>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> FunctionStructuralFeatureTypeSelect<T> create(EStructuralFeature eStructuralFeature, Class<T> clazz)
	{
		return new FunctionStructuralFeatureTypeSelect(eStructuralFeature, clazz);
	}

	private Class<T> clazz;
	private EStructuralFeature eStructuralFeature;

	private FunctionStructuralFeatureTypeSelect(EStructuralFeature eStructuralFeature, Class<T> clazz)
	{
		super();
		this.eStructuralFeature = eStructuralFeature;
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T apply(EObject eObject)
	{
		if (eObject.eIsSet(eStructuralFeature) && clazz.isAssignableFrom(eObject.eGet(eStructuralFeature).getClass()))
		{
			return (T) eObject;
		}

		return null;
	}
}
