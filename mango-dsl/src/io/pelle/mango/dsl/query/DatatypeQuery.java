package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Datatype;

import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Optional;

public class DatatypeQuery<T extends Datatype> {

	private T datatype;

	protected DatatypeQuery(T datatype) {
		this.datatype = datatype;
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
	
	protected Optional<Object> getStructuralFeature(EStructuralFeature structuralFeature)
	{
		for(T datatype : getDatatypeHierarchy())
		{
			if (datatype.eGet(structuralFeature) != null)
			{
				return Optional.of(datatype.eGet(structuralFeature));
			}
		}
		
		return Optional.absent();
	}
	
	protected ArrayList<T> getDatatypeHierarchy() {
		
		ArrayList<T> datatypeHierarchy = new ArrayList<T>();

		datatypeHierarchy.add(datatype);

		T refDatatype = (T) getDatatypeExtends(datatype);

		while (refDatatype != null) {
			datatypeHierarchy.add(refDatatype);

			refDatatype = (T) getDatatypeExtends(datatype);
		}

		return datatypeHierarchy;
	}
	
	protected T getDatatype()
	{
		return datatype;
	}
}
