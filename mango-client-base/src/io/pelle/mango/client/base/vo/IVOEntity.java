package io.pelle.mango.client.base.vo;

import java.io.Serializable;

public interface IVOEntity extends IHasChangeTracker, Serializable {

	public static final String ID_FIELD_NAME = "id";

	long getId();

	long getOid();

	boolean isNew();

	void setOid(long oid);

	String getNaturalKey();

	boolean hasNaturalKey();
}
