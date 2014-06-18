package io.pelle.mango.dsl;

import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryControl;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityAttributeType;
import io.pelle.mango.dsl.mango.EntityDataType;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;
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
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

public class ModelUtil {
	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	private static final URIConverter uriConverter = new ExtensibleURIConverterImpl();

	// public static Collection<PackageDeclaration> getRootPackages(Model model)
	// {
	// return ModelQuery.createQuery(model).getRootPackages().getList();
	// }
	//
	public static PackageDeclaration getSingleRootPackage(Model model) {
		return ModelQuery.createQuery(model).getRootPackages().getSingleResult();
	}

	//
	// public static boolean hasSingleRootPackage(Model model) {
	// return ModelQuery.createQuery(model).getRootPackages().hasExactlyOne();
	// }
	/**
	 * EntityAttributeType: EntityDataType | Entity;
	 * 
	 * EntityEntityAttribute: 'entity' type=[EntityAttributeType|QualifiedName]
	 * (cardinality=Cardinality)? name=ID;
	 * 
	 * EntityType: 'entity' type=[EntityAttributeType|QualifiedName]
	 * (cardinality=Cardinality)?;
	 */

	public static Entity getEntity(EntityAttribute entityAttribute) {

		if (EntityEntityAttribute.class.isAssignableFrom(entityAttribute.getClass())) {

		}

		throw new RuntimeException(String.format("EntityAttribute '%s' not supported", entityAttribute.getClass()));
	}

	public static Entity getEntity(EntityEntityAttribute entityEntityAttribute) {
		return getEntity(entityEntityAttribute.getType());
	}

	public static Entity getEntity(EntityAttributeType entityAttributeType) {

		if (EntityDataType.class.isAssignableFrom(entityAttributeType.getClass())) {
			return getEntity(entityAttributeType);
		}

		if (Entity.class.isAssignableFrom(entityAttributeType.getClass())) {
			return getEntity(entityAttributeType);
		}

		throw new RuntimeException(String.format("EntityAttributeType '%s' not supported", entityAttributeType.getClass()));
	}

	public static Entity getEntity(EntityDataType entityDataType) {
		return entityDataType.getEntity();
	}

	public static Entity getEntity(Entity entity) {
		return entity;
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
			if (resource.getContents().get(0) instanceof Model) {
				return (Model) resource.getContents().get(0);
			} else if (resource.getContents().get(0) instanceof PackageDeclaration) {
				return (PackageDeclaration) resource.getContents().get(0);
			} else {
				throw new RuntimeException(String.format("unknown model root '%s'", resource.getContents().get(0).eClass().toString()));
			}
		}

		return null;
	}

	public static <T extends EObject> T getParentEObject(EObject eObject, Class<T> eObjectClass) {
		return getParentEObject(eObject, eObjectClass, true);
	}

	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getParentEObject(EObject eObject, Class<T> eObjectClass, boolean optional) {
		EObject currentEObject = eObject;

		int count = 0;
		while (currentEObject != null && !eObjectClass.isAssignableFrom(currentEObject.getClass())) {
			// see EcoreUtil.getRootContainer(EObject)
			if (++count > 100000) {
				break;
			}
			currentEObject = currentEObject.eContainer();
		}

		if (currentEObject != null) {
			return (T) currentEObject;
		} else {
			if (optional) {
				return null;
			} else {
				throw new RuntimeException(String.format("no parent object of type '%s' found for '%s'", eObjectClass.getName(), eObject.toString()));
			}

		}
	}

	public static Object getControlRef(Object dictionaryControl) {
		try {
			return PropertyUtils.getProperty(dictionaryControl, "ref");
		} catch (Exception e) {
			// ignore nonexisting refs
		}

		return null;
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

	public static <T> T getControlAttribute(DictionaryControl dictionaryControl, Function<DictionaryControl, T> function) {
		ArrayList<DictionaryControl> controlHierarchy = getControlHierarchy(dictionaryControl);

		return Iterables.getFirst(Iterables.filter(Iterables.transform(controlHierarchy, function), Predicates.notNull()), null);
	}

	@SuppressWarnings("unchecked")
	public static <ControlyType extends DictionaryControl> ArrayList<ControlyType> getControlHierarchy(ControlyType dictionaryControl) {
		ArrayList<ControlyType> controlHierarchy = new ArrayList<ControlyType>();

		controlHierarchy.add(dictionaryControl);

		ControlyType refControl = (ControlyType) getControlRef(dictionaryControl);

		while (refControl != null) {
			controlHierarchy.add(refControl);

			refControl = (ControlyType) getControlRef(refControl);
		}

		return controlHierarchy;
	}

	public static Dictionary getParentDictionary(EObject eObject) {
		return getParentEObject(eObject, Dictionary.class, false);
	}

	public static Entity getParentEntity(EObject eObject) {
		return getParentEObject(eObject, Entity.class, false);
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

	public static String getParentDictionaryName(EObject eObject) {
		return getParentDictionary(eObject).getName();
	}

	public static boolean isExtendedByOtherEntity(final Entity entity) {

		return Iterators.any(getRoot(entity).eAllContents(), new Predicate<EObject>() {

			@Override
			public boolean apply(EObject input) {

				if (input instanceof Entity) {
					Entity otherEntity = (Entity) input;
					return otherEntity.getExtends() != null && otherEntity.getExtends().equals(entity);
				}

				return false;
			}
		});
	}

	public static EObject getRoot(EObject eObject) {

		EObject current = eObject;

		while (current.eContainer() != null) {
			current = current.eContainer();
		}

		return current;
	}

}