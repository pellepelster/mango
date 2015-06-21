package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeTracker implements IChangeTracker, Serializable {

	private static final long serialVersionUID = -7165340661545211047L;

	private Map<String, Object> changes = new HashMap<String, Object>();

	private List<String> loadedAttributes = new ArrayList<String>();

	private IVOEntity voEntity;

	private ChangeTracker() {
	}

	public ChangeTracker(IVOEntity voEntity) {
		this.voEntity = voEntity;
	}

	public Map<String, Object> getChanges() {
		return changes;
	}

	public void setChanges(Map<String, Object> changes) {
		this.changes = changes;
	}

	public void addChange(String attributeName, Object value) {
		this.changes.put(attributeName, value);
	}

	@Override
	public void setLoaded(String attributeName) {
		loadedAttributes.add(attributeName);
	}

	public void checkLoaded(String attributeName) {
		if (loadedAttributes != null && !voEntity.isNew() && !loadedAttributes.contains(attributeName)) {
			throw new RuntimeException("attribute '" + attributeName + "' is not loaded for class '" + voEntity.getClass() + "'");
		}
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

	@Override
	public void disableLoadChecking() {
		loadedAttributes = null;
	}

}
