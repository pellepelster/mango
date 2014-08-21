package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.db.util.BeanUtils;

public class VOClassQuery {

	private Class<? extends IBaseVO> voClass;

	public VOClassQuery(Class<? extends IBaseVO> voClass) {
		this.voClass = voClass;
	}

	public static VOClassQuery createQuery(Class<? extends IBaseVO> voClass) {
		return new VOClassQuery(voClass);
	}

	public static VOClassQuery createQuery(IBaseVO vo) {
		return new VOClassQuery(vo.getClass());
	}

	public AttributesDescriptorQuery<?> attributesDescriptors() {
		return AttributesDescriptorQuery.createQuery(voClass, BeanUtils.getAttributeDescriptors(voClass));
	}

}
