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
package io.pelle.mango.client.gwt.modules.property;

import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.modules.property.PropertyModule;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class PropertyModuleUI extends BaseGwtModuleUI<PropertyModule> {

	final static Logger LOG = Logger.getLogger("PropertyModuleUI");

	private HTML title;

	private VerticalPanel panel;

	/**
	 * @param module
	 */
	public PropertyModuleUI(PropertyModule module) {
		super(module, PropertyModule.UI_MODULE_ID);

	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
