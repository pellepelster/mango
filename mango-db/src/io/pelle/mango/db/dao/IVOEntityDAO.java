package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.List;

import com.google.common.base.Optional;

public interface IVOEntityDAO<T extends IVOEntity> {

	Class<T> getVOEntityClass();

	T create(T entity);

	T save(T entity);

	T read(long id);

	Optional<T> read(SelectQuery<T> query);

	List<T> getAll();

	List<T> filter(SelectQuery<T> query);

	void deleteAll();

	void delete(T entity);

	void delete(long id);

	void deleteQuery(DeleteQuery<T> query);

	long count(CountQuery<T> query);

	long count();

	long aggregate(AggregateQuery<T> query);

	Optional<T> getByNaturalKey(String naturalKey);

}
