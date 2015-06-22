package io.pelle.mango.server.base;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.VOEntityMetadata;

public abstract class BaseEntity implements IBaseEntity {

	private VOEntityMetadata metadata;

	private long oid;

	public BaseEntity() {
		super();
		metadata = new VOEntityMetadata(this, false);
	}

	@Override
	public boolean isNew() {
		return getId() == IBaseVO.NEW_VO_ID;
	}

	@Override
	public void setOid(long oid) {
		this.oid = oid;
	}

	@Override
	public long getOid() {
		if (isNew()) {
			return this.oid;
		} else {
			return getId();
		}
	}

	@Override
	public VOEntityMetadata getMetadata() {
		return metadata;
	}

	@Override
	public String getNaturalKey() {
		return toString();
	}

	@Override
	public boolean hasNaturalKey() {
		return false;
	}

}