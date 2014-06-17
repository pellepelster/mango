package io.pelle.mango.client.base.vo.query;

@SuppressWarnings("serial")
public class StringExpression implements IExpression {

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
		return "'" + value + "'";
	}

}