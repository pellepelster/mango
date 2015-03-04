package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEntityDAO<ENTITYTYPE extends IBaseEntity> implements IVOEntityDAO<ENTITYTYPE> {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	private final Class<ENTITYTYPE> voEntityClass;

	public AbstractEntityDAO(Class<ENTITYTYPE> voEntityClass) {
		this.voEntityClass = voEntityClass;
	}

	@Override
	public Class<ENTITYTYPE> getVOEntityClass() {
		return voEntityClass;
	}

	@Override
	public ENTITYTYPE create(ENTITYTYPE entity) {
		return baseEntityDAO.create(entity);
	}

	@Override
	public ENTITYTYPE save(ENTITYTYPE entity) {
		return baseEntityDAO.save(entity);
	}

	@Override
	public ENTITYTYPE read(long id, Class<ENTITYTYPE> entityClass) {
		return baseEntityDAO.read(id, entityClass);
	}

}
