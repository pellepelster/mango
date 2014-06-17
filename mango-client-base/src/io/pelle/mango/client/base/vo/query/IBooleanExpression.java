package io.pelle.mango.client.base.vo.query;

public interface IBooleanExpression extends IExpression {

	IBooleanExpression and(IExpression expression);

	IBooleanExpression or(IExpression expression);

}
