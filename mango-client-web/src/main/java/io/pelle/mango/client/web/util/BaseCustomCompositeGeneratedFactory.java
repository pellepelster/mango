package io.pelle.mango.client.web.util;

import java.util.HashMap;
import java.util.Map;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.util.CustomComposite;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public abstract class BaseCustomCompositeGeneratedFactory implements ICustomCompositeFactory {

	private Map<String, FactoryMethod> map = new HashMap<String, FactoryMethod>();

	@Override
	public Object create(String type, ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent) {

		FactoryMethod m = map.get(type);

		if (m != null) {
			return m.create(compositeModel, parent);
		} else {
			throw new RuntimeException("unknown type '" + type + "' did you forget to annotate it using '" + CustomComposite.class.getName() + "'");

		}
	}

	protected void register(String type, FactoryMethod factory) {
		map.put(type, factory);
	}

	protected interface FactoryMethod {
		public Object create(ICustomCompositeModel compositeModel, BaseDictionaryElement<? extends IBaseModel> parent);
	}

}
