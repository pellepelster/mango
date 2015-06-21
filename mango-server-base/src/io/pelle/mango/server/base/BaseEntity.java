package io.pelle.mango.server.base;

import io.pelle.mango.client.base.vo.ChangeTracker;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;

public abstract class BaseEntity implements IBaseEntity {

	private ChangeTracker changeTracker;

	private long oid;

	public BaseEntity() {
		super();
		changeTracker = new ChangeTracker(this);
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
	public ChangeTracker getChangeTracker() {
		return changeTracker;
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