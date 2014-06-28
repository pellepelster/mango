package io.pelle.mango.dsl.emf;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Optional;

public class EmfModelQuery {

	public static <T extends EObject> EObjectQuery<T> createEObjectQuery(T eObject) {
		return new EObjectQuery<T>(eObject);
	}

	@SuppressWarnings("unchecked")
	public static <T extends EObject> Optional<T> getParentEObject(EObject eObject, Class<T> eObjectClass) {

		EObject currentEObject = eObject;

		int count = 0;

		while (currentEObject != null && !eObjectClass.isAssignableFrom(currentEObject.getClass())) {
			// see EcoreUtil.getRootContainer(EObject)
			if (++count > 100000) {
				break;
			}
			currentEObject = currentEObject.eContainer();
		}

		return Optional.fromNullable((T) currentEObject);
	}

}
