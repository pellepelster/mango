package io.pelle.mango.client.base.vo.query;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public abstract class BaseBooleanExpression implements IBooleanExpression {

	private Map<IExpression, LOGICAL_OPERATOR> expressions = new HashMap<IExpression, LOGICAL_OPERATOR>();

	public BaseBooleanExpression() {
		super();
	}

	@Override
	public IBooleanExpression and(IExpression andExpression) {
		expressions.put(andExpression, LOGICAL_OPERATOR.AND);
		return this;
	}

	@Override
	public IBooleanExpression or(IExpression orExpression) {
		expressions.put(orExpression, LOGICAL_OPERATOR.OR);
		return this;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<IExpression, LOGICAL_OPERATOR> expressionEntry : expressions.entrySet()) {

			sb.append(expressionEntry.getValue().toString());
			sb.append(" ");
			sb.append(expressionEntry.getKey().getJPQL(aliasProvider));
		}

		return sb.toString();
	}

}
