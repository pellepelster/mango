package io.pelle.mango.client.base.vo.query;

import java.io.Serializable;

public class StringExpression implements Serializable, IExpression {

	private static final long serialVersionUID = -7175999509584167909L;

	private String value;

	public StringExpression() {
	}

	public StringExpression(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public void appendToValue(String append) {
		value += append;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return "'" +  value + "'";
	}

}