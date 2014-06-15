package io.pelle.mango.client.base.vo;

public interface IEntityDescriptor<T extends IVOEntity> {

	Class<? extends IVOEntity> getVOEntityClass();

}
