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
package io.pelle.mango.client.gwt.modules.log;

import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.modules.log.LogModule;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class LogModuleUI extends BaseGwtModuleUI<LogModule> {

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public LogModuleUI(LogModule module) {
		super(module, LogModule.UI_MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.ensureDebugId(ModuleNavigationModule.NAVIGATION_UI_MODULE_ID);

		verticalPanel.add(new HTML("xxx"));
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return verticalPanel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
