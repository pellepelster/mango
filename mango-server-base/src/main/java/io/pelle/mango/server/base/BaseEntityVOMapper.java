package io.pelle.mango.server.base;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IEntityVOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseEntityVOMapper implements IEntityVOMapper {

	protected abstract Map<Class<?>, Class<?>> getClassMappings();

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends IBaseEntity>> getEntityClasses() {
		List<Class<? extends IBaseEntity>> result = new ArrayList<Class<? extends IBaseEntity>>();

		for (Class<?> clazz : getClassMappings().values()) {
			if (IBaseEntity.class.isAssignableFrom(clazz)) {
				result.add((Class<? extends IBaseEntity>) clazz);
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends IBaseVO>> getVOClasses() {
		List<Class<? extends IBaseVO>> result = new ArrayList<Class<? extends IBaseVO>>();

		for (Class<?> clazz : getClassMappings().values()) {
			if (IBaseVO.class.isAssignableFrom(clazz)) {
				result.add((Class<? extends IBaseVO>) clazz);
			}
		}

		return result;
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

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends IBaseEntity> getEntityClass(Class<?> clazz) {
		if (IBaseEntity.class.isAssignableFrom(clazz)) {
			return (Class<? extends IBaseEntity>) clazz;
		} else {
			return (Class<? extends IBaseEntity>) getMappedClass(clazz);
		}
	}

}
