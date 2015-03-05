package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseVO;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractVODAO<VOTYPE extends IBaseVO> implements IVOEntityDAO<VOTYPE> {

	@Autowired
	private BaseVODAO baseVODAO;

	private final Class<VOTYPE> voEntityClass;

	public AbstractVODAO(Class<VOTYPE> voEntityClass) {
		this.voEntityClass = voEntityClass;
	}

	@Override
	public Class<VOTYPE> getVOEntityClass() {
		return voEntityClass;
	}

	@Override
	public VOTYPE create(VOTYPE entity) {
		return baseVODAO.create(entity);
	}

	@Override
	public VOTYPE save(VOTYPE entity) {
		return baseVODAO.save(entity);
	}

	@Override
	public VOTYPE read(long id, Class<VOTYPE> entityClass) {
		return baseVODAO.read(id, entityClass);
	}

}
