package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SelectQuery<T extends IVOEntity> extends BaseQuery<T, SelectQuery<T>> implements Serializable {

	public SelectQuery() {
		super();
	}

	public SelectQuery(String className) {
		getFroms().add(new Entity(aliasProvider, className));
	}

	public SelectQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
	}

	public static final <T extends IVOEntity> SelectQuery<T> selectFrom(Class<T> entity) {
		return new SelectQuery<T>(entity);
	}

	public SelectQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
		return this;
	}

	@Override
	protected SelectQuery<T> getQuery() {
		return this;
	}

}
