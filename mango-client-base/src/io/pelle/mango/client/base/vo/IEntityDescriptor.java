package io.pelle.mango.client.base.vo;

public interface IEntityDescriptor<T extends IVOEntity> extends IMetaDescriptor<T> {

	String getLabel();

	String getPluralLabel();

	String getName();
}
