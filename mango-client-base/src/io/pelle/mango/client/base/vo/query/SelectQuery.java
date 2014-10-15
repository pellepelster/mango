package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SelectQuery<T extends IVOEntity> extends BaseQuery<T, SelectQuery<T>> implements Serializable {

	private int maxResults = MAX_RESULTS_DEFAULT;

	public static int MAX_RESULTS_DEFAULT = 50;

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

	public SelectQuery<T> setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public int getMaxResults() {
		return maxResults;
	}

}
