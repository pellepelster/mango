package io.pelle.mango.dsl.query.predicates;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Predicate;

public class EObjectStructuralFeatureImplementsPredicate implements Predicate<EObject>
{
	private EStructuralFeature eStructuralFeature;
	private Class<?> clazz;

	public static EObjectStructuralFeatureImplementsPredicate create(EStructuralFeature eStructuralFeature, Class<?> clazz)
	{
		return new EObjectStructuralFeatureImplementsPredicate(eStructuralFeature, clazz);
	}

	private EObjectStructuralFeatureImplementsPredicate(EStructuralFeature eStructuralFeature, Class<?> clazz)
	{
		super();
		this.eStructuralFeature = eStructuralFeature;
		this.clazz = clazz;
	}

	@Override
	public boolean apply(EObject eObject)
	{
		return eObject.eIsSet(eStructuralFeature) && clazz.isAssignableFrom(eObject.eGet(eStructuralFeature).getClass());
	}
}
