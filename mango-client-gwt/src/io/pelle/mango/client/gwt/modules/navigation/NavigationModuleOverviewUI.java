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
package io.pelle.mango.client.gwt.modules.navigation;

import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;

import java.util.List;

import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleOverviewUI extends BaseGwtModuleUI<ModuleNavigationModule> {

	private static final String NAVIGATION_OVERVIEW_STYLE = "navigationOverview";

	private static final String NAVIGATION_OVERVIEW_PANEL_WRAPPER_STYLE = "navigationOverviewPanelWrapper";

	private static final String NAVIGATION_OVERVIEW_PANEL_STYLE = "navigationOverviewPanel";

	private static final String NAVIGATION_OVERVIEW_TEXT_STYLE = "navigationOverviewText";

	private static final String PANEL_SIZE = "128px";

	private final Grid grid;

	/**
	 * @param module
	 */
	public NavigationModuleOverviewUI(ModuleNavigationModule module) {
		super(module, ModuleNavigationModule.NAVIGATION_OVERVIEW_UI_MODULE_ID);

		List<NavigationTreeElement> children = module.getChildrenForNavigationElement(module.getNavigationTreeElementName());

		int columns = (int) Math.ceil((double) children.size() / 2);
		int rows = columns;

		if (columns == 1) {
			columns = children.size();
		}

		grid = new Grid(rows, columns);
		grid.addStyleName(NAVIGATION_OVERVIEW_STYLE);
		grid.setWidth("100%");
		grid.setHeight("100%");
		grid.ensureDebugId(ModuleNavigationModule.MODULE_ID);

		int column = 0;
		int row = 0;

		for (final NavigationTreeElement navigationTreeElement : children) {

			if (column == columns) {
				column = 0;
				row++;
			}

			FlowPanel flowPanelWrapper = new FlowPanel();
			flowPanelWrapper.setHeight(PANEL_SIZE);
			flowPanelWrapper.setWidth(PANEL_SIZE);
			flowPanelWrapper.addStyleName(NAVIGATION_OVERVIEW_PANEL_WRAPPER_STYLE);

			FlowPanel overviewPanel = new FlowPanel();
			overviewPanel.setHeight(PANEL_SIZE);
			overviewPanel.setWidth(PANEL_SIZE);
			overviewPanel.addStyleName(NAVIGATION_OVERVIEW_PANEL_STYLE);

			flowPanelWrapper.add(overviewPanel);

			ImageResource imageResource = ModuleNavigationModule.RESOURCES.unspecified();
			if (navigationTreeElement.getImageResource() != null) {
				imageResource = navigationTreeElement.getImageResource();
			}

			Image image = new Image(imageResource);
			overviewPanel.add(image);

			overviewPanel.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ModuleHandler.getInstance().startUIModule(navigationTreeElement.getModuleLocator());

				}
			}, ClickEvent.getType());

			HTML text = new HTML(navigationTreeElement.getLabel());
			text.addStyleName(NAVIGATION_OVERVIEW_TEXT_STYLE);

			overviewPanel.add(text);
			grid.setWidget(row, column, flowPanelWrapper);

			column++;

		}

	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return grid;
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return super.isInstanceOf(moduleUrl) && Objects.equal(getModule().getNavigationTreeElementName(), ModuleUtils.getUrlParameter(moduleUrl, ModuleNavigationModule.NAVIGATIONTREEELEMENTNAME_PARAMETER_ID));
	}

	@Override
	public String getTitle() {
		return MangoClientWeb.MESSAGES.navigationTitle();
	}

}
