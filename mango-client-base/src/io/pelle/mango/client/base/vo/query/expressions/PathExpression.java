package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;

import com.google.common.base.Objects;

public class PathExpression implements Serializable, IExpression {

	private static final long serialVersionUID = 6136700255909180860L;

	private String path;

	private String className;

	public PathExpression() {
	}

	public PathExpression(String className, String path) {
		this.path = path;
		this.className = className;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return aliasProvider.getAliasFor(className) + "." + path;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("className", className).add("path", path).toString();
	}

	public String getClassName() {
		return className;
	}

}