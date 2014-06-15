package io.pelle.mango.dsl.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

public class TypeQuery<T extends EObject> extends BaseEObjectCollectionQuery<T>
{

	public TypeQuery(Collection<T> eObjects)
	{
		super(eObjects);
	}

	public <K extends EObject> Collection<K> getFeatures(EStructuralFeature eStructuralFeature, Class<K> attributeTypeClass)
	{
		return transform(eStructuralFeature, attributeTypeClass);
	}

	public <K extends EObject> Collection<K> getFeatures(EReference eReference, Class<K> attributeTypeClass)
	{
		return transform(eReference, attributeTypeClass);
	}

	public <K extends EObject> TypeQuery<K> getFeaturesQuery(EReference eReference, Class<K> attributeTypeClass)
	{
		return new TypeQuery<K>(transform(eReference, attributeTypeClass));
	}

}
