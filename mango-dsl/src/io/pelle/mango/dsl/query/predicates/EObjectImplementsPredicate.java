package io.pelle.mango.dsl.query.predicates;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Predicate;

public class EObjectImplementsPredicate implements Predicate<EObject>
{
	private Class<?>[] classes;

	public static EObjectImplementsPredicate create(Class<?>... classes)
	{
		return new EObjectImplementsPredicate(classes);
	}

	private EObjectImplementsPredicate(Class<?>[] classes)
	{
		super();
		this.classes = classes;
	}

	@Override
	public boolean apply(EObject eObject)
	{
		for (Class<?> clazz : this.classes)
		{
			if (clazz.isAssignableFrom(eObject.getClass()))
			{
				return true;
			}
		}

		return false;
	}
}
