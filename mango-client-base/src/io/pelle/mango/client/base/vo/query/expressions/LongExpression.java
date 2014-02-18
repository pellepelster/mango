package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class LongExpression implements IExpression {

	private long value;

	public LongExpression() {
	}

	public LongExpression(long value) {
		this.value = value;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return Long.toString(value);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("value", value).toString();
	}

}