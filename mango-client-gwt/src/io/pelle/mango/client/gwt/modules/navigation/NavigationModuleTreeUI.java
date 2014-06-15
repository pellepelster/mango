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

import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class NavigationModuleTreeUI extends BaseGwtModuleUI<ModuleNavigationModule> {

	private static class NavigationTreeModel implements TreeViewModel {

		private final List<NavigationTreeElement> navigationTree = new ArrayList<NavigationTreeElement>();

		private final SingleSelectionModel<NavigationTreeElement> selectionModel = new SingleSelectionModel<NavigationTreeElement>();
		private ListDataProvider<NavigationTreeElement> rootDataProvider;

		public NavigationTreeModel() {
			selectionModel.addSelectionChangeHandler(new Handler() {
				/** {@inheritDoc} */
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					if (selectionModel.getSelectedObject() != null && selectionModel.getSelectedObject().hasModuleLocator()) {
						ModuleHandler.getInstance().startUIModule(selectionModel.getSelectedObject().getModuleLocator());
					}

				}
			});
		}

		private Cell<NavigationTreeElement> getCell() {

			Cell<NavigationTreeElement> cell = new AbstractCell<NavigationTreeElement>() {
				/** {@inheritDoc} */
				@Override
				public void render(Context context, NavigationTreeElement value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getLabel());
					}
				}
			};

			return cell;
		}

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		@Override
		public <T> NodeInfo<?> getNodeInfo(T value) {
			if (value == null) {
				rootDataProvider = new ListDataProvider<NavigationTreeElement>(navigationTree);
				return new DefaultNodeInfo<NavigationTreeElement>(rootDataProvider, getCell(), selectionModel, null);

			} else if (value instanceof NavigationTreeElement) {
				ListDataProvider<NavigationTreeElement> dataProvider = new ListDataProvider<NavigationTreeElement>(((NavigationTreeElement) value).getChildren());
				return new DefaultNodeInfo<NavigationTreeElement>(dataProvider, getCell(), selectionModel, null);
			}

			return null;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeaf(Object value) {
			return (value instanceof NavigationTreeElement && ((NavigationTreeElement) value).getChildren().isEmpty());
		}

		public void setNavigationTreeModel(List<NavigationTreeElement> navigationTreeRootElements) {
			if (rootDataProvider != null) {
				rootDataProvider.getList().clear();

				for (NavigationTreeElement navigationTreeRootElement : navigationTreeRootElements) {
					rootDataProvider.getList().addAll(navigationTreeRootElement.getChildren());
				}
			}
		}

	}

	private final VerticalPanel verticalPanel;

	/**
	 * @param module
	 */
	public NavigationModuleTreeUI(ModuleNavigationModule module) {
		super(module, ModuleNavigationModule.NAVIGATION_UI_MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.ensureDebugId(ModuleNavigationModule.NAVIGATION_UI_MODULE_ID);

		final NavigationTreeModel navigationTreeModel = new NavigationTreeModel();
		CellTree cellTree = new CellTree(navigationTreeModel, null);

		verticalPanel.add(cellTree);

		navigationTreeModel.setNavigationTreeModel(module.getNavigationTreeRoots());
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
