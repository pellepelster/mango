package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Entity;

public class EntityQuery
{
	private Entity entity;

	public EntityQuery(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return this.entity;
	}


}
