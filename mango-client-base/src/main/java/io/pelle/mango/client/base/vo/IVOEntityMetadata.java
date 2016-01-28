package io.pelle.mango.client.base.vo;

public interface IVOEntityMetadata extends IChangeTracker {


	void setLoaded(String attributeName);

	boolean isLoaded(String attributeName);

}
