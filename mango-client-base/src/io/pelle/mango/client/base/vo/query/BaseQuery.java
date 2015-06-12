package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.expressions.BooleanExpression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

@SuppressWarnings("serial")
public abstract class BaseQuery<T extends IVOEntity, Q> implements Serializable {

	private List<IAttributeDescriptor<?>> orderBys = new ArrayList<IAttributeDescriptor<?>>();

	protected IAliasProvider aliasProvider = new AliasProvider();

	private Optional<IBooleanExpression> whereExpression = Optional.absent();

	private List<Entity> froms = new ArrayList<Entity>();

	public enum SORT_ORDER {
		DESC, ASC
	}

	private SORT_ORDER sortOrder = SORT_ORDER.ASC;

	public BaseQuery() {
		super();
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

	public IAliasProvider getAliasProvider() {
		return aliasProvider;
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

	public void setFroms(List<Entity> froms) {
		this.froms = froms;
	}

	public Q where(IBooleanExpression expression) {

		whereExpression = Optional.of(expression);

		return getQuery();
	}

	public Q addWhereAnd(IBooleanExpression expression) {

		if (whereExpression.isPresent()) {
			whereExpression.get().and(expression);
		} else {
			where(expression);
		}

		return getQuery();
	}

	public Q addWhereOr(IBooleanExpression expression) {

		if (whereExpression.isPresent()) {
			whereExpression.get().or(expression);
		} else {
			where(expression);
		}

		return getQuery();
	}

	public Q orderBy(IAttributeDescriptor<?> orderBy) {
		orderBys.add(orderBy);
		return getQuery();
	}

	public abstract Q getQuery();

	public Optional<IBooleanExpression> getWhereExpression() {
		return whereExpression;
	}

	public List<IAttributeDescriptor<?>> getOrderBys() {
		return orderBys;
	}

	public SORT_ORDER getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SORT_ORDER sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Q descending() {
		this.sortOrder = SORT_ORDER.DESC;
		return getQuery();
	}

	public Q ascending() {
		this.sortOrder = SORT_ORDER.ASC;
		return getQuery();
	}

}
