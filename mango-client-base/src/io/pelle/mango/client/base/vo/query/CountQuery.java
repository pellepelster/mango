package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

@SuppressWarnings("serial")
public class CountQuery<T extends IVOEntity> extends BaseQuery<T, CountQuery<T>> {

	public CountQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
	}

	public static final <T extends IVOEntity> CountQuery<T> countFrom(Class<T> entity) {
		return new CountQuery<T>(entity);
	}

	public CountQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
		return this;
	}

	@Override
	protected CountQuery<T> getQuery() {
		return this;
	}

}
