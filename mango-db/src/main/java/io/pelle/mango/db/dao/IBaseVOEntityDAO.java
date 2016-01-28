package io.pelle.mango.db.dao;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

public interface IBaseVOEntityDAO<VOENTITYTYPE extends IVOEntity> {

	<T extends VOENTITYTYPE> T getNewVO(String className, Map<String, String> properties);

	<T extends VOENTITYTYPE> T create(T entity);

	<T extends VOENTITYTYPE> T save(T entity);

	<T extends VOENTITYTYPE> T read(long id, Class<T> entityClass);

	<T extends VOENTITYTYPE> List<T> filter(SelectQuery<T> query);

	<T extends VOENTITYTYPE> List<T> getAll(Class<T> entityClass);

	<T extends VOENTITYTYPE> Optional<T> read(SelectQuery<T> query);

	<T extends VOENTITYTYPE> void delete(T entity);

	<T extends VOENTITYTYPE> void deleteAll(Class<T> entityClass);

	<T extends VOENTITYTYPE> void delete(Class<T> entityClass, long id);

	<T extends VOENTITYTYPE> void deleteQuery(DeleteQuery<T> query);

	<T extends VOENTITYTYPE> long count(CountQuery<T> query);

	<T extends VOENTITYTYPE> long count(Class<T> entityClass);

	<T extends VOENTITYTYPE> long aggregate(AggregateQuery<T> query);

	<T extends VOENTITYTYPE> List<T> searchByNaturalKey(Class<T> entityClass, String naturalKey);

}
