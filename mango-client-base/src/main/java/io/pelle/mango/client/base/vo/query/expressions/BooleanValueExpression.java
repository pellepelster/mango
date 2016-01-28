package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class BooleanValueExpression implements IExpression, Serializable {

	private Boolean value;

	public BooleanValueExpression() {
	}

	public BooleanValueExpression(Boolean value) {
		this.value = value;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return Boolean.toString(value);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("value", value).toString();
	}

}