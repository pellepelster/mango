package io.pelle.mango.client.base.vo.query;

import java.util.HashMap;
import java.util.Map;

public class AliasProvider implements IAliasProvider {

	private int aliasCounter = -1;

	private Map<Object, String> aliases = new HashMap<Object, String>();

	private class ClassAlias {

		private Class<?> clazz;

		public ClassAlias(Class<?> clazz) {
			super();
			this.clazz = clazz;
		}

		@Override
		public int hashCode() {
			// TODO dirty hack
			return 1;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO dirty hack

			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			ClassAlias other = (ClassAlias) obj;

			if (clazz == null) {
				if (other.clazz != null)
					return false;
			}

			return (other.clazz.equals(clazz) || clazz.equals(other.clazz));
		}

	}

	@Override
	public String getAliasFor(Object key) {

		Object aliasKey = key;

		if (key instanceof Class) {
			aliasKey = new ClassAlias((Class<?>) key);
		}

		if (!aliases.containsKey(aliasKey)) {
			aliasCounter++;
			aliases.put(aliasKey, "x" + Integer.toString(aliasCounter));
		}

		return aliases.get(aliasKey);
	}
}
