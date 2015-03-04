package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;

public interface IVOEntityDAO<VOENTITYTYPE extends IVOEntity> {

	Class<VOENTITYTYPE> getVOEntityClass();

	VOENTITYTYPE create(VOENTITYTYPE entity);

	VOENTITYTYPE save(VOENTITYTYPE entity);

	VOENTITYTYPE read(long id, Class<VOENTITYTYPE> entityClass);

}
