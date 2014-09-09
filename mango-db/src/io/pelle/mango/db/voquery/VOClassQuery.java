package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.util.BeanUtils;

public class VOClassQuery {

	private Class<? extends IVOEntity> voEntityClass;

	public VOClassQuery(Class<? extends IVOEntity> voClass) {
		this.voEntityClass = voClass;
	}

	public static VOClassQuery createQuery(Class<? extends IVOEntity> voEntityClass) {
		return new VOClassQuery(voEntityClass);
	}

	public static VOClassQuery createQuery(IBaseVO vo) {
		return new VOClassQuery(vo.getClass());
	}

	public AttributesDescriptorQuery<?> attributesDescriptors() {
		return AttributesDescriptorQuery.createQuery(voEntityClass, BeanUtils.getAttributeDescriptors(voEntityClass));
	}

}
