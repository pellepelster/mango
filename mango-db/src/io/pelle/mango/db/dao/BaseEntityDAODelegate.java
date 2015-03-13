/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class BaseEntityDAODelegate implements IBaseEntityDAO {

	private Map<Class<?>, IVOEntityDAO<? extends IBaseEntity>> entityDAOs = new HashMap<Class<?>, IVOEntityDAO<? extends IBaseEntity>>();

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@Override
	public <T extends IBaseEntity> T create(T entity) {

		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entity);

		if (entityDAO != null) {
			return entityDAO.create(entity);
		} else {
			return baseEntityDAO.create(entity);
		}

	}

	@SuppressWarnings("unchecked")
	private <T extends IBaseEntity> IVOEntityDAO<T> getVOEntityDAO(Object entity) {

		Class<?> entityClass = null;

		if (Class.class.isAssignableFrom(entity.getClass())) {
			entityClass = (Class<?>) entity;
		} else {
			entityClass = entity.getClass();
		}

		return (IVOEntityDAO<T>) entityDAOs.get(entityClass);
	}

	@Override
	public <T extends IBaseEntity> T save(T entity) {

		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entity);

		if (entityDAO != null) {
			return entityDAO.save(entity);
		} else {
			return baseEntityDAO.save(entity);
		}

	}

	@Override
	public <T extends IBaseEntity> T read(long id, Class<T> entityClass) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			return entityDAO.read(id);
		} else {
			return baseEntityDAO.read(id, entityClass);
		}
	}

	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery<T> query) {

		IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

		if (entityDAO != null) {
			return entityDAO.filter(query);
		} else {
			return baseEntityDAO.filter(query);
		}
	}

	@Override
	public <T extends IBaseEntity> Optional<T> read(SelectQuery<T> query) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

		if (entityDAO != null) {
			return entityDAO.read(query);
		} else {
			return baseEntityDAO.read(query);
		}
	}

	@Override
	public <T extends IBaseEntity> void deleteAll(Class<T> entityClass) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			entityDAO.deleteAll();
		} else {
			baseEntityDAO.deleteAll(entityClass);
		}
	}

	@Override
	public <T extends IBaseEntity> void delete(T entity) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entity);

		if (entityDAO != null) {
			entityDAO.delete(entity);
		} else {
			baseEntityDAO.delete(entity);
		}
	}

	@Override
	public <T extends IBaseEntity> long count(CountQuery<T> query) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

		if (entityDAO != null) {
			return entityDAO.count(query);
		} else {
			return baseEntityDAO.count(query);
		}
	}

	@Override
	public <T extends IBaseEntity> long aggregate(AggregateQuery<T> query) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

		if (entityDAO != null) {
			return entityDAO.aggregate(query);
		} else {
			return baseEntityDAO.aggregate(query);
		}
	}

	@Override
	public <T extends IBaseEntity> Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			return entityDAO.getByNaturalKey(naturalKey);
		} else {
			return baseEntityDAO.getByNaturalKey(entityClass, naturalKey);
		}
	}

	@Override
	public <T extends IBaseEntity> void deleteQuery(DeleteQuery<T> query) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

		if (entityDAO != null) {
			entityDAO.deleteQuery(query);
		} else {
			baseEntityDAO.deleteQuery(query);
		}
	}

	@Override
	public void registerCallback(IDAOCallback callback) {
		baseEntityDAO.registerCallback(callback);
	}

	@Autowired(required = false)
	public void setEntityDAOs(List<IVOEntityDAO<? extends IBaseEntity>> entityDAOList) {
		for (IVOEntityDAO<? extends IBaseEntity> voEntityDAO : entityDAOList) {
			Class<? extends IBaseEntity> baseEntityClass = EntityVOMapper.getInstance().getEntityClass(voEntityDAO.getVOEntityClass());
			entityDAOs.put(baseEntityClass, voEntityDAO);
		}
	}

	@Override
	public <T extends IBaseEntity> void delete(Class<T> entityClass, long id) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			entityDAO.delete(id);
		} else {
			baseEntityDAO.delete(entityClass, id);
		}
	}

	@Override
	public <T extends IBaseEntity> long count(Class<T> entityClass) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			return entityDAO.count();
		} else {
			return baseEntityDAO.count(entityClass);
		}
	}

	@Override
	public <T extends IBaseEntity> List<T> getAll(Class<T> entityClass) {
		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

		if (entityDAO != null) {
			return entityDAO.getAll();
		} else {
			return baseEntityDAO.getAll(entityClass);
		}
	}

}
