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
package io.pelle.mango.client.gwt;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.editor.DictionaryEditorModuleUIFactory;
import io.pelle.mango.client.gwt.modules.dictionary.search.DictionarySearchModuleUIFactory;
import io.pelle.mango.client.gwt.modules.hierarchical.HierarchicalTreeModuleUIFactory;
import io.pelle.mango.client.gwt.modules.log.LogModuleUIFactory;
import io.pelle.mango.client.gwt.modules.navigation.NavigationModuleUIFactory;
import io.pelle.mango.client.gwt.modules.property.PropertyModuleUIFactory;
import io.pelle.mango.client.gwt.modules.webhook.WebhookModuleUIFactory;
import io.pelle.mango.client.web.module.ModuleUIFactoryRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link ILayoutFactory} implementation for GWT
 * 
 * @author pelle
 * 
 */
public class MangoBootstrapLayoutFactory implements ILayoutFactory<Panel, Widget> {

	public static final String ROOT_CONTAINER_CSS_ID = "mango-bootstrap-layout";

	public static final String CONTAINER_CSS_ID = "mango-bootstrap-layout-container";

	public static final String CONTAINER_MULTIPLE_CSS_ID = "mango-bootstrap-layout-multiple-container";

	final static Logger LOG = Logger.getLogger("MangoBootstrapLayoutFactory");

	private class PanelLayoutInfo {

		private final Widget widget;

		private List<IModuleUI<Panel, ?>> moduleUIs = new ArrayList<IModuleUI<Panel, ?>>();

		public PanelLayoutInfo(boolean supportsMultipleChildren, Widget widget) {
			super();
			this.widget = widget;
		}

		public List<IModuleUI<Panel, ?>> getModuleUIs() {
			return moduleUIs;
		}

		public boolean conainsModuleUI(IModuleUI<Panel, ?> moduleUI) {
			return moduleUIs.contains(moduleUI);
		}

		public void addModuleUI(IModuleUI<Panel, ?> moduleUI) {

			Panel moduleContainer = moduleUI.getContainer();
			moduleContainer.addStyleName(GwtStyles.MODULE);

			if (widget instanceof PanelGroup) {

				String containerId = moduleUI.getModule().getModuleId();
				PanelGroup panelGroup = (PanelGroup) widget;
				PanelCollapse panelCollapse = new PanelCollapse();
				panelCollapse.setId(containerId);

				org.gwtbootstrap3.client.ui.Panel containerPanel = new org.gwtbootstrap3.client.ui.Panel(PanelType.DEFAULT);

				// create header
				PanelHeader panelHeader = new PanelHeader();
				Heading heading = new Heading(HeadingSize.H4);
				Anchor anchor = new Anchor();
				anchor.setText(moduleUI.getTitle());
				anchor.setDataTarget("#" + containerId);
				anchor.setDataParent("#" + panelGroup.getId());
				anchor.setDataToggle(Toggle.COLLAPSE);
				heading.add(anchor);
				panelHeader.add(heading);
				containerPanel.add(panelHeader);

				// Widget title = new
				// HtmlWithHelp(MangoClientWeb.MESSAGES.panelTitle(moduleUI.getTitle()),
				// moduleUI.getModule().getHelpText());
				// title.getElement().getStyle().setProperty("transform",
				// "translateY(40%");
				// title.setStylePrimaryName(GwtStyles.H4_CLASS);

				int beforeIndex = 0;

				List<IModuleUI<Panel, ?>> moduleUIs = panelGroupMappings.get(panelGroup);
				moduleUIs.add(moduleUI);

				Collections.sort(moduleUIs, new Comparator<IModuleUI<Panel, ?>>() {
					@Override
					public int compare(IModuleUI<Panel, ?> moduleUI1, IModuleUI<Panel, ?> moduleUI2) {
						Integer order1 = moduleUI1.getOrder();
						Integer order2 = moduleUI2.getOrder();
						return order1.compareTo(order2);
					}
				});
				beforeIndex = moduleUIs.indexOf(moduleUI);

				panelCollapse.add(moduleContainer);
				containerPanel.add(panelCollapse);

				panelGroup.insert(containerPanel, beforeIndex);

				panelCollapse.setIn(true);

			} else if (widget instanceof Panel) {
				Panel panel = (Panel) widget;
				removeAllChildren(panel);
				panel.add(moduleContainer);
			} else {
				throw new RuntimeException("unsupported panel type");
			}

			moduleUIs.add(moduleUI);

		}

		private void removeAllChildren(Panel panel) {
			Iterator<Widget> childrenIterator = panel.iterator();

			while (childrenIterator.hasNext()) {
				panel.remove(childrenIterator.next());
			}
		}

		public void removeModuleUI(IModuleUI<Panel, ?> moduleUI) {
			if (widget instanceof PanelGroup) {
				PanelGroup panelGroup = (PanelGroup) widget;
				panelGroup.remove(moduleUI.getContainer());
			} else if (widget instanceof Panel) {
				Panel panel = (Panel) widget;
				panel.remove(moduleUI.getContainer());
			} else {
				throw new RuntimeException("unsupported panel type");
			}
		}
	}

