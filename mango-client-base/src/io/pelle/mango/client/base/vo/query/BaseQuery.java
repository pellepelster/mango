package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;

public abstract class BaseQuery<T extends IVOEntity, Q> {

	protected IAliasProvider aliasProvider = new AliasProvider();

	private Optional<IExpression> whereExpression = Optional.absent();

	private List<Entity> froms = new ArrayList<Entity>();

	protected String getWhereClause() {

		StringBuilder result = new StringBuilder();

		if (whereExpression.isPresent()) {
			result.append("WHERE ");
			result.append(whereExpression.get().getJPQL(aliasProvider));
		}

		return result.toString();
	}

	protected String getSelectClause() {
		String result = "";
		String delimiter = "";

		for (IEntity entity : froms) {
			result += delimiter + entity.getAlias();
			delimiter = ", ";
		}

		return result;
	}

	protected String getFromClause(IEntityVOMapper entityVOMapper) {
		String result = "";
		String delimiter = "";

		for (Entity entity : froms) {
			result += delimiter + "" + entity.getName(entityVOMapper) + " " + entity.getAlias();
			delimiter = ", ";
		}

		return result;
	}

	protected String getJoinClause() {

		StringBuilder result = new StringBuilder();

		for (Entity entity : froms) {
			addJoins(result, entity.getAlias(), entity.getJoins());
		}

		return result.toString();
	}

	public Q join(IAttributeDescriptor<?>... attributeDescriptors) {

		Join join = null;

		for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors) {
			if (join == null) {
				join = froms.get(froms.size() - 1).join(attributeDescriptor);
			} else {
				join = join.join(attributeDescriptor);
			}
		}

		return getQuery();
	}

	private void addJoins(StringBuilder result, String parentAlias, Collection<Join> joins) {

		for (Join join : joins) {
			result.append(" ");
			result.append(join.getJoinType() + " " + parentAlias + "." + join.getField() + " " + join.getAlias());
			addJoins(result, join.getAlias(), join.getJoins());
		}
	}

	public static BooleanExpression and(IExpression expression1, IExpression expression2) {
		return new BooleanExpression(expression1, LOGICAL_OPERATOR.AND, expression2);
	}

	public static BooleanExpression or(IExpression expression1, IExpression expression2) {
		return new BooleanExpression(expression1, LOGICAL_OPERATOR.OR, expression2);
	}

	public List<Entity> getFroms() {
		return froms;
	}

	public Q where(IExpression expression) {
		whereExpression = Optional.of(expression);
		return getQuery();

	}

	protected abstract Q getQuery();
}
