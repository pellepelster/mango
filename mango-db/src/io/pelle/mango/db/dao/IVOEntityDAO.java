package io.pelle.mango.db.dao;

import java.util.List;

import com.google.common.base.Optional;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

public interface IVOEntityDAO<T extends IVOEntity> {

	Class<T> getVOEntityClass();

	T create(T entity);

	T save(T entity);

	T read(long id, Class<T> entityClass);

	List<T> filter(SelectQuery<T> query);

	Optional<T> read(SelectQuery<T> query);

	void deleteAll(Class<T> entityClass);

	void delete(T entity);

	long count(CountQuery<T> query);

	long aggregate(AggregateQuery<T> query);

	Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey);

	void deleteQuery(DeleteQuery<T> query);

}
