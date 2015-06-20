package io.pelle.mango.dsl;

import io.pelle.mango.dsl.mango.Datatype;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryControl;
import io.pelle.mango.dsl.mango.DictionaryReferenceControl;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.ModelRoot;
import io.pelle.mango.dsl.mango.PackageDeclaration;
import io.pelle.mango.dsl.query.ModelQuery;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class ModelUtil {

	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	private static final URIConverter uriConverter = new ExtensibleURIConverterImpl();

	public static PackageDeclaration getSingleRootPackage(Model model) {
		return ModelQuery.createQuery(model).getRootPackages().getSingleResult();
	}

	public static Model getModelFromFile(URI uri) {
		return (Model) getModelRootFromFile(uri);
	}

	private static ModelRoot getModelRootFromFile(URI uri) {
		ResourceSet resourceSet = new ResourceSetImpl();

		Resource resource = null;
		try {
			resource = resourceSet.createResource(uri);
			resource.load(uriConverter.createInputStream(uri), resourceSet.getLoadOptions());
		} catch (Exception e) {
			LOG.error(String.format("error creating resource for '%s'", resource.getURI().toString()), e);
		}

		if (!resource.getContents().isEmpty()) {
			if (resource.getContents().get(0) instanceof ModelRoot) {
				return (ModelRoot) resource.getContents().get(0);
			} else if (resource.getContents().get(0) instanceof PackageDeclaration) {
				return (PackageDeclaration) resource.getContents().get(0);
			} else {
				throw new RuntimeException(String.format("unknown model root '%s'", resource.getContents().get(0).eClass().toString()));
			}
		}

		return null;
	}

	public static Object getRefObject(Object dictionaryControl) {
		try {
			return PropertyUtils.getProperty(dictionaryControl, "ref");
		} catch (Exception e) {
			// ignore nonexisting refs
		}

		return null;
	}

	public static Dictionary getReferenceControlDictionary(DictionaryReferenceControl dictionaryControl) {
		return getControlAttribute(dictionaryControl, new Function<DictionaryReferenceControl, Dictionary>() {
			@Override
			public Dictionary apply(DictionaryReferenceControl dictionaryControl) {
				return dictionaryControl.getDictionary();
			}
		});
	}

	public static String getControlName(DictionaryControl dictionaryControl) {
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, String>() {
			@Override
			public String apply(DictionaryControl dictionaryControl) {
				return dictionaryControl.getName();
			}
		});
	}

	public static EntityAttribute getEntityAttribute(DictionaryControl dictionaryControl) {
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, EntityAttribute>() {
			@Override
			public EntityAttribute apply(DictionaryControl dictionaryControl) {
				if (dictionaryControl.getBaseControl() != null) {
					return dictionaryControl.getBaseControl().getEntityattribute();
				} else {
					return null;
				}
			}
		});
	}

	public static Datatype getTypeDatatype(DictionaryControl dictionaryControl) {
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, Datatype>() {
			@Override
			public Datatype apply(DictionaryControl dictionaryControl) {
				if (dictionaryControl.getBaseControl() != null) {
					return dictionaryControl.getBaseControl().getType();
				} else {
					return null;
				}
			}
		});
	}

	public static Object getBaseControlFeature(DictionaryControl dictionaryControl, final EStructuralFeature structuralFeature) {
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, Object>() {
			@Override
			public Object apply(DictionaryControl dictionaryControl) {
				if (dictionaryControl.getBaseControl().eGet(structuralFeature) != null) {
					return dictionaryControl.getBaseControl().eGet(structuralFeature);
				} else {
					return null;
				}
			}
		});
	}

	public static <T, ControlyType extends DictionaryControl> T getControlAttribute(ControlyType dictionaryControl, Function<ControlyType, T> function) {
		ArrayList<ControlyType> controlHierarchy = getControlHierarchy(dictionaryControl);

		return Iterables.getFirst(Iterables.filter(Iterables.transform(controlHierarchy, function), Predicates.notNull()), null);
	}

	@SuppressWarnings("unchecked")
	public static <DatatypeType extends Datatype> ArrayList<DatatypeType> getDatatypeHierarchy(DatatypeType datatypeType) {

		ArrayList<DatatypeType> datatypeHierarchy = new ArrayList<DatatypeType>();

		datatypeHierarchy.add(datatypeType);

		DatatypeType refDatatype = (DatatypeType) getRefObject(datatypeType);

		while (refDatatype != null) {
			datatypeHierarchy.add(refDatatype);

			refDatatype = (DatatypeType) getRefObject(refDatatype);
		}

		return datatypeHierarchy;
	}

	@SuppressWarnings("unchecked")
	public static <ControlyType extends DictionaryControl> ArrayList<ControlyType> getControlHierarchy(ControlyType dictionaryControl) {

		ArrayList<ControlyType> controlHierarchy = new ArrayList<ControlyType>();

		controlHierarchy.add(dictionaryControl);

		ControlyType refControl = (ControlyType) getRefObject(dictionaryControl);

		while (refControl != null) {
			controlHierarchy.add(refControl);

			refControl = (ControlyType) getRefObject(refControl);
		}

		return controlHierarchy;
	}

	public static List<Entity> getEntityHierarchy(Entity entity) {
		List<Entity> entityHierarchy = new ArrayList<Entity>();

		entityHierarchy.add(entity);

		Entity entityExtends = entity.getExtends();

		while (entityExtends != null) {
			entityHierarchy.add(entityExtends);
			entityExtends = entityExtends.getExtends();
		}

		return entityHierarchy;
	}

	public static <T> T getParentWithType(EObject eObject, Class<T> parentClass) {

		EObject current = eObject;

		while (current.eContainer() != null) {

			if (parentClass.isAssignableFrom(current.getClass())) {
				return (T) current;
			}

			current = current.eContainer();
		}

		return null;
	}

	public static EObject getRoot(EObject eObject) {

		EObject current = eObject;

		while (current.eContainer() != null) {
			current = current.eContainer();
		}

		return current;
	}

	public static Model getRootModel(EObject eObject) {

		EObject root = getRoot(eObject);

		if (root instanceof ModelRoot) {
			return ((ModelRoot) root).getModelRoot();
		}

		throw new RuntimeException("unexpected type '" + eObject.toString() + "'");
	}
}