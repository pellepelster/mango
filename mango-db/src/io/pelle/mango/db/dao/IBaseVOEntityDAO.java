package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.List;

import com.google.common.base.Optional;

public interface IBaseVOEntityDAO<VOENTITYTYPE extends IVOEntity> {

	<T extends VOENTITYTYPE> T create(T entity);

	<T extends VOENTITYTYPE> T save(T entity);

	<T extends VOENTITYTYPE> T read(long id, Class<T> entityClass);

	<T extends VOENTITYTYPE> List<T> filter(SelectQuery<T> selectQuery);

	<T extends VOENTITYTYPE> List<T> filter(SelectQuery<T> selectQuery, int firstResult, int maxResults);

	<T extends VOENTITYTYPE> Optional<T> read(SelectQuery<T> selectQuery);

	<T extends VOENTITYTYPE> void deleteAll(Class<T> entityClass);

	<T extends VOENTITYTYPE> void delete(T entity);

	<T extends VOENTITYTYPE> long count(CountQuery<T> countQuery);
}
