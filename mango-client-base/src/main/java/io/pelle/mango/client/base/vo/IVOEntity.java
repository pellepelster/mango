package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.HashMap;

public interface IVOEntity extends IHasMetadata, Serializable {

	public static final String OPERATION_CREATE = "CREATE";

	public static final String OPERATION_UPDATE = "UPDATE";
	
	public static final String OPERATION_READ = "READ";
	
	public static final String OPERATION_DELETE = "DELETE";

	public static final String ID_FIELD_NAME = "id";

	long getId();

	long getOid();

	boolean isNew();

	void setOid(long oid);

	String getNaturalKey();

	boolean hasNaturalKey();

	HashMap<String, Object> getData();

}
