package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;

public class ServerSelectQuery<T extends IVOEntity> extends BaseServerQuery<T, SelectQuery<T>> {

	private ServerSelectQuery(SelectQuery<T> selectQuery) {
		super(selectQuery);
	}

	public static <T extends IVOEntity> ServerSelectQuery<T> adapt(SelectQuery<T> selectQuery) {
		return new ServerSelectQuery<>(selectQuery);
	}

	public String getJPQL(IEntityVOMapper entityVOMapper) {

		String result = "SELECT " + getSelectClause() + " FROM " + getFromClause(entityVOMapper) + " " + getJoinClause() + " " + getWhereClause() + " " + getOrderClause();

		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");
	}

}
