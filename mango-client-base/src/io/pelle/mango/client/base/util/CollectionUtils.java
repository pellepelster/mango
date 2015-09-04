/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
	public static <T> List<T> copyToArrayList(List<T> fromList) {
		ArrayList<T> result = new ArrayList<T>();

		for (T listItem : fromList) {
			result.add(listItem);
		}

		return result;
	}

	public static HashMap<String, String> copyMap(Map<String, Object> map) {
		HashMap<String, String> result = new HashMap<String, String>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String value = null;

			if (entry.getValue() != null) {
				value = entry.getValue().toString();
			}

			result.put(entry.getKey(), value);
		}

		return result;
	}

	public static HashMap<String, Object> getMap(String key1, Object value1) {
		return getMap(key1, value1, null, null, null, null);
	}

	public static HashMap<String, Object> getMap(String key1, Object value1, String key2, Object value2) {
		return getMap(key1, value1, key2, value2, null, null);
	}

	// no varargs in gwt sadly
	public static HashMap<String, Object> getMap(String key1, Object value1, String key2, Object value2, String key3, Object value3) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		if (key1 != null) {
			result.put(key1, value1);
		}

		if (key2 != null) {
			result.put(key2, value2);
		}

		if (key3 != null) {
			result.put(key3, value3);
		}

		return result;
	}
}
