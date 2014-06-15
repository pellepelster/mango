package io.pelle.mango.client.base.vo.query;

public interface IBooleanExpression extends IExpression {

	IExpression and(IExpression expression);

	IExpression or(IExpression expression);

}
