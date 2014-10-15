package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.List;

import javax.persistence.EntityManager;

public class BaseDAO {

	@SuppressWarnings("unchecked")
	public List<IBaseEntity> getResultList(SelectQuery<?> selectQuery, EntityManager entityManager) {
		return getResultList(selectQuery, entityManager, 0, selectQuery.getMaxResults());
	}

	@SuppressWarnings("rawtypes")
	public List getResultList(SelectQuery<?> selectQuery, EntityManager entityManager, int firstResult, int maxResults) {

		String jpql = ServerSelectQuery.adapt(selectQuery).getJPQL(EntityVOMapper.getInstance());
		return entityManager.createQuery(jpql).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

}
