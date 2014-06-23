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

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.module.BaseModule;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.util.BaseAsyncCallback;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Handler for module loading
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
@SuppressWarnings("rawtypes")
public final class ModuleHandler {
	
	final static Logger LOG = Logger.getLogger("ModuleHandler");

	public static EventBus MODULE_EVENT_BUS = GWT.create(SimpleEventBus.class);

	private int moduleCounter = 0;

	private static ModuleHandler instance;
	
	public static String DEFAULT_LOCATION  = "default";

	private final LinkedHashMap<String, Stack<IModuleUI>> currentModules = new LinkedHashMap<String, Stack<IModuleUI>>();

	public static ModuleHandler getInstance() {
		if (instance == null) {
			instance = new ModuleHandler();
		}

		return instance;
	}

	private ModuleHandler() {
	}

	private String getLocation(String location)
	{
		if (location == null || location.trim().isEmpty())
		{
			return DEFAULT_LOCATION;
		}
		else
		{
			return location;
		}
	}
	
	private Stack<IModuleUI> getModuleStack(String location) {
		if (!this.currentModules.containsKey(location)) {
			this.currentModules.put(location, new Stack<IModuleUI>());
		}

		return this.currentModules.get(location);
	}

	public void startUIModule(final String moduleUrl, String location) {
		startUIModule(moduleUrl, location, new HashMap<String, Object>(), Optional.<AsyncCallback<IModuleUI>>absent());
	}

	public void startUIModule(final String moduleUrl) {
		startUIModule(moduleUrl, null, new HashMap<String, Object>(), Optional.<AsyncCallback<IModuleUI>>absent());
	}

	public void startUIModule(final String moduleUrl, Map<String, Object> parameters) {
		startUIModule(moduleUrl, null, parameters, Optional.<AsyncCallback<IModuleUI>>absent());
	}

	private class MouldeUrlPredicate implements Predicate<IModuleUI> {
		private String moduleUrl;

		public MouldeUrlPredicate(String moduleUrl) {
			super();
			this.moduleUrl = moduleUrl;
		}

		@Override
		public boolean apply(IModuleUI input) {
			return input.isInstanceOf(this.moduleUrl);
		}

	};

	public void startUIModule(final String moduleUrl, final String location1, final Map<String, Object> parameters, final Optional<AsyncCallback<IModuleUI>> callback) {
		
		final String location = getLocation(location1);
		
		LOG.info("starting ui module for url '" + moduleUrl + "'");

		Optional<IModuleUI> moduleUI = Iterables.tryFind(getModuleStack(location), new MouldeUrlPredicate(moduleUrl));

		if (moduleUI.isPresent()) {
			LOG.info("ui module for url '" + moduleUrl + "' already started (" + moduleUI.get().toString() + ")");
			moduleUI.get().updateUrl(moduleUrl);
			
			if (callback.isPresent())
			{
				callback.get().onSuccess(moduleUI.get());
			}
				
			MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI.get(), location);
		} else {
			if (ModuleUIFactoryRegistry.getInstance().supports(moduleUrl)) {
				ModuleUIFactoryRegistry.getInstance().getModuleFactory(moduleUrl).getNewInstance(moduleUrl, new BaseAsyncCallback<IModuleUI, IModuleUI>(callback) {

					@Override
					public void onSuccess(IModuleUI moduleUI) {
						ModuleHandler.this.getModuleStack(location).add(moduleUI);
						MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI, location);

						if (callback.isPresent())
						{
							callback.get().onSuccess(moduleUI);
						}
					}
				}, parameters, peekCurrentModule(location));
			} else {
				throw new RuntimeException("unsupported module url '" + moduleUrl + "'");
			}
		}
	}

	private Optional<IModuleUI> peekCurrentModule(String location) {
		if (getModuleStack(location).isEmpty())
		{
			return Optional.absent();
		}
		else
		{
			return Optional.of(getModuleStack(location).peek());
		}
	}
	
	private Optional<IModuleUI> popCurrentModule(String location) {
		if (getModuleStack(location).isEmpty())
		{
			return Optional.absent();
		}
		else
		{
			return Optional.of(getModuleStack(location).pop());
		}
	}

	public void startModule(final String moduleUrl, Map<String, Object> parameters, final AsyncCallback<IModule> moduleCallback) {
		LOG.info("starting module for url '" + moduleUrl + "'");

		if (ModuleFactoryRegistry.getInstance().supports(moduleUrl)) {
			this.moduleCounter++;
			parameters.put(BaseModule.MODULE_COUNTER_PARAMETER_ID, this.moduleCounter);

			ModuleFactoryRegistry.getInstance().getModuleFactory(moduleUrl).getNewInstance(moduleUrl, moduleCallback, parameters);
		} else {
			moduleCallback.onFailure(new RuntimeException("unsupported module url '" + moduleUrl + "'"));
		}
	}

	private String getModuleLocation(IModuleUI moduleUI) {
		
		for (Map.Entry<String, Stack<IModuleUI>> currentModuleEntry : this.currentModules.entrySet()) {
			if (currentModuleEntry.getValue().contains(moduleUI)) {
				return currentModuleEntry.getKey();
			}

		}

		throw new RuntimeException("module ui '" + moduleUI + "' not found");
	}

	public void closeCurrentAndShow(IModuleUI moduleUI) {
		LOG.info("closing module ui '" + moduleUI.toString() + "'");

		String location = getModuleLocation(moduleUI);

		Optional<IModuleUI> currentModuleUI = popCurrentModule(location);

		if (currentModuleUI.isPresent()) {
			MangoClientWeb.getInstance().getLayoutFactory().closeModuleUI(currentModuleUI.get());
		}

		MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI, location);
	}
}
