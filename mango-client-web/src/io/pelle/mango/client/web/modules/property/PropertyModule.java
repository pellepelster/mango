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
package io.pelle.mango.client.web.modules.property;

import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.core.property.PropertyProvider;
import io.pelle.mango.client.property.BasePropertyModule;
import io.pelle.mango.client.web.MangoClientWeb;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class PropertyModule extends BasePropertyModule {

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public final static String UI_MODULE_ID = MODULE_ID;

	public final static String UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(UI_MODULE_ID);

	public PropertyModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
		getModuleCallback().onSuccess(this);
	}

	public List<IProperty<?>> getProperties() {
		return PropertyProvider.getInstance().getRootCategory().getProperties();
	}

	@Override
	public String getTitle() {
		return MangoClientWeb.MESSAGES.logTitle();
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl));
	}

}
