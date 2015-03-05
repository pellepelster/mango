package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

public abstract class AbstractVODAO<VOTYPE extends IBaseVO> implements IVOEntityDAO<VOTYPE> {

	@Autowired
	private BaseVODAO baseVODAO;

	private final Class<VOTYPE> voEntityClass;

	@Override
	public Class<VOTYPE> getVOEntityClass() {
		return voEntityClass;
	}

	public AbstractVODAO(Class<VOTYPE> voEntityClass) {
		this.voEntityClass = voEntityClass;
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

	@Override
	public List<VOTYPE> filter(SelectQuery<VOTYPE> query) {
		return baseVODAO.filter(query);
	}

	@Override
	public Optional<VOTYPE> read(SelectQuery<VOTYPE> query) {
		return baseVODAO.read(query);
	}

	@Override
	public void deleteAll(Class<VOTYPE> entityClass) {
		baseVODAO.deleteAll(entityClass);
	}

	@Override
	public void delete(VOTYPE entity) {
		baseVODAO.delete(entity);
	}

	@Override
	public long count(CountQuery<VOTYPE> query) {
		return baseVODAO.count(query);
	}

	@Override
	public long aggregate(AggregateQuery<VOTYPE> query) {
		return baseVODAO.aggregate(query);
	}

	@Override
	public Optional<VOTYPE> getByNaturalKey(Class<VOTYPE> entityClass, String naturalKey) {
		return baseVODAO.getByNaturalKey(entityClass, naturalKey);
	}

	@Override
	public void deleteQuery(DeleteQuery<VOTYPE> query) {
		baseVODAO.deleteQuery(query);
	}

}
