package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityAttributeType;
import io.pelle.mango.dsl.mango.EntityDataType;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;

import java.util.ArrayList;
import java.util.List;

public class EntityQuery {
	
	private Entity entity;

	public EntityQuery(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public static EntityQuery createQuery(Entity entity) {
		return new EntityQuery(entity);
	}

	public List<Entity> getReferencedEntities() {
		
		List<Entity> entities = new ArrayList<Entity>();

		for (EntityEntityAttribute entityEntityAttribute : TypeQuery.createQuery(entity.getAttributes()).filterByType(EntityEntityAttribute.class))
		{
			entities.add(getEntity(entityEntityAttribute));
		}
		
		
		return entities;
	}
	
	public static Entity getEntity(EntityAttribute entityAttribute) {
		throw new RuntimeException(String.format("EntityAttribute '%s' not supported", entityAttribute.getClass()));
	}

	
	public static Entity getEntity(EntityEntityAttribute entityEntityAttribute) {
		return getEntity(entityEntityAttribute.getType());
	}

	public static Entity getEntity(EntityAttributeType entityAttributeType) {

		/**
		 * EntityAttributeType:
	 	 *      EntityDataType | Entity;
		 */
		
		if (EntityDataType.class.isAssignableFrom(entityAttributeType.getClass())) {
			return ((EntityDataType) entityAttributeType).getEntity();
		}

		if (Entity.class.isAssignableFrom(entityAttributeType.getClass())) {
			return ((Entity) entityAttributeType);
		}

		throw new RuntimeException(String.format("EntityAttributeType '%s' not supported", entityAttributeType.getClass()));
	}

}
