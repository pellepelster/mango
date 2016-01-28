package io.pelle.mango.client.gwt.utils;

import java.util.HashMap;
import java.util.Map;

import io.pelle.mango.client.base.util.CustomComposite;

public abstract class BaseCustomGwtCompositeGeneratedFactory implements ICustomGwtCompositeFactory {

	private Map<String, FactoryMethod> map = new HashMap<String, FactoryMethod>();

	@Override
	public Object create(String type, io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite baseCustomComposite) {

		FactoryMethod m = map.get(type);

		if (m != null) {
			return m.create(baseCustomComposite);
		} else {
			throw new RuntimeException("unknown type '" + type + "' did you forget to annotate it using '" + CustomComposite.class.getName() + "'");
		}
	}

	protected void register(String type, FactoryMethod factory) {
		map.put(type, factory);
	}

	protected interface FactoryMethod {
		public Object create(io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite baseCustomComposite);
	}

}
