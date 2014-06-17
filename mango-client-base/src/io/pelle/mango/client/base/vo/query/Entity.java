package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.Join.JOIN_TYPE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Entity implements IEntity, Serializable {

	/** Entity class */
	private String className;

	private String alias;

	/** Joins for this entity */
	private Map<String, Join> joins = new HashMap<String, Join>();

	private IAliasProvider aliasProvider;

	public Entity() {
	}
	
	/**
	 * Constructor for <code>Entity</code>
	 * 
	 * /**
	 * 
	 * @param aliasProvider
	 * @param entityClass
	 */
	public Entity(IAliasProvider aliasProvider, String className) {
		this.className = className;
		this.alias = aliasProvider.getAliasFor(className);
		this.aliasProvider = aliasProvider;

	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Entity) {
			return ((Entity) obj).className.equals(className);
		}

		if (obj instanceof String) {
			return ((String) obj).equals(className);
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getAlias() {
		return alias;
	}

	public String getClassName() {
		return className;
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


	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return className.hashCode();
	}

}