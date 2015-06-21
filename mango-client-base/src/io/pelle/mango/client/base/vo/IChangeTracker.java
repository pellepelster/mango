package io.pelle.mango.client.base.vo;

public interface IChangeTracker {

	boolean hasChanges();

	void clearChanges();

	void copyChanges(IChangeTracker sourceChangeTracker);

	void setLoaded(String attributeName);

	void disableLoadChecking();

}