	private final Map<Direction, PanelLayoutInfo> containers = new HashMap<DockLayoutPanel.Direction, PanelLayoutInfo>();

	private final Container rootContainer;

	/**
	 * Constructor for {@link MangoBootstrapLayoutFactory}
	 * 
	 * @param unit
	 */
	public MangoBootstrapLayoutFactory() {

		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new NavigationModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new HierarchicalTreeModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new LogModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new WebhookModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new PropertyModuleUIFactory());

		rootContainer = new Container();
		rootContainer.setId(ROOT_CONTAINER_CSS_ID);
		rootContainer.setFluid(true);

		row = new Row();
		rootContainer.add(row);

		initializePanelLayout(Direction.WEST, true);
		initializePanelLayout(Direction.CENTER, false);

		RootLayoutPanel.get().add(rootContainer);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {

				for (Map.Entry<Direction, PanelLayoutInfo> panelEntry : containers.entrySet()) {
					for (IModuleUI<Panel, ?> moduleUI : panelEntry.getValue().getModuleUIs()) {
						moduleUI.onResize();
					}
				}

			}
		});
	}

	private Map<PanelGroup, List<IModuleUI<Panel, ?>>> panelGroupMappings = new HashMap<PanelGroup, List<IModuleUI<Panel, ?>>>();

	private Row row;

	private Direction getDirection(String location) {
		try {
			return Direction.valueOf(location);
		} catch (Exception e) {
			return Direction.CENTER;
		}
	}

	private void initializePanelLayout(Direction direction, boolean supportsMultipleChildren) {

		Panel container = null;

		switch (direction) {
		case CENTER:
			container = new Column(ColumnSize.MD_10);
			break;
		case WEST:
			container = new Column(ColumnSize.MD_2);
			break;
		default:
			throw new RuntimeException("unsupported direction '" + direction.toString() + "'");
		}

		container.getElement().setId(CONTAINER_CSS_ID + "-" + direction.toString().toLowerCase());
		container.setStyleName(CONTAINER_CSS_ID + "-" + direction.toString().toLowerCase(), true);
		container.setStyleName(CONTAINER_CSS_ID, true);

		row.add(container);

		if (supportsMultipleChildren) {
			PanelGroup panelGroup = new PanelGroup();
			panelGroup.setWidth("100%");
			panelGroup.setId(UUID.uuid());

			panelGroup.getElement().setId(CONTAINER_CSS_ID + "-" + direction.toString().toLowerCase());
			panelGroup.setStyleName(CONTAINER_MULTIPLE_CSS_ID + "-" + direction.toString().toLowerCase(), true);
			panelGroup.setStyleName(CONTAINER_MULTIPLE_CSS_ID, true);
			panelGroup.setStyleName(CONTAINER_CSS_ID + "-" + direction.toString().toLowerCase(), true);
			panelGroup.setStyleName(CONTAINER_CSS_ID, true);

			panelGroupMappings.put(panelGroup, new ArrayList<IModuleUI<Panel, ?>>());
			container.add(panelGroup);
			container = panelGroup;
		}

		containers.put(direction, new PanelLayoutInfo(supportsMultipleChildren, container));

	}

	private void showModuleUIInternal(IModuleUI<Panel, ?> moduleUI, DockLayoutPanel.Direction direction) {

		if (containers.containsKey(direction)) {
			PanelLayoutInfo panelLayoutInfo = containers.get(direction);
			panelLayoutInfo.addModuleUI(moduleUI);
		} else {
			throw new RuntimeException("no panel layout info found for direction '" + direction + "'");
		}

	}

	/** {@inheritDoc} */
	@Override
	public void showModuleUI(IModuleUI<Panel, ?> moduleUI, String location) {
		LOG.info("showing module ui '" + moduleUI + "' for location '" + location + "'");

		DockLayoutPanel.Direction direction = getDirection(location);

		showModuleUIInternal(moduleUI, direction);
	}

	private Direction getDirection(IModuleUI<Panel, ?> moduleUI) {
		for (Map.Entry<Direction, PanelLayoutInfo> panelEntry : containers.entrySet()) {
			if (panelEntry.getValue().conainsModuleUI(moduleUI)) {
				return panelEntry.getKey();
			}
		}

		throw new RuntimeException("no direction found for module ui '" + moduleUI + "'");

	}

	@Override
	public void closeModuleUI(IModuleUI<Panel, ?> moduleUI) {

		LOG.info("closing module ui '" + moduleUI.getModule().getModuleUrl() + "'");

		Direction direction = getDirection(moduleUI);

		if (containers.containsKey(direction)) {
			PanelLayoutInfo panelLayoutInfo = containers.get(direction);
			panelLayoutInfo.removeModuleUI(moduleUI);
		} else {
			throw new RuntimeException("no panel layout info found for direction '" + direction + "'");
		}
	}

}
