package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ObjectExpression implements IExpression, Serializable {

	private Serializable value;

	public ObjectExpression() {
	}

	public ObjectExpression(Serializable value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return "'" + value + "'";
	}

}
