package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityType;

import java.util.ArrayList;
import java.util.List;

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
