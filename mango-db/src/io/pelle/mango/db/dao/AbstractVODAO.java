package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.List;
import java.util.Map;

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
	public VOTYPE read(long id) {
		return baseVODAO.read(id, getVOEntityClass());
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
	public void deleteAll() {
		baseVODAO.deleteAll(getVOEntityClass());
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
	public List<VOTYPE> getByNaturalKey(String naturalKey) {
		return baseVODAO.searchByNaturalKey(getVOEntityClass(), naturalKey);
	}

	@Override
	public void deleteQuery(DeleteQuery<VOTYPE> query) {
		baseVODAO.deleteQuery(query);
	}

	@Override
	public List<VOTYPE> getAll() {
		return baseVODAO.getAll(getVOEntityClass());
	}

	@Override
	public void delete(long id) {
		baseVODAO.delete(getVOEntityClass(), id);
	}

	@Override
	public long count() {
		return baseVODAO.count(getVOEntityClass());
	}

	@Override
	public VOTYPE getNewEntity(String className, Map<String, String> properties) {
		return baseVODAO.getNewVO(className, properties);
	}
}
