package io.pelle.mango.dsl.query;

import org.eclipse.emf.ecore.EObject;

public class BaseEObjectQuery<T extends EObject>
{
	private T eObject;

	public BaseEObjectQuery(T eObject)
	{
		super();
		this.eObject = eObject;
	}

	public T getObject()
	{
		return this.eObject;
	}

}
