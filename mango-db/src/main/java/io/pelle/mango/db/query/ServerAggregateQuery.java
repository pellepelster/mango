package io.pelle.mango.db.query;

import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.AggregateQuery;

public class ServerAggregateQuery<T extends IVOEntity> extends
		BaseServerQuery<T, AggregateQuery<T>> {

	private ServerAggregateQuery(AggregateQuery<T> query) {
		super(query);
	}

	public static <T extends IVOEntity> ServerAggregateQuery<T> adapt(
			AggregateQuery<T> query) {
		return new ServerAggregateQuery<>(query);
	}

	private String getSumSelectClause() {
		
		StringBuilder sb = new StringBuilder();
		
		for (IAttributeDescriptor<?> attributeDescriptor :  getBaseQuery().getQuery().getSums()) {
			
			if (attributeDescriptor.getParent() instanceof EntityDescriptor) {
				EntityDescriptor<?> entityDescriptor = (EntityDescriptor<?>) attributeDescriptor.getParent();

				String alias = getBaseQuery().getAliasProvider().getAliasFor(entityDescriptor.getVOEntityClass());

				sb.append("SUM(");
				sb.append(alias);
				sb.append(".");
				sb.append(attributeDescriptor.getAttributeName());
				sb.append(")");
			}
			
		}
		
		return sb.toString();
	}
	
	public String getJPQL(IEntityVOMapper entityVOMapper) {
		
		String result = "SELECT " + getSumSelectClause() + " FROM "
				+ getFromClause(entityVOMapper) + " " + getJoinClause() + " "
				+ getWhereClause();
		return result.trim().replaceAll("\\b\\s{2,}\\b", " ");
	}
}
