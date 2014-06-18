package io.pelle.mango.dsl.generator.util;

import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class GeneratorUtil {

	public static String getEntityName(Entity entity) {
		return entity.getName();
	}

	public static Entity getParentEntity(EntityAttribute entityAttribute) {
		return (Entity) entityAttribute.eContainer();
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
