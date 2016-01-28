package io.pelle.mango.dsl.emf;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Optional;

public class EObjectQuery<T extends EObject> {

	private Optional<T> eObject;

	public EObjectQuery(T eObject) {
		this.eObject = Optional.fromNullable(eObject);
	}

	public EObjectQuery(Optional<T> eObject) {
		this.eObject = eObject;
	}

	public boolean hasMatch() {
		return eObject.isPresent();
	}

	public <P extends EObject> EObjectQuery<P> getParentByType(Class<P> parentType) {
		if (eObject.isPresent()) {
			Optional<P> parent = EmfModelQuery.getParentEObject(eObject.get(), parentType);
			return new EObjectQuery<P>(parent);
		} else {
			return new EObjectQuery<P>(Optional.<P> absent());
		}
	}

	public T getMatch() {
		return eObject.get();
	}
}
