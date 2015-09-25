package io.pelle.mango.demo.server;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.MangoGwtAsyncAdapterRemoteServiceLocator;
import io.pelle.mango.client.IMangoGwtRemoteServiceLocator;
import io.pelle.mango.client.web.IMangoImplProvider;
import io.pelle.mango.client.web.MangoMessages;
import io.pelle.mango.client.web.util.I18NProxy;
import io.pelle.mango.client.web.util.ICustomCompositeFactory;
import io.pelle.mango.demo.server.util.ReflectionCustomCompositeFactory;

public class SpringMangoImplProvider implements IMangoImplProvider {

	private MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator;
	
	private MangoMessages messages;

	private ICustomCompositeFactory compositeFactory;
	
	public SpringMangoImplProvider() {
		super();
		this.messages = I18NProxy.create(MangoMessages.class);
		compositeFactory = new ReflectionCustomCompositeFactory();
	}

	@Autowired
	public void setMangoGwtAsyncAdapterRemoteServiceLocator(MangoGwtAsyncAdapterRemoteServiceLocator mangoGwtAsyncAdapterRemoteServiceLocator) {
		this.mangoGwtAsyncAdapterRemoteServiceLocator =mangoGwtAsyncAdapterRemoteServiceLocator;
	}

	@Override
	public IMangoGwtRemoteServiceLocator getGwtRemoteServiceLocatorInstance() {
		return mangoGwtAsyncAdapterRemoteServiceLocator;
	}

	@Override
	public MangoMessages getMessages() {
		return messages;
	}

	@Override
	public String getI18NString(String key) {
		return null;
	}

	@Override
	public ICustomCompositeFactory getCompositeFactory() {
		return compositeFactory;
	}

}
