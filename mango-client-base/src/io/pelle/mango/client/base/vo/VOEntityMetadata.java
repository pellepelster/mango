package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class VOEntityMetadata implements IVOEntityMetadata, Serializable {

	private static final long serialVersionUID = -7165340661545211047L;

	private List<IVOEntityMetadata> listChangeTrackers = new ArrayList<IVOEntityMetadata>();

	private Map<String, Object> changes = new HashMap<String, Object>();

	private List<String> loadedAttributes = new ArrayList<String>();

	private boolean trackLoadedAttributes = true;

	private IVOEntity voEntity;

	@SuppressWarnings("unused")
	private VOEntityMetadata() {
	}

	public VOEntityMetadata(IVOEntity voEntity, boolean trackLoadedAttributes) {
		this.voEntity = voEntity;
		this.trackLoadedAttributes = trackLoadedAttributes;
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

		if (trackLoadedAttributes && loadedAttributes != null && !voEntity.isNew() && !loadedAttributes.contains(attributeName)) {
			throw new RuntimeException("attribute '" + attributeName + "' is not loaded for class '" + voEntity.getClass() + "'");
		}
	}

	@Override
	public boolean hasChanges() {
		return !this.changes.isEmpty() || Iterables.any(listChangeTrackers, new Predicate<IVOEntityMetadata>() {

			@Override
			public boolean apply(IVOEntityMetadata input) {
				return input.hasChanges();
			}
		});
	}

	@Override
	public void clearChanges() {
		this.changes.clear();
	}

	@Override
	public void copyChanges(IVOEntityMetadata source) {
		this.changes = new HashMap<String, Object>(((VOEntityMetadata) source).changes);
	}

	@Override
	public void disableLoadChecking() {
		loadedAttributes = null;
	}

	public void addListChangeTracker(IVOEntityMetadata listChangeTracker) {
		listChangeTrackers.add(listChangeTracker);
	}

	@Override
	public boolean isLoaded(String attributeName) {
		return !trackLoadedAttributes || loadedAttributes.contains(attributeName);
	}

}
