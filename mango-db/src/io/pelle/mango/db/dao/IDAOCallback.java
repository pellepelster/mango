package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;

import java.util.Map;

public interface IDAOCallback<VOType extends IVOEntity> {

	void onCreate(VOType voEntity);

	void onUpdate(VOType voEntity);

	void onDeleteAll(Class<? extends IVOEntity> voEntityClass);

	void onDelete(VOType voEntity);

	void onNewInstance(VOType voEntity, Map<String, String> properties);
}
