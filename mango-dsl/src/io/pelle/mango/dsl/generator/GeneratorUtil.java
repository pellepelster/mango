package io.pelle.mango.dsl.generator;

import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

public class GeneratorUtil {

	public static String getEntityName(Entity entity) {
		return entity.getName();
	}

	public static Entity getParentEntity(EntityAttribute entityAttribute) {
		return (Entity) entityAttribute.eContainer();
	}

	public static EObject getRoot(EObject eObject) {

		EObject current = eObject;

		while (current.eContainer() != null) {
			current = current.eContainer();
		}

		return current;
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

	public static boolean hasOneToOneEntityAttribute(Entity entity) {
		return Iterables.any(entity.getAttributes(), new Predicate<EntityAttribute>() {

			@Override
			public boolean apply(EntityAttribute entityAttribute) {

				if (entityAttribute instanceof EntityEntityAttribute) {
					EntityEntityAttribute entityEntityAttribute = (EntityEntityAttribute) entityAttribute;

					switch (entityEntityAttribute.getCardinality()) {
					case ONETOONE:
						return true;
					default:
						return false;
					}
				}
				return false;
			}
		});
	}
}
