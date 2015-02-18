package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class NumberExpression implements IExpression, Serializable {

	private Number value;

	public NumberExpression() {
	}

	public NumberExpression(Number value) {
		this.value = value;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return value.toString();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("value", value).toString();
	}

}