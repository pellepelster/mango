package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

@SuppressWarnings("serial")
public class ObjectExpression implements IExpression {

	private Object value;

	public ObjectExpression() {
	}

	public ObjectExpression(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
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
