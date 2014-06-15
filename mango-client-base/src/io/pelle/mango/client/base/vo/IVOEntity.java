package io.pelle.mango.client.base.vo;

public interface IVOEntity extends IHasChangeTracker {

	public static final String ID_FIELD_NAME = "id";

	long getId();

	long getOid();

	boolean isNew();

	void setOid(long oid);

}
