package io.pelle.mango.client.base.vo.query;

import com.google.common.base.Objects;

public class LikeExpression extends BaseBooleanExpression {

	private PathExpression expression1;

	private StringExpression expression2;

	private boolean caseInsensitive = false;

	public LikeExpression(PathExpression expression1, StringExpression expression2, boolean caseInsensitive) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.caseInsensitive = caseInsensitive;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		expression2.appendToValue("%");

		if (caseInsensitive) {
			return ("LOWER(" + expression1.getJPQL(aliasProvider) + ") LIKE LOWER(" + expression2.getJPQL(aliasProvider) + ") " + super.getJPQL(aliasProvider)).trim();
		} else {
			return (expression1.getJPQL(aliasProvider) + " LIKE " + expression2.getJPQL(aliasProvider) + " " + super.getJPQL(aliasProvider)).trim();
		}

	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("expression1", expression1).add("expression2", expression2).add("caseInsensitive", caseInsensitive).toString();
	}

}
