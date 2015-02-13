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

import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.editor.DictionaryEditorModuleUIFactory;
import io.pelle.mango.client.gwt.modules.dictionary.search.DictionarySearchModuleUIFactory;
import io.pelle.mango.client.gwt.modules.hierarchical.HierarchicalTreeModuleUIFactory;
import io.pelle.mango.client.gwt.modules.log.LogModuleUIFactory;
import io.pelle.mango.client.gwt.modules.navigation.NavigationModuleUIFactory;
import io.pelle.mango.client.gwt.modules.property.PropertyModuleUIFactory;
import io.pelle.mango.client.gwt.modules.webhook.WebhookModuleUIFactory;
import io.pelle.mango.client.gwt.utils.HtmlWithHelp;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleUIFactoryRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@link ILayoutFactory} implementation for GWT
 * 
 * @author pelle
 * 
 */
public class GWTLayoutFactory implements ILayoutFactory<Panel, Widget> {
	final static Logger LOG = Logger.getLogger("GWTLayoutFactory");

	private class PanelLayoutInfo {

		private final Widget widget;

		private List<IModuleUI<Panel, ?>> moduleUIs = new ArrayList<IModuleUI<Panel, ?>>();

		public PanelLayoutInfo(int size, boolean supportsMultipleChildren, Widget widget) {
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
			if (widget instanceof StackLayoutPanel) {

				StackLayoutPanel stackLayoutPanel = (StackLayoutPanel) widget;
				Panel panel = moduleUI.getContainer();
				panel.setWidth("100%");

				Widget title = new HtmlWithHelp(MangoClientWeb.MESSAGES.panelTitle(moduleUI.getTitle()), moduleUI.getModule().getHelpText());
				title.getElement().getStyle().setProperty("transform", "translateY(40%");
				title.setStylePrimaryName(GwtStyles.H4_CLASS);

				int beforeIndex = 0;

				List<IModuleUI<Panel, ?>> moduleUIs = stackLayoutPanelMappings.get(stackLayoutPanel);
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

				stackLayoutPanel.insert(panel, new SimplePanel(title), 3, beforeIndex);
				stackLayoutPanel.showWidget(0);

			} else if (widget instanceof Panel) {
				Panel panel = (Panel) widget;
				removeAllChildren(panel);
				panel.add(moduleUI.getContainer());
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
			if (widget instanceof StackLayoutPanel) {
				StackLayoutPanel stackLayoutPanel = (StackLayoutPanel) widget;
				stackLayoutPanel.remove(moduleUI.getContainer());
			} else if (widget instanceof Panel) {
				Panel panel = (Panel) widget;
				panel.remove(moduleUI.getContainer());
			} else {
				throw new RuntimeException("unsupported panel type");
			}
		}
	}

	private final Map<Direction, PanelLayoutInfo> panels = new HashMap<DockLayoutPanel.Direction, PanelLayoutInfo>();

	private final DockLayoutPanel rootPanel;

	/**
	 * Constructor for {@link GWTLayoutFactory}
	 * 
	 * @param unit
	 */
	public GWTLayoutFactory(Unit unit) {
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new NavigationModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new HierarchicalTreeModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new LogModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new WebhookModuleUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new PropertyModuleUIFactory());

		rootPanel = new DockLayoutPanel(Unit.PCT);
		rootPanel.setWidth("100%");
		rootPanel.setHeight("100%");

		initializePanelLayout(Direction.WEST, 15, true);
		initializePanelLayout(Direction.CENTER, 85, false);

		RootLayoutPanel.get().add(rootPanel);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {

				for (Map.Entry<Direction, PanelLayoutInfo> panelEntry : panels.entrySet()) {
					for (IModuleUI<Panel, ?> moduleUI : panelEntry.getValue().getModuleUIs()) {
						moduleUI.onResize();
					}
				}

			}
		});
	}

	private Map<StackLayoutPanel, List<IModuleUI<Panel, ?>>> stackLayoutPanelMappings = new HashMap<StackLayoutPanel, List<IModuleUI<Panel, ?>>>();

	private Direction getDirection(String location) {
		try {
			return Direction.valueOf(location);
		} catch (Exception e) {
			return Direction.CENTER;
		}
	}

	private void initializePanelLayout(Direction direction, int size, boolean supportsMultipleChildren) {
		Widget panel = null;

		if (supportsMultipleChildren) {
			StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Unit.EM);
			stackLayoutPanelMappings.put(stackLayoutPanel, new ArrayList<IModuleUI<Panel, ?>>());
			stackLayoutPanel.setHeight("100%");
			panel = stackLayoutPanel;
		} else {
			panel = new HorizontalPanel();
		}

		panel.setHeight("100%");
		panel.setWidth("100%");

		panel.setStyleName("docklayoutpanel-" + direction.toString().toLowerCase(), true);

		panels.put(direction, new PanelLayoutInfo(size, supportsMultipleChildren, panel));

		switch (direction) {
		case CENTER:
			rootPanel.add(panel);
			break;
		case EAST:
			rootPanel.addEast(panel, size);
			break;
		case NORTH:
			rootPanel.addNorth(panel, size);
			break;
		case SOUTH:
			rootPanel.addSouth(panel, size);
			break;
		case WEST:
			rootPanel.addWest(panel, size);
			break;
		case LINE_END:
			rootPanel.addLineEnd(panel, size);
			break;
		case LINE_START:
			rootPanel.addLineStart(panel, size);
			break;
		default:
			throw new RuntimeException("unsupported direction '" + direction.toString() + "'");
		}
	}

	private void showModuleUIInternal(IModuleUI<Panel, ?> moduleUI, DockLayoutPanel.Direction direction) {
		if (panels.containsKey(direction)) {
			PanelLayoutInfo panelLayoutInfo = panels.get(direction);
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
		for (Map.Entry<Direction, PanelLayoutInfo> panelEntry : panels.entrySet()) {
			if (panelEntry.getValue().conainsModuleUI(moduleUI)) {
				return panelEntry.getKey();
			}
		}

		throw new RuntimeException("no direction found for module ui '" + moduleUI + "'");

	}

	@Override
	public void closeModuleUI(IModuleUI<Panel, ?> moduleUI) {

		LOG.info("closing module ui '" + moduleUI + "'");

		Direction direction = getDirection(moduleUI);

		if (panels.containsKey(direction)) {
			PanelLayoutInfo panelLayoutInfo = panels.get(direction);
			panelLayoutInfo.removeModuleUI(moduleUI);
		} else {
			throw new RuntimeException("no panel layout info found for direction '" + direction + "'");
		}
	}

}
