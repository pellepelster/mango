package io.pelle.mango.client.web;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Dictionary;

import io.pelle.mango.client.IMangoGwtRemoteServiceLocator;
import io.pelle.mango.client.MangoGwtRemoteServiceLocator;
import io.pelle.mango.client.web.util.ICustomCompositeFactory;

public class MangoGwtImplProvider implements IMangoImplProvider {

	private MangoMessages messages;

	private Dictionary dictionary;

	private ICustomCompositeFactory compositeFactory;

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public MangoGwtImplProvider() {
		super();

		if (GWT.isClient()) {
			messages = ((MangoMessages) GWT.create(MangoMessages.class));
			compositeFactory = GWT.create(ICustomCompositeFactory.class);
		}

	}

	@Override
	public IMangoGwtRemoteServiceLocator getGwtRemoteServiceLocatorInstance() {
		return MangoGwtRemoteServiceLocator.getInstance();
	}

	@Override
	public MangoMessages getMessages() {
		return messages;
	}

	@Override
	public String getI18NString(String key) {

		if (dictionary == null) {
			return null;
		}
		String value = dictionary.get(key);

		if (value == null || value.trim().isEmpty()) {
			return null;
		} else {
			return value;
		}

	}

	@Override
	public ICustomCompositeFactory getCompositeFactory() {
		return compositeFactory;
	}

}
