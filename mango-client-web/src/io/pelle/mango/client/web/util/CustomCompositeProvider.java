package io.pelle.mango.client.web.util;

import com.google.gwt.core.shared.GWT;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class CustomCompositeProvider {

	private static CustomCompositeProvider instance;

	private CustomCompositeProvider() {
	}

	private ICustomCompositeFactory factory;

	public static CustomCompositeProvider getInstance() {

		if (instance == null) {

			instance = new CustomCompositeProvider();

			if (GWT.isClient()) {
				instance.setFactory((ICustomCompositeFactory) GWT.create(ICustomCompositeFactory.class));
			}

		}

		return instance;
	}

	public void setFactory(ICustomCompositeFactory factory) {
		this.factory = factory;
	}

	public Object create(String type, ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		return factory.create(type, compositeModel, parent);
	}

}
