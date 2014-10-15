package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;

import java.io.Serializable;

import com.google.common.base.Optional;

public class ExpressionFactory {

	public static Optional<IBooleanExpression> createEqualsExpression(Class<? extends IBaseVO> clazz, String attributePath, Serializable value) {

		IBooleanExpression compareExpression = null;

		if (value != null) {
			PathExpression pathExpression = new PathExpression(clazz.getName(), attributePath);
			compareExpression = new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new ObjectExpression(value));
		}

		return Optional.fromNullable(compareExpression);

	}

	public static Optional<IBooleanExpression> createStringEqualsExpression(Class<? extends IVOEntity> clazz, String attributePath, String value) {

		IBooleanExpression compareExpression = null;

		if (value != null && !value.trim().isEmpty()) {
			PathExpression pathExpression = new PathExpression(clazz.getName(), attributePath);
			compareExpression = new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new StringExpression(value));
		}

		return Optional.fromNullable(compareExpression);

	}

	public static Optional<IBooleanExpression> createStringStartsWithExpression(Class<? extends IVOEntity> clazz, String attributePath, String value) {

		IBooleanExpression compareExpression = null;

		if (value != null && !value.trim().isEmpty()) {
			PathExpression pathExpression = new PathExpression(clazz.getName(), attributePath);
			compareExpression = new CompareExpression(pathExpression, ComparisonOperator.LIKE_NO_CASE, new StringExpression(value + "%"));
		}

		return Optional.fromNullable(compareExpression);

	}

	public static IBooleanExpression createLongExpression(Class<? extends IBaseVO> clazz, String attributePath, long value) {

		IBooleanExpression compareExpression = null;

		PathExpression pathExpression = new PathExpression(clazz.getName(), attributePath);
		compareExpression = new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new LongExpression(value));

		return compareExpression;

	}

}
