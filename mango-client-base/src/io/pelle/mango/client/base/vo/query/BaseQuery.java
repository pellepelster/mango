package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

@SuppressWarnings("serial")
public abstract class BaseQuery<T extends IVOEntity, Q> implements Serializable {

	protected IAliasProvider aliasProvider = new AliasProvider();

	private Optional<IExpression> whereExpression = Optional.absent();

	private List<Entity> froms = new ArrayList<Entity>();

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

	public Q where(IExpression expression) {
		whereExpression = Optional.of(expression);
		return getQuery();

	}

	protected abstract Q getQuery();

	public Optional<IExpression> getWhereExpression() {
		return whereExpression;
	}

}
