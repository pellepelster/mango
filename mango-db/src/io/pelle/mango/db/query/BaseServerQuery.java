package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.BaseQuery;
import io.pelle.mango.client.base.vo.query.Entity;
import io.pelle.mango.client.base.vo.query.IEntity;
import io.pelle.mango.client.base.vo.query.Join;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.Collection;

public class BaseServerQuery<T extends IVOEntity, Q> {

	private BaseQuery<T, Q> baseQuery;

	protected BaseServerQuery(BaseQuery<T, Q> baseQuery) {
		this.baseQuery = baseQuery;
	}

	protected String getSelectClause() {
		String result = "";
		String delimiter = "";

		for (IEntity entity : baseQuery.getFroms()) {
			result += delimiter + entity.getAlias();
			delimiter = ", ";
		}

		return result;
	}

	protected String getFromClause(IEntityVOMapper entityVOMapper) {
		String result = "";
		String delimiter = "";

		for (Entity entity : baseQuery.getFroms()) {
			result += delimiter + "" + entityVOMapper.getEntityClass(EntityVOMapper.getInstance().getVOClass(entity.getClassName())).getSimpleName() + " " + entity.getAlias();
			delimiter = ", ";
		}

		return result;
	}

	protected String getOrderClause() {

		StringBuilder result = new StringBuilder();

		if (!baseQuery.getOrderBys().isEmpty()) {
			result.append("ORDER BY ");
		}

		String delimiter = "";
		for (IAttributeDescriptor<?> orderBy : baseQuery.getOrderBys()) {

			result.append(delimiter);
			result.append(orderBy.getAttributeName());
			delimiter = ", ";
		}

		if (!baseQuery.getOrderBys().isEmpty()) {
			result.append(" " + baseQuery.getSortOrder());
		}

		return result.toString();
	}

	private void addJoins(StringBuilder result, String parentAlias, Collection<Join> joins) {

		for (Join join : joins) {
			result.append(" ");
			result.append(join.getJoinType() + " " + parentAlias + "." + join.getField() + " " + join.getAlias());
			addJoins(result, join.getAlias(), join.getJoins());
		}
	}

	protected String getWhereClause() {

		StringBuilder result = new StringBuilder();

		if (baseQuery.getWhereExpression().isPresent()) {
			result.append("WHERE ");
			result.append(baseQuery.getWhereExpression().get().getJPQL(baseQuery.getAliasProvider()));
		}

		return result.toString();
	}

	public BaseQuery<T, Q> getBaseQuery() {
		return baseQuery;
	}

	protected String getJoinClause() {

		StringBuilder result = new StringBuilder();

		for (Entity entity : baseQuery.getFroms()) {
			addJoins(result, entity.getAlias(), entity.getJoins());
		}

		return result.toString();
	}

}
