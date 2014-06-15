package io.pelle.mango.client.base.vo.query;

import java.io.Serializable;

import com.google.common.base.Objects;

public class LongExpression implements Serializable, IExpression {

	private static final long serialVersionUID = -7175999509584167909L;

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