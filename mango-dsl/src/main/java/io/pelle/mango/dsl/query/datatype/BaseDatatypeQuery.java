package io.pelle.mango.dsl.query.datatype;

import io.pelle.mango.dsl.mango.Datatype;

import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Optional;

public class BaseDatatypeQuery<T extends Datatype> {

	private Optional<T> datatype;

	protected BaseDatatypeQuery(T datatype) {
		this.datatype = Optional.fromNullable(datatype);
	}

	@SuppressWarnings("unchecked")
	private T getDatatypeExtends(T datatype) {
		try {
			return (T) PropertyUtils.getProperty(datatype, "extends");
		} catch (Exception e) {
			// ignore nonexisting refs
		}

		return null;
	}

	protected Optional<Object> getStructuralFeature(EStructuralFeature structuralFeature) {

		for (T datatype : getDatatypeHierarchy()) {
			if (datatype.eGet(structuralFeature) != null) {
				return Optional.of(datatype.eGet(structuralFeature));
			}
		}

		return Optional.absent();
	}

	protected ArrayList<T> getDatatypeHierarchy() {

		ArrayList<T> datatypeHierarchy = new ArrayList<T>();

		if (datatype.isPresent()) {
			datatypeHierarchy.add(datatype.get());

			T refDatatype = (T) getDatatypeExtends(datatype.get());

			while (refDatatype != null) {
				datatypeHierarchy.add(refDatatype);

				refDatatype = (T) getDatatypeExtends(datatype.get());
			}
		}

		return datatypeHierarchy;
	}

	protected Optional<T> getDatatype() {
		return datatype;
	}
}
