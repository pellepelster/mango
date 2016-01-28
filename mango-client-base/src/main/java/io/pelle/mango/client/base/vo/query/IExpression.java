package io.pelle.mango.client.base.vo.query;

import java.io.Serializable;

public interface IExpression extends Serializable {

	String getJPQL(IAliasProvider aliasProvider);

}
