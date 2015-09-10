package io.pelle.mango.client.web;

import io.pelle.mango.client.IMangoGwtRemoteServiceLocator;

public interface IMangoImplProvider {

	IMangoGwtRemoteServiceLocator getGwtRemoteServiceLocatorInstance();
	
	MangoMessages getMessages();
	
	String getI18NString(String key);
	
}
