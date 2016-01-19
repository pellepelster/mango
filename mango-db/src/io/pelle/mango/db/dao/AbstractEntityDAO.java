package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.LongAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.voquery.EntityVOClassQuery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

public abstract class AbstractEntityDAO<T extends IBaseEntity> implements IVOEntityDAO<T> {

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	private final Class<T> voEntityClass;

	public AbstractEntityDAO(Class<T> voEntityClass) {
		this.voEntityClass = voEntityClass;
	}

	@Override
	public T getNewEntity(String className, Map<String, String> properties) {
		return baseEntityDAO.getNewVO(className, properties);
	}
	
	@Override
	public Class<T> getVOEntityClass() {
		return voEntityClass;
	}

	@Override
	public T create(T entity) {
		return baseEntityDAO.create(entity);
	}

	@Override
	public T save(T entity) {
		return baseEntityDAO.save(entity);
	}

	@Override
	public T read(long id) {
		return baseEntityDAO.read(id, voEntityClass);
	}

	@Override
	public List<T> filter(SelectQuery<T> query) {
		return baseEntityDAO.filter(query);
	}

	@Override
	public Optional<T> read(SelectQuery<T> query) {
		return baseEntityDAO.read(query);
	}

	@Override
	public void deleteAll() {
		baseEntityDAO.deleteAll(voEntityClass);
	}

	@Override
	public void delete(T entity) {
		baseEntityDAO.delete(entity);
	}

	@Override
	public long count(CountQuery<T> query) {
		return baseEntityDAO.count(query);
	}

	@Override
	public long aggregate(AggregateQuery<T> query) {
		return baseEntityDAO.aggregate(query);
	}

	@Override
	public List<T> getByNaturalKey(String naturalKey) {
		return baseEntityDAO.searchByNaturalKey(voEntityClass, naturalKey);
	}

	@Override
	public void deleteQuery(DeleteQuery<T> query) {
		baseEntityDAO.deleteQuery(query);
	}

	@Override
	public List<T> getAll() {
		SelectQuery<T> query = SelectQuery.selectFrom(voEntityClass);
		return filter(query);
	}

	@Override
	public void delete(long id) {
		baseEntityDAO.delete(voEntityClass, id);
	}

	@Override
	public long count() {
		return baseEntityDAO.count(voEntityClass);
	}

	protected BaseEntityDAO getBaseEntityDAO() {
		return baseEntityDAO;
	}

}
