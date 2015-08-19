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
package io.pelle.mango.client.gwt.modules.dictionary.container;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.NavTabs;
import org.gwtbootstrap3.client.ui.TabContent;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.html.ClearFix;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.base.modules.dictionary.container.ITab;
import io.pelle.mango.client.base.modules.dictionary.container.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.gwt.ColumnLayoutStrategy;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.container.TabFolder;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtTabFolder extends Div implements IContainer<Panel>, IUpdateListener {

	private TabFolder tabfolder;

	private Map<ITab, TabPane> tabMappings = new HashMap<>();

	private Map<ITab, TabListItem> tabListItemMappings = new HashMap<>();

	public GwtTabFolder(TabFolder tabfolder, ColumnLayoutStrategy columnLayoutStrategy) {

		this.tabfolder = tabfolder;
		setWidth("100%");

		NavTabs navTabs = new NavTabs();

		TabContent tabContent = new TabContent();

		int tabIndex = 0;
		for (ITab tab : tabfolder.getTabs()) {

			TabListItem tabListItem = new TabListItem();
			tabListItem.setTitle(tab.getName());
			tabListItem.setText(tab.getName());
			tabListItem.setDataTarget("#" + tab.getName());
			if (tabIndex == 0) {
				tabListItem.setActive(true);
			}
			navTabs.add(tabListItem);

			TabPane tabPane = new TabPane();
			tabPane.setId(tab.getName());
			tabPane.setTitle(tab.getName());
			if (tabIndex == 0) {
				tabPane.setActive(true);
			}
			columnLayoutStrategy.createLayout(tabPane, (BaseContainerElement<?>) tab);

			tabMappings.put(tab, tabPane);
			tabContent.add(tabPane);
			tabIndex++;
		}

		add(navTabs);
		add(tabContent);

		add(new ClearFix());
		onUpdate();
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return this;
	}

	@Override
	public void onUpdate() {

		if (tabMappings.containsKey(tabfolder.getActiveTab())) {
			tabMappings.get(tabfolder.getActiveTab()).setActive(true);
			// tabListItemMappings.get(tabfolder.getActiveTab()).setActive(true);
		}

	}

}
