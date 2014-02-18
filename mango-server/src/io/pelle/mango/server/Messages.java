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
package io.pelle.mango.server;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{

	private static final String BUNDLE_NAME = "io.pelle.mango.server.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static final String NAVIGATION_TITLE_ADD = "navigation.title.add";

	public static final String NAVIGATION_TITLE_SEARCH = "navigation.title.search";

	public static final String FILE_NOT_FOUND = "file.notfound";

	public static String getString(String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object... arguments)
	{
		try
		{
			String message = RESOURCE_BUNDLE.getString(key);

			return MessageFormat.format(message, arguments);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
		catch (IllegalArgumentException e)
		{
			return '!' + e.getMessage() + '!';
		}
	}

}
