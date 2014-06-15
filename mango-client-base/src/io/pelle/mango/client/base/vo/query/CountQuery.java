package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;

public class CountQuery<T extends IVOEntity> extends BaseQuery<T, CountQuery<T>> {

	public CountQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from));
	}

	public static final <T extends IVOEntity> CountQuery<T> countFrom(Class<T> entity) {
		return new CountQuery<T>(entity);
	}

	public CountQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from));
		return this;
	}

	public String getJPQL(IEntityVOMapper entityVOMapper) {

		String result = "SELECT COUNT(" + getSelectClause() + ") FROM " + getFromClause(entityVOMapper) + " " + getJoinClause() + " " + getWhereClause();
		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");

	}

	@Override
	protected CountQuery<T> getQuery() {
		return this;
	}

}
