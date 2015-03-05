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
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class BaseEntityDAODelegate implements IBaseEntityDAO {

	private Map<Class<?>, IVOEntityDAO<?>> entityDAOs = new HashMap<Class<?>, IVOEntityDAO<?>>();

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseEntity> T create(T entity) {

		IVOEntityDAO<T> voEntityDAO = (IVOEntityDAO<T>) entityDAOs.get(entity.getClass());

		if (voEntityDAO != null) {
			return voEntityDAO.create(entity);
		} else {
			return baseEntityDAO.create(entity);
		}
	}

	@Override
	public <T extends IBaseEntity> T save(T entity) {
		return baseEntityDAO.save(entity);
	}

	@Override
	public <T extends IBaseEntity> T read(long id, Class<T> entityClass) {
		return baseEntityDAO.read(id, entityClass);
	}

	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery<T> query) {
		return baseEntityDAO.filter(query);
	}

	@Override
	public <T extends IBaseEntity> Optional<T> read(SelectQuery<T> query) {
		return baseEntityDAO.read(query);
	}

	@Override
	public <T extends IBaseEntity> void deleteAll(Class<T> entityClass) {
		baseEntityDAO.deleteAll(entityClass);
	}

	@Override
	public <T extends IBaseEntity> void delete(T entity) {
		baseEntityDAO.delete(entity);
	}

	@Override
	public <T extends IBaseEntity> long count(CountQuery<T> query) {
		return baseEntityDAO.count(query);
	}

	@Override
	public <T extends IBaseEntity> long aggregate(AggregateQuery<T> query) {
		return baseEntityDAO.aggregate(query);
	}

	@Override
	public <T extends IBaseEntity> Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey) {
		return baseEntityDAO.getByNaturalKey(entityClass, naturalKey);
	}

	@Override
	public <T extends IBaseEntity> void deleteQuery(DeleteQuery<T> query) {
		baseEntityDAO.deleteQuery(query);
	}

	@Override
	public void registerCallback(IDAOCallback callback) {
		baseEntityDAO.registerCallback(callback);
	}

	@Autowired
	public void setEntityDAOs(List<IVOEntityDAO<?>> entityDAOList) {
		for (IVOEntityDAO<?> voEntityDAO : entityDAOList) {
			Class<? extends IBaseEntity> baseEntityClass = EntityVOMapper.getInstance().getEntityClass(voEntityDAO.getVOEntityClass());
			entityDAOs.put(baseEntityClass, voEntityDAO);
		}
	}

}
