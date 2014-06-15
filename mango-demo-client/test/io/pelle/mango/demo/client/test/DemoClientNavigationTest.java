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
package io.pelle.mango.demo.client.test;

import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;
import io.pelle.mango.client.web.test.MangoClientWebTest;
import io.pelle.mango.client.web.test.modules.navigation.NavigationModuleTestUI;
import io.pelle.mango.client.web.test.modules.navigation.NavigationTreeTestElement;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

public class DemoClientNavigationTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "io.pelle.mango.demo.DemoClientTest";
	}

	private class NavigationTreeElementTest extends BaseErrorAsyncCallback<List<NavigationTreeTestElement>> {
		@Override
		public void onSuccess(List<NavigationTreeTestElement> result) {
			assertFalse(result.isEmpty());
			finishTest();
		}
	}

	private class NavigationModuleTest extends BaseErrorAsyncCallback<NavigationModuleTestUI> {
		@Override
		public void onSuccess(NavigationModuleTestUI result) {
			result.getRootElements(new NavigationTreeElementTest());
		}
	}

	@Test
	@Ignore
	public void testNavigationTree() {
		MangoClientWebTest.getInstance();
		MangoDemoClientConfiguration.registerAll();
		delayTestFinish(10000);

		MangoClientWebTest.getInstance().startUIModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, NavigationModuleTestUI.class, Direction.WEST.toString(), new NavigationModuleTest());

	}

}
