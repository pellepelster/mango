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
package io.pelle.mango.client.web.module;

import java.util.ArrayList;
import java.util.List;

public final class ModuleFactoryRegistry
{

	private static ModuleFactoryRegistry instance;

	public static ModuleFactoryRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new ModuleFactoryRegistry();
		}

		return instance;
	}

	private final List<IModuleFactory> moduleFactories = new ArrayList<IModuleFactory>();

	private ModuleFactoryRegistry()
	{
	}

	public void addModuleFactory(IModuleFactory moduleFactory)
	{
		this.moduleFactories.add(moduleFactory);
	}

	public IModuleFactory getModuleFactory(String moduleUrl)
	{
		for (IModuleFactory moduleFactory : this.moduleFactories)
		{
			if (moduleFactory.supports(moduleUrl))
			{
				return moduleFactory;
			}
		}

		throw new RuntimeException("no module factory found for module url '" + moduleUrl + "'");
	}

	public boolean supports(String moduleUrl)
	{
		return getModuleFactory(moduleUrl) != null;
	}
}
