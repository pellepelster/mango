package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IVOEntity;

import java.io.Serializable;

import com.google.common.base.Objects;

public class PathExpression implements Serializable, IExpression {

	private static final long serialVersionUID = 6136700255909180860L;

	private String path;

	private Class<? extends IVOEntity> voEntityClass;

	public PathExpression() {
	}

	public PathExpression(Class<? extends IVOEntity> voEntityClass, String path) {
		this.path = path;
		this.voEntityClass = voEntityClass;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return aliasProvider.getAliasFor(voEntityClass) + "." + path;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("voEntity", voEntityClass).add("path", path).toString();
	}

}