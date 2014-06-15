package io.pelle.mango.db.test.mockup;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IEntityVOMapper;

import java.util.List;

public class EntityVOMapper implements IEntityVOMapper {

	public static EntityVOMapper INSTANCE = new EntityVOMapper();

	private EntityVOMapper() {

	}

	@Override
	public List<Class<? extends IBaseEntity>> getEntityClasses() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Class<?> getMappedClass(Class<?> clazz) {
		return clazz;
	}

	@Override
	public List<Class<? extends IBaseVO>> getVOClasses() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Class<? extends IBaseEntity> getEntityClass(Class<?> clazz) {
		return (Class<? extends IBaseEntity>) clazz;
	}

}
