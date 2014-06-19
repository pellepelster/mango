package io.pelle.mango.client.base.vo.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class AliasProvider implements IAliasProvider, Serializable {

	private int aliasCounter = -1;

	private Map<Object, String> aliases = new HashMap<Object, String>();

	public AliasProvider() {
		super();
	}

	@Override
	public String getAliasFor(Object key) {

		Object aliasKey = key;

		if (key instanceof String) {
			aliasKey = key;
		} else if (key instanceof Class) {
			aliasKey  = ((Class<?>) key).getName();
		} 

		if (!aliases.containsKey(aliasKey)) {
			aliasCounter++;
			aliases.put(aliasKey, "x" + Integer.toString(aliasCounter));
		}

		return aliases.get(aliasKey);
	}
}
