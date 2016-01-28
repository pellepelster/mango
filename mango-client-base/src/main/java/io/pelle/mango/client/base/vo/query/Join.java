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
package io.pelle.mango.client.base.vo.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;

@SuppressWarnings("serial")
public class Join implements IEntity, Serializable {

	/**
	 * Join types for a JPQL join
	 */
	public enum JOIN_TYPE {
		/**
		 * Represents a left join
		 */
		LEFT {
			/** {@inheritDoc} */
			@Override
			public String toString() {
				return "LEFT JOIN";
			}

		}

		/**
		 * Represents a left fetch join
		 */
		// LEFT_FETCH {
		// /** {@inheritDoc} */
		// @Override
		// public String toString() {
		// return "LEFT JOIN FETCH";
		// }
		//
		// }
	}

	/** Joins for this query */
	private Map<String, Join> joins = new HashMap<String, Join>();

	/** The join field */
	private String field;

	/** Type of the join */
	private JOIN_TYPE joinType;

	private String alias;

	private IAliasProvider aliasProvider;;

	public Join() {
	}

	public Join(IAliasProvider aliasProvider, JOIN_TYPE joinType, String field) {
		super();
		this.alias = aliasProvider.getAliasFor(this);
		this.joinType = joinType;
		this.field = field;
		this.aliasProvider = aliasProvider;
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

	/** {@inheritDoc} */
	@Override
	public String getAlias() {
		return alias;
	}

	public String getField() {
		return field;
	}

	public String getJoinClause() {

		String result = "";
		String delimiter = "";

		for (Map.Entry<String, Join> join : joins.entrySet()) {

			result += delimiter + join.getValue().getJoinType() + " " + join.getValue().getAlias() + "." + join.getValue().getField() + " " + join.getValue().getAlias() + " " + join.getValue().getJoinClause();
			delimiter = " ";
		}
		return result;
	}

	/**
	 * Returns the type of the join
	 * 
	 * @return
	 */
	public JOIN_TYPE getJoinType() {
		return joinType;
	}

	public Collection<Join> getJoins() {
		return joins.values();
	}

}
