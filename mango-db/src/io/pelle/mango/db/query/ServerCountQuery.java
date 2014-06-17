package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.CountQuery;

public class ServerCountQuery<T extends IVOEntity> extends BaseServerQuery<T, CountQuery<T>> {
	
	private ServerCountQuery(CountQuery<T> countQuery)
	{
		super(countQuery);
	}
	
	public static <T extends IVOEntity> ServerCountQuery<T> adapt(CountQuery<T> countQuery)
	{
		return new ServerCountQuery<>(countQuery);
	}
	public String getJPQL(IEntityVOMapper entityVOMapper) {

		String result = "SELECT COUNT(" + getSelectClause() + ") FROM " + getFromClause(entityVOMapper) + " " + getJoinClause() + " " + getWhereClause();
		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");

	}
}
