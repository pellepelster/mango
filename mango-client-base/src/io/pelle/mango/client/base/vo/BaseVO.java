package io.pelle.mango.client.base.vo;

import java.util.HashMap;

import io.pelle.mango.client.base.db.vos.UUID;

public abstract class BaseVO implements IBaseVO {

	private VOEntityMetadata metadata;

	private static final long serialVersionUID = -3339163131084690483L;

	private long oid;

	public BaseVO() {
		super();
		this.oid = UUID.uuid().hashCode();
		metadata = new VOEntityMetadata(this, true);
	}

	private HashMap<String, Object> data = new HashMap<String, Object>();

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	@Override
	public HashMap<String, Object> getData() {
		return this.data;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {

		if (this == object) {
			return true;
		}
		if (object == null || object.getClass() != this.getClass()) {
			return false;
		}

		BaseVO baseVO = (BaseVO) object;
		return getOid() == baseVO.getOid();
	}

	@Override
	public long getOid() {
		if (isNew()) {
			return this.oid;
		} else {
			return getId();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (int) (getOid() ^ getOid() >>> 32);
		return hash;
	}

	@Override
	public boolean isNew() {
		return getId() == NEW_VO_ID;
	}

	@Override
	public void setOid(long oid) {
		this.oid = oid;
	}

	@Override
	public String toString() {
		return getClass().getName() + "(id: " + getOid() + ")";
	}

	@Override
	public String getNaturalKey() {
		return toString();
	}

	@Override
	public boolean hasNaturalKey() {
		return false;
	}

	@Override
	public VOEntityMetadata getMetadata() {
		return this.metadata;
	}

	@Override
	public Object get(String name) {
		if (IVOEntity.ID_FIELD_NAME.equals(name)) {
			return getOid();
		}

		throw new java.lang.RuntimeException("no such attribute '" + name + "'");
	}

	@Override
	public void set(String name, Object value) {
		if (IVOEntity.ID_FIELD_NAME.equals(name)) {
			setId((Long) value);
			return;
		}

		throw new java.lang.RuntimeException("no such attribute '" + name + "'");

	}

}
