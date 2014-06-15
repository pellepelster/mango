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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
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
public final class ModuleHandler {
	
	final static Logger LOG = Logger.getLogger("ModuleHandler");

	public static EventBus MODULE_EVENT_BUS = GWT.create(SimpleEventBus.class);

	private int moduleCounter = 0;

	private static ModuleHandler instance;

	private final LinkedHashMap<String, List<IModuleUI>> currentModules = new LinkedHashMap<String, List<IModuleUI>>();

	public static ModuleHandler getInstance() {
		if (instance == null) {
			instance = new ModuleHandler();
		}

		return instance;
	}

	private ModuleHandler() {
	}

	private List<IModuleUI> getModuleList(String location) {
		if (!this.currentModules.containsKey(location)) {
			this.currentModules.put(location, new ArrayList<IModuleUI>());
		}

		return this.currentModules.get(location);
	}

	public void startUIModule(final String moduleUrl, String location) {
		startUIModule(moduleUrl, location, new HashMap<String, Object>(), null);
	}

	public void startUIModule(final String moduleUrl) {
		startUIModule(moduleUrl, null, new HashMap<String, Object>(), null);
	}

	public void startUIModule(final String moduleUrl, Map<String, Object> parameters) {
		startUIModule(moduleUrl, null, parameters, null);
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

	public void startUIModule(final String moduleUrl, final String location, final Map<String, Object> parameters, AsyncCallback<IModuleUI> callback) {
		LOG.info("starting ui module for url '" + moduleUrl + "'");

		Optional<IModuleUI> moduleUI = Iterables.tryFind(getModuleList(location), new MouldeUrlPredicate(moduleUrl));

		if (moduleUI.isPresent()) {
			LOG.info("ui module for url '" + moduleUrl + "' already started (" + moduleUI.get().toString() + ")");
			moduleUI.get().updateUrl(moduleUrl);

			MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI.get(), location);
		} else {
			if (ModuleUIFactoryRegistry.getInstance().supports(moduleUrl)) {
				ModuleUIFactoryRegistry.getInstance().getModuleFactory(moduleUrl).getNewInstance(moduleUrl, new BaseAsyncCallback<IModuleUI, IModuleUI>(callback) {

					@Override
					public void onSuccess(IModuleUI moduleUI) {
						ModuleHandler.this.getModuleList(location).add(moduleUI);
						MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI, location);

						callParentCallbacks(moduleUI);
					}
				}, parameters, getCurrentModule(location));
			} else {
				throw new RuntimeException("unsupported module url '" + moduleUrl + "'");
			}
		}
	}

	private Optional<IModuleUI> getCurrentModule(String location) {
		return Iterables.tryFind(getModuleList(location), Predicates.alwaysTrue());
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
		for (Map.Entry<String, List<IModuleUI>> currentModuleEntry : this.currentModules.entrySet()) {
			if (currentModuleEntry.getValue().contains(moduleUI)) {
				return currentModuleEntry.getKey();
			}

		}

		throw new RuntimeException("module ui '" + moduleUI + "' not found");
	}

	public void closeCurrentAndShow(IModuleUI moduleUI) {
		LOG.info("closing module ui '" + moduleUI.toString() + "'");

		String location = getModuleLocation(moduleUI);

		Optional<IModuleUI> currentModuleUI = getCurrentModule(location);

		if (currentModuleUI.isPresent()) {
			MangoClientWeb.getInstance().getLayoutFactory().closeModuleUI(currentModuleUI.get());
		}

		MangoClientWeb.getInstance().getLayoutFactory().showModuleUI(moduleUI, location);
	}
}
