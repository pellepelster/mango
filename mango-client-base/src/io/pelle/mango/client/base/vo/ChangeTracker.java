package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChangeTracker implements IChangeTracker, Serializable {
	private static final long serialVersionUID = -7165340661545211047L;

	private Map<String, Object> changes = new HashMap<String, Object>();

	public ChangeTracker() {
	}

	public void addChange(String attributeName, Object value) {
		this.changes.put(attributeName, value);
	}

	@Override
	public boolean hasChanges() {
		return !this.changes.isEmpty();
	}

	@Override
	public void clearChanges() {
		this.changes.clear();
	}

	@Override
	public void copyChanges(IChangeTracker source) {
		this.changes = new HashMap<String, Object>(((ChangeTracker) source).changes);
	}

}
