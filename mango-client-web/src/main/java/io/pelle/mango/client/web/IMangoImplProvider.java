package io.pelle.mango.client.web;

import io.pelle.mango.client.IMangoGwtRemoteServiceLocator;
import io.pelle.mango.client.web.util.ICustomCompositeFactory;

public interface IMangoImplProvider {

	IMangoGwtRemoteServiceLocator getGwtRemoteServiceLocatorInstance();
	
	MangoMessages getMessages();
	
	String getI18NString(String key);
	
	ICustomCompositeFactory getCompositeFactory();
	
}
