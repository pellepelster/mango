package io.pelle.mango.client.base.vo;

import java.io.Serializable;

public interface IMetaDescriptor<T> extends Serializable {

	IMetaDescriptor<?> getParent();

	Class<T> getVOEntityClass();

}
