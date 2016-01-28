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
package io.pelle.mango.client.base.modules.navigation;

import java.util.ArrayList;
import java.util.List;

public class NavigationTreeProvider
{

	private static List<NavigationTreeElement> rootElements = new ArrayList<NavigationTreeElement>();

	public static List<NavigationTreeElement> getRootNavigationElements()
	{
		return rootElements;
	}

	public static void addRootNavigationElement(NavigationTreeElement rootElement)
	{
		rootElements.add(rootElement);
	}

}
