package io.pelle.mango.client.gwt.utils;

import com.google.gwt.core.shared.GWT;

public class CustomGwtCompositeProvider {

	private static CustomGwtCompositeProvider instance;

	private CustomGwtCompositeProvider() {
	}

	private ICustomGwtCompositeFactory factory;

	public static CustomGwtCompositeProvider getInstance() {

		if (instance == null) {

			instance = new CustomGwtCompositeProvider();

			if (GWT.isClient()) {
				instance.setFactory((ICustomGwtCompositeFactory) GWT.create(ICustomGwtCompositeFactory.class));
			}

		}

		return instance;
	}

	public void setFactory(ICustomGwtCompositeFactory factory) {
		this.factory = factory;
	}

	public Object create(String type, io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite baseCustomComposite) {
		return factory.create(type, baseCustomComposite);
	}

}
