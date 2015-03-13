package io.pelle.mango.db.dao;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerCountQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;

@Component
public class BaseVODAO extends BaseDAO<IBaseVO> {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseVO> T create(T baseVO) {

		IBaseEntity baseEntity = DBUtil.convertVOToEntityClass(baseVO);

		baseEntity = baseEntityDAO.create(baseEntity);

		return (T) DBUtil.convertEntityToVO(baseEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseVO> T read(long id, Class<T> voClass) {

		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(voClass);
		IBaseEntity baseEntity = baseEntityDAO.read(id, entityClass);

		return (T) DBUtil.convertEntityToVO(baseEntity);
	}

	@Override
	public <T extends IBaseVO> void deleteAll(Class<T> voClass) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(voClass);
		baseEntityDAO.deleteAll(entityClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBaseVO> T save(T baseVO) {
		IBaseEntity baseEntity = DBUtil.convertVOToEntityClass(baseVO);
		baseEntity = baseEntityDAO.save(baseEntity);
		return (T) DBUtil.convertEntityToVO(baseEntity);
	}

	@Override
	public <T extends IBaseVO> void delete(T baseVO) {
		IBaseEntity baseEntity = DBUtil.convertVOToEntityClass(baseVO);
		baseEntityDAO.delete(baseEntity);
	}

	@Override
	public <T extends IBaseVO> Optional<T> read(SelectQuery<T> selectQuery) {
		List<T> result = filter(selectQuery);

		if (result.size() == 1) {
			return Optional.of(result.get(0));
		} else {
			return Optional.<T> absent();
		}
	}

	@Override
	public <T extends IBaseVO> List<T> filter(SelectQuery<T> selectQuery) {
		List<IBaseEntity> entityResult = (List<IBaseEntity>) getResultList(selectQuery, entityManager);

		final Map<Class<?>, Set<String>> classLoadAssociations = DBUtil.getClassLoadAssociations(selectQuery);

		return new ArrayList<>(Collections2.transform(entityResult, new Function<IBaseEntity, T>() {
			@Override
			public T apply(IBaseEntity baseEntity) {
				@SuppressWarnings("unchecked")
				T baseVO = (T) DBUtil.convertEntityToVO(baseEntity, classLoadAssociations);
				return baseVO;
			}
		}));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IBaseVO> List<T> getAll(Class<T> voClass) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(voClass);
		return (List<T>) baseEntityDAO.getAll(entityClass);
	}

	@Override
	public <T extends IBaseVO> long count(CountQuery<T> countQuery) {
		return (long) entityManager.createQuery(ServerCountQuery.adapt(countQuery).getJPQL(EntityVOMapper.getInstance())).getSingleResult();
	}

	@Override
	public <T extends IBaseVO> Optional<T> getByNaturalKey(Class<T> entityClass, String naturalKey) {

		List<T> result = filter(DBUtil.getNaturalKeyQuery(entityClass, naturalKey));

		if (result.size() > 1 || result.isEmpty()) {
			return Optional.absent();
		} else {
			return Optional.of(result.get(0));
		}
	}

	@Override
	public <T extends IBaseVO> void delete(Class<T> voClass, long id) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(voClass);
		baseEntityDAO.delete(entityClass, id);
	}

	@Override
	public <T extends IBaseVO> long count(Class<T> voClass) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(voClass);
		return baseEntityDAO.count(entityClass);
	}
}