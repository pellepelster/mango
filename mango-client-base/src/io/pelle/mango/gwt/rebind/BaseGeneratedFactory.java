package io.pelle.mango.gwt.rebind;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseGeneratedFactory {

	private Map<String, FactoryMethod> map = new HashMap<String, FactoryMethod>();

	public Object create(String type) {

		FactoryMethod m = map.get(type);

		if (m != null) {
			return m.create();
		} else {
			throw new RuntimeException("Unknown type: " + type);

		}
	}

	protected void register(String type, FactoryMethod factory) {
		map.put(type, factory);
	}

	protected interface FactoryMethod {
		public Object create();
	}

}
