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

import io.pelle.mango.client.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.hierarchical.HierarchicalNodeInfo.HierarchicalNodeCallback;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.events.VOEventHandler;
import io.pelle.mango.client.web.modules.dictionary.events.VOSavedEvent;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class HierarchicalTreeModel implements TreeViewModel {

	private Map<String, HierarchicalDataProvider> dataProviders = new HashMap<String, HierarchicalDataProvider>();

	private final SingleSelectionModel<DictionaryHierarchicalNodeVO> selectionModel = new SingleSelectionModel<DictionaryHierarchicalNodeVO>();

	private HierarchicalConfigurationVO hierarchicalConfiguration;

	private final Cell<DictionaryHierarchicalNodeVO> hierarchicalCell = new HierarchicalCell();

	private final CellList<DictionaryHierarchicalNodeVO> cellList = new CellList<DictionaryHierarchicalNodeVO>(hierarchicalCell);

	private final static String ROOT_DATAPROVIDER_KEY = "root";

	public static final String HIERARCHICAL_ADD_NODE = "hierarchicalAddNode";

	private final boolean showAddnodes;

	private void refreshParentDataProvider(final IHierarchicalVO hierarchicalVO) {
		for (HierarchicalDataProvider dataProvider : dataProviders.values()) {
			boolean doUpdate = Iterables.any(dataProvider.getItems(), new Predicate<DictionaryHierarchicalNodeVO>() {
				@Override
				public boolean apply(DictionaryHierarchicalNodeVO hierarchicalNodeVO) {
					return hierarchicalVO.getParentClassName().equals(hierarchicalNodeVO.getVoClassName()) && hierarchicalVO.getParentId().equals(hierarchicalNodeVO.getVoId());
				}
			});

			if (doUpdate) {
				dataProvider.onRangeChanged(cellList);
			}
		}
	}

	public HierarchicalTreeModel(HierarchicalConfigurationVO hierarchicalConfiguration, boolean showAddnodes, final SimpleCallback<DictionaryHierarchicalNodeVO> nodeSelectionHandler) {
		this.hierarchicalConfiguration = hierarchicalConfiguration;
		this.showAddnodes = showAddnodes;

		MangoClientWeb.EVENT_BUS.addHandler(VOSavedEvent.TYPE, new VOEventHandler() {

			@Override
			public void onVOEvent(IBaseVO baseVO) {
				if (baseVO instanceof IHierarchicalVO) {
					IHierarchicalVO hierarchicalVO = (IHierarchicalVO) baseVO;

					if (dataProviders.containsKey(getKey(hierarchicalVO.getParent()))) {
						dataProviders.get(getKey(hierarchicalVO.getParent())).onRangeChanged(cellList);
					} else {
						refreshParentDataProvider(hierarchicalVO);
					}
				}

			}
		});

		selectionModel.addSelectionChangeHandler(new Handler() {
			/** {@inheritDoc} */
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (nodeSelectionHandler != null && selectionModel.getSelectedObject() != null) {
					DictionaryHierarchicalNodeVO hierarchicalNodeVO = selectionModel.getSelectedObject();
					nodeSelectionHandler.onCallback(hierarchicalNodeVO);
				}
			}
		});

	}

	private String getKey(DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO) {
		if (dictionaryHierarchicalNodeVO == null) {
			return ROOT_DATAPROVIDER_KEY;
		} else {
			return dictionaryHierarchicalNodeVO.getVoClassName() + "#" + dictionaryHierarchicalNodeVO.getVoId().toString();
		}
	}

	private String getKey(IHierarchicalVO hierarchicalVO) {
		if (hierarchicalVO == null) {
			return ROOT_DATAPROVIDER_KEY;
		} else {
			return hierarchicalVO.getClass().getName() + "#" + hierarchicalVO.getId();
		}
	}

	@Override
	public boolean isLeaf(Object value) {
		if (value instanceof DictionaryHierarchicalNodeVO) {
			DictionaryHierarchicalNodeVO hierarchicalNodeVO = (DictionaryHierarchicalNodeVO) value;

			if (hierarchicalNodeVO.getData().containsKey(HIERARCHICAL_ADD_NODE)) {
				return true;
			}

			return !hierarchicalNodeVO.getHasChildren();
		}

		return false;
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null || value instanceof DictionaryHierarchicalNodeVO) {
			final DictionaryHierarchicalNodeVO hierarchicalNodeVO = (DictionaryHierarchicalNodeVO) value;
			HierarchicalDataProvider dataProvider = new HierarchicalDataProvider(hierarchicalConfiguration, hierarchicalNodeVO, showAddnodes, value == null);
			dataProvider.addDataDisplay(cellList);
			dataProviders.put(getKey(hierarchicalNodeVO), dataProvider);

			return new HierarchicalNodeInfo(dataProvider, hierarchicalCell, selectionModel, new HierarchicalNodeCallback() {
				@Override
				public void onUnsetDataDisplay() {
					dataProviders.remove(getKey(hierarchicalNodeVO));
				}
			});
		} else {
			throw new RuntimeException("unsupported tree node type '" + value.getClass().getName() + "'");
		}
	}
}