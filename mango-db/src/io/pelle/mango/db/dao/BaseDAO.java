package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerAggregateQuery;
import io.pelle.mango.db.query.ServerDeleteQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public abstract class BaseDAO<VOENTITYTYPE extends IVOEntity> implements IBaseVOEntityDAO<VOENTITYTYPE> {

	private static Logger LOG = Logger.getLogger(BaseDAO.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	protected List<IBaseEntity> getResultListInternal(SelectQuery<?> selectQuery, EntityManager entityManager) {
		return getResultListInternal(selectQuery, entityManager, selectQuery.getFirstResult(), selectQuery.getMaxResults());
	}

	@SuppressWarnings("rawtypes")
	protected List getResultListInternal(SelectQuery<?> selectQuery, EntityManager entityManager, int firstResult, int maxResults) {

		String jpql = ServerSelectQuery.adapt(selectQuery).getJPQL(EntityVOMapper.getInstance());
		LOG.debug(jpql);

		try {
			return entityManager.createQuery(jpql).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		} catch (Exception e) {
			throw new RuntimeException(String.format("error executing jpql '%s'", jpql), e);
		}
	}

	public <T extends VOENTITYTYPE> long aggregate(AggregateQuery<T> query) {

		ServerAggregateQuery<T> serverQuery = ServerAggregateQuery.adapt(query);

		Object result = entityManager.createQuery(serverQuery.getJPQL(EntityVOMapper.getInstance())).getSingleResult();

		if (result instanceof Long) {
			return (long) result;
		}

		throw new RuntimeException(String.format("unsupported query result '%s'", result));

	}

	public <T extends VOENTITYTYPE> void deleteQuery(DeleteQuery<T> deleteQuery) {

		String query = ServerDeleteQuery.adapt(deleteQuery).getJPQL(EntityVOMapper.getInstance());
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("executing delete query '%s'", query));
		}
		entityManager.createQuery(query).executeUpdate();
	}

}
