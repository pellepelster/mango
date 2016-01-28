package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.query.functions.FunctionEObjectTypeSelect;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class TypeQuery<T extends EObject> extends BaseEObjectCollectionQuery<T>
{

	public TypeQuery(Collection<T> eObjects)
	{
		super(eObjects);
	}
	
	public static <T extends EObject> TypeQuery<T> createQuery(Collection<T> eObjects) {
		return new TypeQuery<T>(eObjects);
	}

	public <K extends EObject> Collection<K> filterByType(Class<K> clazz)
	{
		Collection<K> result = Collections2.transform(getCollection(), FunctionEObjectTypeSelect.getFunction(clazz));
		return Collections2.filter(result, Predicates.notNull());
	}

}
