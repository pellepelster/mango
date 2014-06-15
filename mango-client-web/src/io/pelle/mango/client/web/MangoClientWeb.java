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
package io.pelle.mango.client.web;

import io.pelle.mango.client.IMangoGwtRemoteServiceLocator;
import io.pelle.mango.client.MangoClientConfiguration;
import io.pelle.mango.client.MangoGwtRemoteServiceLocator;
import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.web.module.ModuleFactoryRegistry;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModuleFactory;
import io.pelle.mango.client.web.modules.hierarchical.HierarchicalTreeModuleFactory;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModuleFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * TODO pelle insert type comment
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class MangoClientWeb implements EntryPoint {

	private ILayoutFactory<?, ?> layoutFactory;

	/** Shared instance of {@link MangoClientWeb} */
	private static MangoClientWeb instance;

	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

	/**
	 * Returns the instance
	 * 
	 * @return
	 */
	public static MangoClientWeb getInstance() {
		if (instance == null) {
			instance = new MangoClientWeb();
			init();
		}

		return instance;
	}

	public final static MangoMessages MESSAGES = ((MangoMessages) GWT.create(MangoMessages.class));;

	public final static MangoResources RESOURCES = ((MangoResources) GWT.create(MangoResources.class));;

	private IMangoGwtRemoteServiceLocator myAdminGWTRemoteServiceLocator;

	public ILayoutFactory<?, ?> getLayoutFactory() {
		return this.layoutFactory;
	}

	public IMangoGwtRemoteServiceLocator getRemoteServiceLocator() {

		if (this.myAdminGWTRemoteServiceLocator != null) {
			return this.myAdminGWTRemoteServiceLocator;
		}

		return MangoGwtRemoteServiceLocator.getInstance();
	}

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		instance = this;
		init();
	}

	/**
	 * Registers all MyAdmin modules
	 */
	public static void init() {
		ModuleFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new ModuleNavigationModuleFactory());
		ModuleFactoryRegistry.getInstance().addModuleFactory(new HierarchicalTreeModuleFactory());

		MangoClientConfiguration.registerAll();
	}

	public void setLayoutFactory(ILayoutFactory<?, ?> layoutFactory) {
		this.layoutFactory = layoutFactory;
	}

	public void setMyAdminGWTRemoteServiceLocator(IMangoGwtRemoteServiceLocator myAdminGWTRemoteServiceLocator) {
		this.myAdminGWTRemoteServiceLocator = myAdminGWTRemoteServiceLocator;
	}

}
