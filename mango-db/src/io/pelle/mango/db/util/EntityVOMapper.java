package io.pelle.mango.db.util;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public final class EntityVOMapper implements IEntityVOMapper {

	private static EntityVOMapper instance;

	public static EntityVOMapper getInstance() {
		if (instance == null) {
			instance = new EntityVOMapper();
		}
		return instance;
	}

	@Autowired
	private List<IEntityVOMapper> entityVOMappers = new ArrayList<IEntityVOMapper>();

	private EntityVOMapper() {
	}

	public Class<?> getMappedClass(Class<?> clazz) {
		for (IEntityVOMapper entityVOMapper : entityVOMappers) {
			if (entityVOMapper.getMappedClass(clazz) != null) {
				return entityVOMapper.getMappedClass(clazz);
			}
		}

		return null;
	}

	public Class<?> getMappedClass(String className) {
		try {
			return getMappedClass(Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("class '%s' not found", className));
		}
	}

	@SuppressWarnings("unchecked")
	public Class<? extends IBaseVO> getMappedVOClass(Class<? extends IBaseEntity> entityClass) {
		return (Class<? extends IBaseVO>) getMappedClass(entityClass);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends IBaseEntity> getMappedEntityClass(Class<? extends IVOEntity> voEntityClass) {

		if (IBaseEntity.class.isAssignableFrom(voEntityClass)) {
			return (Class<? extends IBaseEntity>) voEntityClass;
		} else {
			return (Class<? extends IBaseEntity>) getMappedClass(voEntityClass);
		}

	}

	public List<Class<? extends IBaseVO>> getVOClasses() {

		List<Class<? extends IBaseVO>> result = new ArrayList<Class<? extends IBaseVO>>();

		for (IEntityVOMapper entityVOMapper : entityVOMappers) {
			result.addAll(entityVOMapper.getVOClasses());
		}

		return result;
	}

	public void setEntityVOMappers(List<IEntityVOMapper> entityVOMappers) {

		for (IEntityVOMapper entityVOMapper : entityVOMappers) {
			if (this != entityVOMapper) {
				this.entityVOMappers.add(entityVOMapper);
			}
		}
	}

	@Override
	public List<Class<? extends IBaseEntity>> getEntityClasses() {
		List<Class<? extends IBaseEntity>> result = new ArrayList<Class<? extends IBaseEntity>>();

		for (IEntityVOMapper entityVOMapper : entityVOMappers) {
			result.addAll(entityVOMapper.getEntityClasses());
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends IBaseEntity> getEntityClass(Class<?> clazz) {
		if (IBaseEntity.class.isAssignableFrom(clazz)) {
			return (Class<? extends IBaseEntity>) clazz;
		} else {
			return (Class<? extends IBaseEntity>) getMappedClass(clazz);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends IBaseVO> getVOClass(Class<?> clazz) {
		if (IBaseVO.class.isAssignableFrom(clazz)) {
			return (Class<? extends IBaseVO>) clazz;
		} else {
			return (Class<? extends IBaseVO>) getMappedClass(clazz);
		}
	}

}
