package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;

public class SelectQuery<T extends IVOEntity> extends BaseQuery<T, SelectQuery<T>> implements Serializable {

	public SelectQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from));
	}

	public static final <T extends IVOEntity> SelectQuery<T> selectFrom(Class<T> entity) {
		return new SelectQuery<T>(entity);
	}

	public SelectQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from));
		return this;
	}

	public String getJPQL(IEntityVOMapper entityVOMapper) {

		String result = "SELECT " + getSelectClause() + " FROM " + getFromClause(entityVOMapper) + " " + getJoinClause() + " " + getWhereClause();

		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");

	}

	public SelectQuery() {
		super();
	}

	@Override
	protected SelectQuery<T> getQuery() {
		return this;
	}

}
