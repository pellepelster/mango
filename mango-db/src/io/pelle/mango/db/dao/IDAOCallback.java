package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;

public interface IDAOCallback {

	<VOType extends IVOEntity> void onCreate(VOType voEntity);

	<VOType extends IVOEntity> void onUpdate(VOType voEntity);

	void onDeleteAll(Class<? extends IVOEntity> voEntityClass);

	<VOType extends IVOEntity> void onDelete(VOType voEntity);

}
