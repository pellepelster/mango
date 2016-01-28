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
package io.pelle.mango.client.gwt.modules.hierarchical;

import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.modules.hierarchical.HierarchicalTreeModule;

import com.google.common.base.Optional;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class HierarchicalTreeModuleUI extends BaseGwtModuleUI<HierarchicalTreeModule> {

	private ScrollPanel scrollPanel = new ScrollPanel();

	public HierarchicalTreeModuleUI(HierarchicalTreeModule module) {
		super(module, HierarchicalTreeModule.MODULE_ID);

		scrollPanel.setHeight(Window.getClientHeight() / 2 + "px");
		scrollPanel.setWidth("100%");

		HierarchicalTree hierarchicalTree = new HierarchicalTree(module.getHierarchicalConfiguration(), Optional.fromNullable(getModule().getShowAddNodes()).or(true), getModule().getNodeActivatedHandler());
		hierarchicalTree.setWidth("100%");
		scrollPanel.add(hierarchicalTree);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return scrollPanel;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return getModule().getHierarchicalConfiguration().getTitle();
	}

}