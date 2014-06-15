package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.Join.JOIN_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an entity for an JPQL query
 * 
 * @author pelle
 * @version $Rev: 662 $, $Date: 2010-08-30 20:58:04 +0200 (Mon, 30 Aug 2010) $
 * 
 */
public class Entity implements IEntity {

	/** Entity class */
	private final Class<? extends IVOEntity> voEntityClass;

	private String alias;

	/** Joins for this entity */
	private final Map<String, Join> joins = new HashMap<String, Join>();

	private IAliasProvider aliasProvider;

	/**
	 * Constructor for <code>Entity</code>
	 * 
	 * /**
	 * 
	 * @param aliasProvider
	 * @param entityClass
	 */
	public Entity(IAliasProvider aliasProvider, Class<? extends IVOEntity> voEntityClass) {
		this.voEntityClass = voEntityClass;
		this.alias = aliasProvider.getAliasFor(voEntityClass);
		this.aliasProvider = aliasProvider;

	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity) {
			return ((Entity) obj).voEntityClass.equals(voEntityClass);
		}

		if (obj instanceof Class<?>) {
			return ((Class<?>) obj).equals(voEntityClass);
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getAlias() {
		return alias;
	}

	/**
	 * The entity class
	 * 
	 * @return
	 */
	public Class<? extends IVOEntity> getVOEntityClass() {
		return voEntityClass;
	}

	public Join join(IAttributeDescriptor<?> attributeDescriptor) {
		return join(attributeDescriptor.getAttributeName());
	}

	public Join join(String field) {
		if (joins.containsKey(field)) {
			return joins.get(field);
		} else {
			Join join = new Join(aliasProvider, JOIN_TYPE.LEFT, field);
			joins.put(field, join);

			return join;
		}
	}

	public List<Join> getJoins() {
		return new ArrayList<Join>(joins.values());
	}

	/**
	 * Name for this entity
	 * 
	 * @return
	 */
	public String getName(IEntityVOMapper entityVOMapper) {
		return entityVOMapper.getEntityClass(voEntityClass).getSimpleName();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return voEntityClass.hashCode();
	}

}