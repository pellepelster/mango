package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.query.functions.FunctionEReferenceTypeSelect;
import io.pelle.mango.dsl.query.functions.FunctionStructuralFeatureTypeSelect;
import io.pelle.mango.dsl.query.predicates.EObjectStructuralFeatureEqualsPredicate;
import io.pelle.mango.server.base.query.BaseCollectionQuery;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class BaseEObjectCollectionQuery<T extends EObject> extends BaseCollectionQuery<T>
{
	public BaseEObjectCollectionQuery(Collection<T> eObjects)
	{
		super(eObjects);
	}

	protected <K extends EObject> Collection<K> transform(EStructuralFeature eStructuralFeature, Class<K> typeClass)
	{
		Collection<K> result = Collections2.transform(getCollection(), FunctionStructuralFeatureTypeSelect.create(eStructuralFeature, typeClass));

		return Collections2.filter(result, Predicates.notNull());
	}

	protected <K extends EObject> Collection<K> transform(EReference eReference, Class<K> typeClass)
	{
		Collection<K> result = Collections2.transform(getCollection(), FunctionEReferenceTypeSelect.create(eReference, typeClass));

		return Collections2.filter(result, Predicates.notNull());
	}

	protected Collection<T> getByEStructuralFeature(EStructuralFeature eStructuralFeature, Object object)
	{
		return Collections2.filter(getCollection(), EObjectStructuralFeatureEqualsPredicate.create(eStructuralFeature, object));
	}

	protected T getSingleByEStructuralFeature(EStructuralFeature eStructuralFeature, Object object)
	{
		Collection<T> result = getByEStructuralFeature(eStructuralFeature, object);

		if (result.size() != 1)
		{
			throw new RuntimeException(String.format("%d EObjects match '%s' for '%s'", result.size(), eStructuralFeature.getName(), object.toString()));
		}
		else
		{
			return result.iterator().next();
		}
	}

}
