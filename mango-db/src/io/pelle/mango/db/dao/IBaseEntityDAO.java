package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;

public interface IBaseEntityDAO extends IBaseVOEntityDAO<IBaseEntity> {

	void registerCallback(IDAOCallback<?> callback);

}
