package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;

public class ExpressionFactory {

	public static IBooleanExpression createStringEqualsExpression(Class<? extends IBaseVO> clazz, String attributePath, String value) {
		
		PathExpression pathExpression = new PathExpression(clazz.getName(), attributePath);
		CompareExpression compareExpression = new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new StringExpression(value));
		
		return compareExpression;
	}

}
