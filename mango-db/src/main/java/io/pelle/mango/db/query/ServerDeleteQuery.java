package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.DeleteQuery;

public class ServerDeleteQuery<T extends IVOEntity> extends BaseServerQuery<T, DeleteQuery<T>> {

	private ServerDeleteQuery(DeleteQuery<T> deleteQuery) {
		super(deleteQuery);
	}

	public static <T extends IVOEntity> ServerDeleteQuery<T> adapt(DeleteQuery<T> deleteQuery) {
		return new ServerDeleteQuery<>(deleteQuery);
	}

	public String getJPQL(IEntityVOMapper entityVOMapper) {

		String result = "DELETE FROM " + getFromClause(entityVOMapper) + " " + getWhereClause();
		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");

	}
}
