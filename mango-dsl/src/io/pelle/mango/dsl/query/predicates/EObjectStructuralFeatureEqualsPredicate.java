package io.pelle.mango.dsl.query.predicates;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Predicate;

public class EObjectStructuralFeatureEqualsPredicate implements Predicate<EObject>
{
	private EStructuralFeature eStructuralFeature;
	private Object object;

	public static EObjectStructuralFeatureEqualsPredicate create(EStructuralFeature eStructuralFeature, Object object)
	{
		return new EObjectStructuralFeatureEqualsPredicate(eStructuralFeature, object);
	}

	private EObjectStructuralFeatureEqualsPredicate(EStructuralFeature eStructuralFeature, Object object)
	{
		super();
		this.eStructuralFeature = eStructuralFeature;
		this.object = object;
	}

	@Override
	public boolean apply(EObject eObject)
	{
		return eObject.eIsSet(eStructuralFeature) && eObject.eGet(eStructuralFeature).equals(object);
	}
}
