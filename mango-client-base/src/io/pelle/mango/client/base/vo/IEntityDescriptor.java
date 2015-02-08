package io.pelle.mango.client.base.vo;

public interface IEntityDescriptor<T extends IVOEntity> extends IMetaDescriptor {

	Class<? extends IVOEntity> getVOEntityClass();

	String getLabel();

	String getPluralLabel();

	String getName();
}
