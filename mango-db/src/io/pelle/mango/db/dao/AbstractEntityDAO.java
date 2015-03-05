package io.pelle.mango.db.dao;

import java.util.List;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

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
	public Class<T> getVOEntityClass() {
		return voEntityClass;
	}
	
	@Override
	public  T create(T entity) {
		return baseEntityDAO.create(entity);
	}

	@Override
	public T save(T entity) {
		return baseEntityDAO.save(entity);
	}

	@Override
	public T read(long id, Class<T> entityClass) {
		return baseEntityDAO.read(id, entityClass);
	}

	@Override
	public List<T> filter(SelectQuery<T> query) {
		return baseEntityDAO.filter(query);
	}

	@Override
	public  Optional<T> read(SelectQuery<T> query) {
		return baseEntityDAO.read(query);
	}

	@Override
	public void deleteAll(Class<T> entityClass) {
		baseEntityDAO.deleteAll(entityClass);
	}

	@Override
	public void delete(T entity) {
		baseEntityDAO.delete(entity);
	}

	@Override
	public  long count(CountQuery<T> query) {
		return baseEntityDAO.count(query);
	}

	@Override
	public  long aggregate(AggregateQuery<T> query) {
		return baseEntityDAO.aggregate(query);
	}

	@Override
	public  Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey) {
		return baseEntityDAO.getByNaturalKey(entityClass, naturalKey);
	}

	@Override
	public void deleteQuery(DeleteQuery<T> query) {
		baseEntityDAO.deleteQuery(query);
	}


}
