package io.pelle.mango.client.base.vo;

import java.util.List;

public interface IEntityVOMapper {
	List<Class<? extends IBaseEntity>> getEntityClasses();

	Class<?> getMappedClass(Class<?> clazz);

	Class<? extends IBaseEntity> getEntityClass(Class<?> clazz);

	Class<? extends IBaseVO> getVOClass(Class<?> clazz);

	List<Class<? extends IBaseVO>> getVOClasses();
}
