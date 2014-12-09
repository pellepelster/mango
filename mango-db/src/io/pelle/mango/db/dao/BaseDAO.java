package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class BaseDAO {

	private static Logger LOG = Logger.getLogger(BaseDAO.class);

	@SuppressWarnings("unchecked")
	public List<IBaseEntity> getResultList(SelectQuery<?> selectQuery, EntityManager entityManager) {
		return getResultList(selectQuery, entityManager, selectQuery.getFirstResult(), selectQuery.getMaxResults());
	}

	@SuppressWarnings("rawtypes")
	public List getResultList(SelectQuery<?> selectQuery, EntityManager entityManager, int firstResult, int maxResults) {

		String jpql = ServerSelectQuery.adapt(selectQuery).getJPQL(EntityVOMapper.getInstance());
		LOG.debug(jpql);

		try {
			return entityManager.createQuery(jpql).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		} catch (Exception e) {
			throw new RuntimeException(String.format("error executing jpql '%s'", jpql), e);
		}
	}

}
