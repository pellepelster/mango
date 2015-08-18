package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;

public interface IVOEntityDecorator {

	void decorateVO(IVOEntity vo);

	/**
	 * True if a given vo class is supported
	 * 
	 * @param clazz
	 * @return
	 */
	boolean supports(Class<? extends IVOEntity> voClass);

}
