package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;

public interface IEntityCallback {

	void beforeCreate(IBaseEntity entity);

	void beforeSave(IBaseEntity entity);

	boolean supports(Class<? extends IBaseEntity> entityClass);

}
