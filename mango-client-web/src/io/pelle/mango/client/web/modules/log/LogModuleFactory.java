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
package io.pelle.mango.client.web.modules.log;

import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.web.module.BaseModuleFactory;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LogModuleFactory extends BaseModuleFactory
{

	/** {@inheritDoc} */
	@Override
	public void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new LogModule(moduleUrl, moduleCallback, parameters);
	}

	@Override
	public boolean supports(String moduleUrl)
	{
		return ModuleUtils.urlContainsModuleId(moduleUrl, LogModule.MODULE_ID);
	}

}
