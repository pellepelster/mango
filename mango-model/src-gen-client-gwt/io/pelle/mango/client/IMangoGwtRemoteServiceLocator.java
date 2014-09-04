package io.pelle.mango.client;

public interface IMangoGwtRemoteServiceLocator {

	io.pelle.mango.client.IHierachicalServiceGWTAsync getHierachicalService();
	io.pelle.mango.client.ISystemServiceGWTAsync getSystemService();
	io.pelle.mango.client.IBaseEntityServiceGWTAsync getBaseEntityService();
}
