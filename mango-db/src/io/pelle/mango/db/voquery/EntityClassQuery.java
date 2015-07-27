package io.pelle.mango.db.voquery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import io.pelle.mango.client.base.vo.IBaseEntity;

public class EntityClassQuery {

	private Class<? extends IBaseEntity> entityClass;

	public EntityClassQuery(Class<? extends IBaseEntity> entityClass) {
		this.entityClass = entityClass;
	}

	public static EntityClassQuery createQuery(Class<? extends IBaseEntity> voEntityClass) {
		return new EntityClassQuery(voEntityClass);
	}

	public static EntityClassQuery createQuery(IBaseEntity entity) {
		return new EntityClassQuery(entity.getClass());
	}

	public List<String> getElementCollections() {

		List<String> result = new ArrayList<>();

		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(ElementCollection.class) && field.isAnnotationPresent(CollectionTable.class)) {
				String tableName = field.getAnnotation(CollectionTable.class).name();
				result.add(tableName);
			}
		}

		return result;
	}

	public List<String> getOneToManyJoinTables() {

		List<String> result = new ArrayList<>();

		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(JoinTable.class) && field.isAnnotationPresent(OneToMany.class)) {
				String tableName = field.getAnnotation(JoinTable.class).name();
				result.add(tableName);
			}
		}

		return result;
	}

}
