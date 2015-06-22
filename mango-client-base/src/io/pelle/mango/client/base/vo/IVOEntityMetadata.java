package io.pelle.mango.client.base.vo;

public interface IVOEntityMetadata {

	boolean hasChanges();

	void clearChanges();

	void copyChanges(IVOEntityMetadata sourceChangeTracker);

	void setLoaded(String attributeName);

	boolean isLoaded(String attributeName);

	void disableLoadChecking();

}
