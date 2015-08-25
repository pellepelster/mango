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

import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import io.pelle.mango.client.base.db.vos.IInfoVOEntity;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.ExpressionFactory;
import io.pelle.mango.db.IUser;
import io.pelle.mango.db.copy.ObjectFieldDescriptor;
import io.pelle.mango.db.copy.ObjectFieldIterator;
import io.pelle.mango.db.query.ServerCountQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.db.voquery.EntityClassQuery;
import io.pelle.mango.server.base.IBaseClientEntity;

@Component
public class BaseEntityDAO extends BaseDAO<IBaseEntity> {

	private static Logger LOG = Logger.getLogger(BaseEntityDAO.class);

	@Autowired(required = false)
	private List<IEntityCallback> entityCallbacks = new ArrayList<IEntityCallback>();

	@Override
	public <T extends IBaseEntity> T create(T entity) {

		beforeCreate(entity);

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

	}

	protected void beforeCreate(IBaseEntity entity) {
		for (IEntityCallback entityCallback : this.entityCallbacks) {
			if (entityCallback.supports(entity.getClass())) {
				entityCallback.beforeCreate(entity);
			}
		}
	}

	protected void beforeSave(IBaseEntity entity) {
		for (IEntityCallback entityCallback : this.entityCallbacks) {
			if (entityCallback.supports(entity.getClass())) {
				entityCallback.beforeSave(entity);
			}
		}
	}

	@Override
	public <T extends IBaseEntity> void delete(T entity) {
		LOG.debug(String.format("deleting entity '%s' with id '%d'", entity.getClass().getName(), entity.getId()));

		// TODO warn if entities with embedded element collections are deleted

		DeleteQuery query = DeleteQuery.deleteFrom(entity.getClass()).where(ExpressionFactory.createLongExpression(entity.getClass(), IBaseEntity.ID_FIELD_NAME, entity.getId()));
		deleteQuery(query);
	}

	private void deleteTables(List<String> tableNames) {

		for (String tableName : tableNames) {
			String nativeDeleteQuery = String.format("delete from %s", tableName);
			entityManager.createNativeQuery(nativeDeleteQuery).executeUpdate();
		}
	}

	@Override
	public <T extends IBaseEntity> void deleteAll(Class<T> entityClass) {

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("deleting everything for entity '%s'", entityClass.getName()));
		}

		List<String> elementCollections = EntityClassQuery.createQuery(entityClass).getElementCollections();
		if (LOG.isDebugEnabled() && !elementCollections.isEmpty()) {
			LOG.debug(String.format("deleting element collections '%s' for entity '%s'", Joiner.on(", ").join(elementCollections), entityClass.getName()));
			deleteTables(elementCollections);
		}

		List<String> oneToManyJoinTables = EntityClassQuery.createQuery(entityClass).getOneToManyJoinTables();
		if (LOG.isDebugEnabled() && !oneToManyJoinTables.isEmpty()) {
			LOG.debug(String.format("deleting one to many join tables '%s' for entity '%s'", Joiner.on(", ").join(oneToManyJoinTables), entityClass.getName()));
			deleteTables(oneToManyJoinTables);
		}

		DeleteQuery query = DeleteQuery.deleteFrom(entityClass);
		deleteQuery(query);
	}

	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> List<T> getAll(Class<T> entityClass) {
		return (List<T>) getResultListInternal(selectFrom(entityClass), entityManager);
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

	@Override
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

	@Override
	public <T extends IBaseEntity> T save(T entity) {

		beforeSave(entity);

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

			Object sourceValue = fieldDescriptor.getSourceValue(entity);
			if (sourceValue instanceof IBaseEntity) {

				checkLoaded(entity, fieldDescriptor);

				IBaseEntity entityAttribute = (IBaseEntity) sourceValue;
				IBaseEntity mergedEntityAttribute = mergeRecursive(entityAttribute, currentPath);

				try {
					fieldDescriptor.getSourceWriteMethod().invoke(entity, mergedEntityAttribute);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			} else if (sourceValue instanceof List) {

				checkLoaded(entity, fieldDescriptor);

				List<Object> sourceList = (List<Object>) sourceValue;

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

	private <T extends IBaseEntity> void checkLoaded(T entity, ObjectFieldDescriptor fieldDescriptor) {
		if (!entity.isNew() && !entity.getMetadata().isLoaded(fieldDescriptor.getFieldName()) && entity.getMetadata().hasChanges()) {
			throw new RuntimeException(String.format("entity '%s' is dirty but has unloaded attribute '%s'", entity.getClass(), fieldDescriptor.getFieldName()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseEntity> List<T> filter(SelectQuery<T> selectQuery) {
		return (List<T>) getResultListInternal(selectQuery, entityManager);
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

	@Override
	public <T extends IBaseEntity> long count(Class<T> entityClass) {
		return count(CountQuery.countFrom(entityClass));
	}

	@Override
	public <T extends IBaseEntity> void delete(Class<T> entityClass, long id) {
		T entity = read(id, entityClass);
		delete(entity);
	}

	@Override
	public <T extends IBaseEntity> List<T> searchByNaturalKey(Class<T> entityClass, String naturalKey) {
		List<T> result = filter(DBUtil.getNaturalKeyQuery(entityClass, naturalKey));
		return result;
	}

	@Override
	public <T extends IBaseEntity> T getNewVO(String className, Map<String, String> properties) {

		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getEntityClass(className);

		try {
			T entity = (T) ConstructorUtils.invokeConstructor(entityClass, new Object[0]);

			return entity;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
