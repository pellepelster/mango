package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

@SuppressWarnings("serial")
public class DeleteQuery<T extends IVOEntity> extends BaseQuery<T, DeleteQuery<T>> {

	public DeleteQuery() {
		super();
	}

	public DeleteQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
	}

	public static final <T extends IVOEntity> DeleteQuery<T> deleteFrom(Class<T> entity) {
		return new DeleteQuery<T>(entity);
	}

	public DeleteQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
		return this;
	}

	@Override
	public DeleteQuery<T> getQuery() {
		return this;
	}

}
