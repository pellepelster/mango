package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseVO;

public interface IVODAODecorator {

	void decorateVO(IBaseVO vo);

	/**
	 * True if a given vo class is supported
	 * 
	 * @param clazz
	 * @return
	 */
	boolean supports(Class<? extends IBaseVO> voClass);

}
