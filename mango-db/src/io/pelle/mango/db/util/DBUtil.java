package io.pelle.mango.db.util;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.Entity;
import io.pelle.mango.client.base.vo.query.Join;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Various DB utilities
 * 
 * @author pelle
 * 
 */
public final class DBUtil {

	public static Map<Class<?>, Set<String>> getClassLoadAssociations(SelectQuery<?> selectQuery) {

		Map<Class<?>, Set<String>> classLoadAssociations = new HashMap<>();

		for (Entity entity : selectQuery.getFroms()) {

			addFirstLevelIBaseVOAttributes(entity.getVOEntityClass(), classLoadAssociations);

			Set<String> associations = getAssociations(entity.getVOEntityClass(), classLoadAssociations);

			for (Join join : entity.getJoins()) {
				associations.add(join.getField());

				addJoinAssociations(join, entity.getVOEntityClass(), classLoadAssociations);
			}

		}

		return classLoadAssociations;
	}

	public static void addJoinAssociations(Join join, Class<?> parentClass, Map<Class<?>, Set<String>> classLoadAssociations) {

		IAttributeDescriptor<?> attributeDescriptor = BeanUtils.getAttributeDescriptor(parentClass, join.getField());

		for (Join join1 : join.getJoins()) {

			getAssociations(attributeDescriptor.getListAttributeType(), classLoadAssociations).add(join1.getField());

			addJoinAssociations(join1, attributeDescriptor.getListAttributeType(), classLoadAssociations);
		}

	}

	public static Set<String> getAssociations(Class<?> clazz, Map<Class<?>, Set<String>> classLoadAssociations) {

		if (!classLoadAssociations.containsKey(clazz)) {
			classLoadAssociations.put(clazz, new HashSet<String>());
		}

		return classLoadAssociations.get(clazz);
	}

	public static void addFirstLevelIBaseVOAttributes(Class<? extends IVOEntity> voEntityClass, Map<Class<?>, Set<String>> classLoadAssociations) {

		Set<String> associations = getAssociations(voEntityClass, classLoadAssociations);

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtils.getAttributeDescriptors(voEntityClass)) {
			if (IVOEntity.class.isAssignableFrom(attributeDescriptor.getListAttributeType())) {
				associations.add(attributeDescriptor.getAttributeName());
			}
		}

	}

	public static IBaseEntity convertVOToEntityClass(IBaseVO baseVO) {
		Class<? extends IBaseEntity> entityClass = EntityVOMapper.getInstance().getMappedEntityClass(baseVO.getClass());
		return (IBaseEntity) CopyBean.getInstance().copyObject(baseVO, entityClass);
	}

	public static IBaseVO convertEntityToVO(IBaseEntity baseEntity) {
		Class<? extends IBaseVO> voClass = EntityVOMapper.getInstance().getMappedVOClass(baseEntity.getClass());
		return (IBaseVO) CopyBean.getInstance().copyObject(baseEntity, voClass);
	}

	public static IBaseVO convertEntityToVO(IBaseEntity baseEntity, Map<Class<?>, Set<String>> classLoadAssociations) {
		Class<? extends IBaseVO> voClass = EntityVOMapper.getInstance().getMappedVOClass(baseEntity.getClass());
		return (IBaseVO) CopyBean.getInstance().copyObject(baseEntity, voClass, classLoadAssociations);
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

	private DBUtil() {
	}

}