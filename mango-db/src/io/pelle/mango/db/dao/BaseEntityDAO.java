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
import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import io.pelle.mango.client.base.db.vos.IInfoVOEntity;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.IUser;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.ObjectFieldIterator;
import io.pelle.mango.db.query.ServerCountQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.server.base.IBaseClientEntity;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Optional;

@Component
public class BaseEntityDAO extends BaseDAO<IBaseEntity> implements IBaseEntityDAO {

	private static Logger LOG = Logger.getLogger(BaseEntityDAO.class);

	private Optional<Timer> createTimer = Optional.absent();

	public <T extends IBaseEntity> T create(T entity) {

		Optional<Timer.Context> context = Optional.absent();

		if (createTimer.isPresent()) {
			context = Optional.of(createTimer.get().time());
		}

		try {

			if (entity instanceof IBaseClientEntity) {

				populateClients(((IBaseClientEntity) entity), new ArrayList<IBaseClientEntity>());

				LOG.debug(String.format("creating entity '%s' for client '%s'", entity.getClass().getName(), getCurrentUser().getClient()));
			} else {
				LOG.debug(String.format("creating entity '%s'", entity.getClass().getName()));
			}

			if (entity instanceof IInfoVOEntity) {

				IInfoVOEntity infoEntity = (IInfoVOEntity) entity;

				if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
					infoEntity.setUpdateUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
					infoEntity.setCreateUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
				}

				Date now = new Date();
				infoEntity.setCreateDate(now);
				infoEntity.setUpdateDate(now);
			}

			T result = mergeRecursive(entity);

			return result;
		} finally {
			if (context.isPresent()) {
				context.get().stop();
			}
		}
	}

	public <T extends IBaseEntity> void delete(T entity) {
		LOG.debug(String.format("deleting entity '%s' with id '%d'", entity.getClass().getName(), entity.getId()));

		T entityToDelete = this.entityManager.merge(entity);
		this.entityManager.remove(entityToDelete);
	}

	@Override
	public <T extends IBaseEntity> void deleteAll(Class<T> entityClass) {
		LOG.debug(String.format("deleting all '%s' entities", entityClass.getName()));

		for (Object entity : getAll(entityClass)) {
			this.entityManager.remove(entity);
		}

		// @see https://github.com/pellepelster/myadmin/issues/8

		// if (BeanUtil.hasAnnotatedAttribute1(entityClass,
		// ElementCollection.class))
		// {
		// }
		// else
		// {
		// DeleteQuery deleteQuery = new DeleteQuery(entityClass);
		//
		// if (IBaseClientEntity.class.isAssignableFrom(entityClass))
		// {
		// deleteQuery.addWhereCondition(getClientConditionalExpression(entityClass));
		// }
		//
		// Query query = this.entityManager.createQuery(deleteQuery.getJPQL());
		// for (NamedParameterExpressionObject namedParameter :
		// deleteQuery.getNamedParameters())
		// {
		// query.setParameter(namedParameter.getName(),
		// namedParameter.getObject());
		// }
		//
		// query.executeUpdate();
		// }
	}

	@SuppressWarnings("unchecked")
	private <T extends IBaseEntity> List<T> getAll(Class<T> entityClass) {
		return (List<T>) getResultList(selectFrom(entityClass), entityManager);
	}

	private IUser getCurrentUser() {
		try {
			return (IUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void populateClients(IBaseClientEntity clientEntity, List<IBaseClientEntity> visited) {
		try {
			clientEntity.setClient(getCurrentUser().getClient());
			visited.add(clientEntity);

			for (Map.Entry<String, Object> entry : ((Map<String, Object>) PropertyUtils.describe(clientEntity)).entrySet()) {

				String propertyName = entry.getKey();
				PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(clientEntity, propertyName);

				if (propertyDescriptor != null) {
					Object attribute = PropertyUtils.getSimpleProperty(clientEntity, propertyName);

					if (attribute != null && IBaseClientEntity.class.isAssignableFrom(attribute.getClass())) {
						if (!visited.contains(attribute)) {
							populateClients((IBaseClientEntity) attribute, visited);
						}
					}

					if (attribute != null && List.class.isAssignableFrom(attribute.getClass())) {
						List<?> list = (List<?>) attribute;

						for (Object listElement : list) {
							if (IBaseClientEntity.class.isAssignableFrom(listElement.getClass())) {
								if (!visited.contains(attribute)) {
									populateClients((IBaseClientEntity) listElement, visited);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("error setting client", e);
		}
	}

	public <T extends IBaseEntity> T read(long id, Class<T> entityClass) {

		T entity = this.entityManager.find(entityClass, id);

		if (entity instanceof IBaseClientEntity) {

			IUser user = getCurrentUser();

			LOG.debug(String.format("retrieving entity '%s' with id %d for client '%s'", entityClass.getName(), id, getCurrentUser().getClient()));

			if (((IBaseClientEntity) entity).getClient().getId() == user.getClient().getId()) {
				return entity;
			} else {
				throw new AccessDeniedException(String.format("entity '%s' not allowed for client '%s'", entity, user.getClient()));
			}
		} else {
			LOG.debug(String.format("retrieving entity '%s' with id %d", entityClass.getName(), id));

			return entity;
		}
	}

	public <T extends IBaseEntity> T save(T entity) {

		if (entity instanceof IBaseClientEntity) {
			((IBaseClientEntity) entity).setClient(getCurrentUser().getClient());

			LOG.debug(String.format("saving '%s' for client '%s'", entity.getClass(), getCurrentUser().getClient()));
		} else {
			// throw new AccessDeniedException(String.format(
			// "entity '%s' not allowed for client '%s'", entity,
			// getCurrentUser().getClient()));
		}

		if (entity instanceof IInfoVOEntity) {

			IInfoVOEntity infoEntity = (IInfoVOEntity) entity;

			if (SecurityContextHolder.getContext().getAuthentication() != null) {

				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				if (principal != null) {
					infoEntity.setUpdateUser(principal.toString());
				}
			}

			infoEntity.setUpdateDate(new Date());
		}

		T result = mergeRecursive(entity);

		return result;
	}

	private <T extends IBaseEntity> T mergeRecursive(T entity) {

		return mergeRecursive(entity, "/");
	}

	@SuppressWarnings("unchecked")
	private <T extends IBaseEntity> T mergeRecursive(T entity, String currentPath) {

		// TODO use dirty paths?
		for (ObjectFieldDescriptor fieldDescriptor : new ObjectFieldIterator(entity)) {

			if (fieldDescriptor.isNonNullSourceType(IBaseEntity.class)) {

				IBaseEntity entityAttribute = (IBaseEntity) fieldDescriptor.getSourceValue();

				IBaseEntity mergedEntityAttribute = mergeRecursive(entityAttribute, currentPath);

				try {
					BeanUtils.setProperty(entity, fieldDescriptor.getFieldName(), mergedEntityAttribute);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			} else if (fieldDescriptor.isNonNullSourceType(List.class)) {

				List<Object> sourceList = (List<Object>) fieldDescriptor.getSourceValue();

				for (int i = 0; i < sourceList.size(); i++) {
					Object listItem = sourceList.get(i);

					if (listItem instanceof IBaseEntity) {
						IBaseEntity baseEntityListItem = (IBaseEntity) listItem;
						IBaseEntity mergedEntityListItem = mergeRecursive(baseEntityListItem, currentPath);
						sourceList.set(i, mergedEntityListItem);
					} else {
						sourceList.set(i, listItem);
					}
				}

				continue;
			}
		}

		return this.entityManager.merge(entity);

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery<T> selectQuery) {
		return (List<T>) getResultList(selectQuery, entityManager);
	}

	@Override
	public <T extends IBaseEntity> Optional<T> read(SelectQuery<T> selectQuery) {
		List<T> result = filter(selectQuery);

		if (result.size() == 1) {
			return Optional.of(result.get(0));
		} else {
			return Optional.<T> absent();
		}
	}

	@Override
	public <T extends IBaseEntity> long count(CountQuery<T> countQuery) {
		return (long) entityManager.createQuery(ServerCountQuery.adapt(countQuery).getJPQL(EntityVOMapper.getInstance())).getSingleResult();
	}

	@Autowired(required = false)
	public void setMetricRegistry(MetricRegistry metricRegistry) {
		createTimer = Optional.fromNullable(metricRegistry.timer(name(BaseEntityDAO.class, "create")));
	}

	@Override
	public <T extends IBaseEntity> Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey) {
		List<T> result = filter(DBUtil.getNaturalKeyQuery(entityClass, naturalKey));

		if (result.size() > 1 || result.isEmpty()) {
			return Optional.absent();
		} else {
			return Optional.of(result.get(0));
		}
	}

}
