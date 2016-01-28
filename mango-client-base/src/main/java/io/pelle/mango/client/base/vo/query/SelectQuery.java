package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class SelectQuery<T extends IVOEntity> extends BaseQuery<T, SelectQuery<T>> implements Serializable {

	private int maxResults = MAX_RESULTS_DEFAULT;

	private int firstResult = FIRST_RESULT_DEFAULT;

	public static int MAX_RESULTS_DEFAULT = 50;

	public static int FIRST_RESULT_DEFAULT = 0;

	private Map<String, Object> data = new HashMap<String, Object>();

	private boolean loadNaturalKeyReferences;

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
	public SelectQuery<T> getQuery() {
		return this;
	}

	public SelectQuery<T> setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public SelectQuery<T> setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public SelectQuery<T> loadNaturalKeyReferences(boolean loadNaturalKeyReferences) {
		this.loadNaturalKeyReferences = loadNaturalKeyReferences;
		return this;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public boolean isLoadNaturalKeyReferences() {
		return loadNaturalKeyReferences;
	}

	public Map<String, Object> getData() {
		return data;
	}

}
