package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;
import io.pelle.mango.client.base.vo.query.LOGICAL_OPERATOR;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class BooleanExpression extends BaseBooleanExpression {

	private IExpression expression1;
	private LOGICAL_OPERATOR logicalOperator;
	private IExpression expression2;

	public BooleanExpression() {
	}

	public BooleanExpression(IExpression expression1, LOGICAL_OPERATOR logicalOperator, IExpression expression2) {
		super();
		this.expression1 = expression1;
		this.logicalOperator = logicalOperator;
		this.expression2 = expression2;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("expression1", expression1).add("logicalOperator", logicalOperator).add("expression2", expression2).toString();
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return "(" + expression1.getJPQL(aliasProvider) + " " + logicalOperator.toString() + " " + expression2.getJPQL(aliasProvider) + ") " + super.getJPQL(aliasProvider).trim();
	}

}
