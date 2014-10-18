package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class BigDecimalExpression implements IExpression, Serializable {

	private BigDecimal value;

	public BigDecimalExpression() {
	}

	public BigDecimalExpression(BigDecimal value) {
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