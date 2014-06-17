package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.BaseQuery;
import io.pelle.mango.client.base.vo.query.Entity;
import io.pelle.mango.client.base.vo.query.IEntity;
import io.pelle.mango.client.base.vo.query.Join;
import io.pelle.mango.db.util.DBUtil;

import java.util.Collection;

public class BaseServerQuery<T extends IVOEntity, Q> {
	
	private BaseQuery<T, Q> selectQuery;
	
	protected BaseServerQuery(BaseQuery<T, Q> selectQuery)
	{
		this.selectQuery = selectQuery;
	}
	
	protected String getSelectClause() {
		String result = "";
		String delimiter = "";

		for (IEntity entity : selectQuery.getFroms()) {
			result += delimiter + entity.getAlias();
			delimiter = ", ";
		}

		return result;
	}

	protected String getFromClause(IEntityVOMapper entityVOMapper) {
		String result = "";
		String delimiter = "";

		for (Entity entity : selectQuery.getFroms()) {
			result += delimiter + "" + entityVOMapper.getEntityClass(DBUtil.getClassOrDie(entity.getClassName())).getSimpleName() + " " + entity.getAlias();
			delimiter = ", ";
		}

		return result;
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

		if (selectQuery.getWhereExpression().isPresent()) {
			result.append("WHERE ");
			result.append(selectQuery.getWhereExpression().get().getJPQL(selectQuery.getAliasProvider()));
		}

		return result.toString();
	}
	
	protected String getJoinClause() {

		StringBuilder result = new StringBuilder();

		for (Entity entity : selectQuery.getFroms()) {
			addJoins(result, entity.getAlias(), entity.getJoins());
		}

		return result.toString();
	}


}
