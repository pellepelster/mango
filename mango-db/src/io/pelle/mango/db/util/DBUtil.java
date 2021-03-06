package io.pelle.mango.db.util;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.BaseQuery;
import io.pelle.mango.client.base.vo.query.Entity;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.Join;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

/**
 * Various DB utilities
 * 
 * @author pelle
 * 
 */
public final class DBUtil {

	public static Class<?> getQueryClass(BaseQuery<?, ?> baseQuery) {
		try {
			return Class.forName(baseQuery.getFroms().get(0).getClassName());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static Map<Class<? extends IVOEntity>, Set<String>> getLoadAssociations(SelectQuery<?> selectQuery) {

		Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations = new HashMap<>();

		for (Entity entity : selectQuery.getFroms()) {

			addFirstLevelIBaseVOAttributes(EntityVOMapper.getInstance().getVOClass(entity.getClassName()), classLoadAssociations);

			Set<String> associations = getAssociations(EntityVOMapper.getInstance().getVOClass(entity.getClassName()), classLoadAssociations);

			for (Join join : entity.getJoins()) {
				associations.add(join.getField());

				addJoinAssociations(join, EntityVOMapper.getInstance().getVOClass(entity.getClassName()), classLoadAssociations);
			}

		}

		if (selectQuery.isLoadNaturalKeyReferences()) {
			addNaturalKeyAttributes(classLoadAssociations);
		}

		return classLoadAssociations;
	}

	public static void addJoinAssociations(Join join, Class<?> parentClass, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {

		IAttributeDescriptor<?> attributeDescriptor = BeanUtils.getAttributeDescriptor(parentClass, join.getField());

		for (Join join1 : join.getJoins()) {

			getAssociations((Class<? extends IVOEntity>) attributeDescriptor.getListAttributeType(), classLoadAssociations).add(join1.getField());

			addJoinAssociations(join1, attributeDescriptor.getListAttributeType(), classLoadAssociations);
		}

	}

	public static Set<String> getAssociations(Class<? extends IVOEntity> clazz, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {

		if (!classLoadAssociations.containsKey(clazz)) {
			classLoadAssociations.put(clazz, new HashSet<String>());
		}

		return classLoadAssociations.get(clazz);
	}

	public static void addFirstLevelIBaseVOAttributes(Class<? extends IVOEntity> voEntityClass, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {

		Set<String> associations = getAssociations(voEntityClass, classLoadAssociations);

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtils.getAttributeDescriptors(voEntityClass)) {
			if (!attributeDescriptor.getAttributeName().equals(IHierarchicalVO.FIELD_PARENT.getAttributeName())
					&& (IVOEntity.class.isAssignableFrom(attributeDescriptor.getListAttributeType()) || List.class.isAssignableFrom(attributeDescriptor.getAttributeType()))) {
				associations.add(attributeDescriptor.getAttributeName());
			}
		}

	}

	public static void addNaturalKeyAttributes(Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {

		for (Map.Entry<Class<? extends IVOEntity>, Set<String>> classLoadAssociation : classLoadAssociations.entrySet()) {

			for (String attributeName : classLoadAssociation.getValue()) {

				IAttributeDescriptor<?> attributeDescriptor = VOClassQuery.createQuery(classLoadAssociation.getKey()).attributesDescriptors().byName(attributeName).getSingleResult();

				if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getListAttributeType())) {
					addNaturalKeyAttributes((Class<? extends IVOEntity>) attributeDescriptor.getListAttributeType(), classLoadAssociations);
				}
			}
		}
	}

	public static void addNaturalKeyAttributes(Class<? extends IVOEntity> voEntityClass, Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations) {

		for (IAttributeDescriptor<?> naturalKey : VOClassQuery.createQuery(voEntityClass).attributesDescriptors().naturalKeys()) {

			if (IBaseVO.class.isAssignableFrom(naturalKey.getListAttributeType())) {
				getAssociations(voEntityClass, classLoadAssociations).add(naturalKey.getAttributeName());
				addNaturalKeyAttributes((Class<? extends IVOEntity>) naturalKey.getListAttributeType(), classLoadAssociations);
			}

		}

	}

	public static IBaseEntity convertVOToEntityClass(IBaseVO baseVO) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(baseVO.getClass());
		return (IBaseEntity) CopyBean.getInstance().copyObject(baseVO, entityClass, false);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IBaseVO> T convertEntityToVO(IBaseEntity baseEntity) {
		Class<? extends IBaseVO> voClass = EntityVOMapper.getInstance().getMappedVOClass(baseEntity.getClass());
		return (T) CopyBean.getInstance().copyObject(baseEntity, voClass);
	}

	public static IBaseVO convertEntityToVO(IBaseEntity baseEntity, Map<Class<? extends IVOEntity>, Set<String>> loadAssociations) {
		Class<? extends IBaseVO> voClass = EntityVOMapper.getInstance().getMappedVOClass(baseEntity.getClass());
		return (IBaseVO) CopyBean.getInstance().copyObject(baseEntity, voClass, loadAssociations, true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String mapToString(Map map) {

		String lineSeperator = System.getProperty("line.separator");

		if (map == null) {
			return "";
		}

		StringBuffer stringBuffer = new StringBuffer();

		Set<Map.Entry> s = map.entrySet();

		for (Map.Entry elem : s) {

			Object key = elem.getKey();
			Object value = elem.getValue();

			stringBuffer.append(key.toString());
			stringBuffer.append("=");
			stringBuffer.append(value == null ? "" : value.toString());
			stringBuffer.append(lineSeperator);

		}

		return stringBuffer.toString();
	}

	public static <T extends IVOEntity> SelectQuery<T> getNaturalKeyQuery(Class<T> voEntityClass, String naturalKey) {

		SelectQuery<T> selectQuery = SelectQuery.selectFrom(voEntityClass);

		populateNaturalKeyQuery(voEntityClass, selectQuery, null, naturalKey);

		return selectQuery;
	}

	public static <T extends IVOEntity> void populateNaturalKeyQuery(Class<T> voEntityClass, SelectQuery selectQuery, IAttributeDescriptor<?> parentAttribute, String naturalKey) {

		for (IAttributeDescriptor naturalKeyAttributeDescriptor : VOClassQuery.createQuery(voEntityClass).attributesDescriptors().naturalKeys()) {

			if (IBaseVO.class.isAssignableFrom(naturalKeyAttributeDescriptor.getListAttributeType())) {

				IAttributeDescriptor<?> attributeDescriptor = null;

				if (parentAttribute != null) {
					attributeDescriptor = parentAttribute.path(naturalKeyAttributeDescriptor);
				} else {
					attributeDescriptor = naturalKeyAttributeDescriptor;
				}
				populateNaturalKeyQuery(naturalKeyAttributeDescriptor.getListAttributeType(), selectQuery, attributeDescriptor, naturalKey);
			} else {
				
				Optional<IBooleanExpression> expression = null;
				
				if (parentAttribute == null) {
					expression = naturalKeyAttributeDescriptor.search(naturalKey);
				} else {
					expression = parentAttribute.path(naturalKeyAttributeDescriptor).search(naturalKey);
				}
				
				if (expression.isPresent()) {
					selectQuery.addWhereOr(expression.get());
				}
			}
		}

	}

	private DBUtil() {
	}

}