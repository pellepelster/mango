package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseVO;

public interface IVODAOCallback {

	<VOType extends IBaseVO> void onAdd(VOType vo);

	<VOType extends IBaseVO> void onUpdate(VOType vo);

	void onDeleteAll(Class<? extends IBaseVO> voClass);

	<T extends IBaseVO> void onDelete(T vo);

}
