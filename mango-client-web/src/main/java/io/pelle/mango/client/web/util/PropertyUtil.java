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
package io.pelle.mango.client.web.util;

import java.util.Map;

/**
 * Various utilities for (GWT compatible) property access
 * 
 * @author pelle
 * 
 */
public class PropertyUtil
{

	/**
	 * Returns a value from a map (by key) or a default value if the key does
	 * not exist
	 * 
	 * @param key
	 * @param parameters
	 * @param defaultValue
	 * @return
	 */
	public static Object getFromMapWithDefault(String key, Map<String, Object> parameters, Object defaultValue)
	{
		if (parameters.containsKey(key))
		{
			return parameters.get(key);
		}
		else
		{
			return defaultValue;
		}
	}

	public static String getStringFromObject(Object o)
	{
		if (o != null)
		{
			return o.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 * 
	 */
	private PropertyUtil()
	{
	}
}
