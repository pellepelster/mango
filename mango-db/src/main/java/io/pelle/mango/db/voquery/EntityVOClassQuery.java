package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.util.BeanUtils;

public class EntityVOClassQuery {

	private Class<? extends IVOEntity> voEntityClass;

	public EntityVOClassQuery(Class<? extends IVOEntity> voClass) {
		this.voEntityClass = voClass;
	}

	public static EntityVOClassQuery createQuery(Class<? extends IVOEntity> voEntityClass) {
		return new EntityVOClassQuery(voEntityClass);
	}

	public static EntityVOClassQuery createQuery(IBaseVO vo) {
		return new EntityVOClassQuery(vo.getClass());
	}

	public AttributesDescriptorQuery<?> attributesDescriptors() {
		return AttributesDescriptorQuery.createQuery(voEntityClass, BeanUtils.getAttributeDescriptors(voEntityClass));
	}

}
