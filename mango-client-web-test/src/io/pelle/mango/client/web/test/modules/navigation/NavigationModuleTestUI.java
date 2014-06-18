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
package io.pelle.mango.client.web.test.modules.navigation;

import io.pelle.mango.client.base.layout.BaseModuleUI;
import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NavigationModuleTestUI extends BaseModuleUI<Object, ModuleNavigationModule> {

	private List<NavigationTreeTestElement> navigationTreeRoots;

	public NavigationModuleTestUI(ModuleNavigationModule module) {
		super(module, ModuleNavigationModule.NAVIGATION_UI_MODULE_ID);
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs() {
		return false;
	}

	@Override
	public String getTitle() {
		return null;
	}

	public void getRootElements(final AsyncCallback<List<NavigationTreeTestElement>> asyncCallback) {
		NavigationModuleTestUI.this.navigationTreeRoots = new ArrayList<NavigationTreeTestElement>(Collections2.transform(this.getModule().getNavigationTreeRoots(), new Function<NavigationTreeElement, NavigationTreeTestElement>() {

			@Override
			public NavigationTreeTestElement apply(NavigationTreeElement input) {
				return new NavigationTreeTestElement(input);
			}
		}));

		asyncCallback.onSuccess(NavigationModuleTestUI.this.navigationTreeRoots);
	}

	@Override
	public Object getContainer() {
		return null;
	}

}
