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

import static com.codahale.metrics.MetricRegistry.name;
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

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Optional;

@Component
public class BaseEntityDAODelegate extends BaseEntityVODelegate implements IBaseEntityDAO {

	private static final String CREATE_METRIC_KEY = "create";

	private static final String SAVE_METRIC_KEY = "save";

	private static final String READ_METRIC_KEY = "read";

	private Map<Class<? extends IBaseEntity>, Timer> createTimers = new HashMap<Class<? extends IBaseEntity>, Timer>();

	private Map<Class<? extends IBaseEntity>, Timer> saveTimers = new HashMap<Class<? extends IBaseEntity>, Timer>();

	private Map<Class<? extends IBaseEntity>, Timer> readTimers = new HashMap<Class<? extends IBaseEntity>, Timer>();

	private Map<Class<?>, IVOEntityDAO<? extends IBaseEntity>> entityDAOs = new HashMap<Class<?>, IVOEntityDAO<? extends IBaseEntity>>();

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@Override
	public <T extends IBaseEntity> T create(T entity) {

		Timer.Context context = getCreateContext(entity.getClass());

		try {

			IVOEntityDAO<T> entityDAO = getVOEntityDAO(entity);

			if (entityDAO != null) {
				return entityDAO.create(entity);
			} else {
				return baseEntityDAO.create(entity);
			}

		} finally {
			if (context != null) {
				context.stop();
			}
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

		Timer.Context context = getSaveContext(entity.getClass());

		IVOEntityDAO<T> entityDAO = getVOEntityDAO(entity);

		T result = null;

		if (entityDAO != null) {
			result = entityDAO.save(entity);
		} else {
			result = baseEntityDAO.save(entity);
		}

		if (context != null) {
			context.stop();
		}

		return result;
	}

	@Override
	public <T extends IBaseEntity> T read(long id, Class<T> entityClass) {

		Timer.Context context = getReadTimerContext(entityClass);

		try {

			IVOEntityDAO<T> entityDAO = getVOEntityDAO(entityClass);

			if (entityDAO != null) {
				return entityDAO.read(id);
			} else {
				return baseEntityDAO.read(id, entityClass);
			}
		} finally {
			if (context != null) {
				context.stop();
			}
		}

	}

	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery<T> query) {

		Timer.Context context = getFilterContext(EntityVOMapper.getInstance().getEntityClass(DBUtil.getQueryClass(query)));

		try {

			IVOEntityDAO<T> entityDAO = getVOEntityDAO(DBUtil.getQueryClass(query));

			if (entityDAO != null) {
				return entityDAO.filter(query);
			} else {
				return baseEntityDAO.filter(query);
			}

		} finally {
			if (context != null) {
				context.stop();
			}
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

	private Timer.Context getReadTimerContext(Class<? extends IBaseEntity> entityClass) {

		Timer timer = readTimers.get(entityClass);

		if (timer != null) {
			return timer.time();
		}

		return null;
	}

	private Timer.Context getCreateContext(Class<? extends IBaseEntity> entityClass) {

		Timer timer = createTimers.get(entityClass);

		if (timer != null) {
			return timer.time();
		}

		return null;
	}

	private Timer.Context getSaveContext(Class<? extends IBaseEntity> entityClass) {

		Timer timer = saveTimers.get(entityClass);

		if (timer != null) {
			return timer.time();
		}

		return null;
	}

	@Autowired(required = false)
	public void setMetricRegistry(MetricRegistry metricRegistry, EntityVOMapper entityVOMapper) {

		for (Class<? extends IBaseEntity> entityClass : entityVOMapper.getEntityClasses()) {
			createTimers.put(entityClass, metricRegistry.timer(name(entityClass, CREATE_METRIC_KEY)));
			saveTimers.put(entityClass, metricRegistry.timer(name(entityClass, SAVE_METRIC_KEY)));
			readTimers.put(entityClass, metricRegistry.timer(name(entityClass, READ_METRIC_KEY)));
		}

	}

}
