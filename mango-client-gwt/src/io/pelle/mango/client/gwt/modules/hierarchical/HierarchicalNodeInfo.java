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

import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;

public class HierarchicalNodeInfo extends DefaultNodeInfo<DictionaryHierarchicalNodeVO> {

	private HierarchicalNodeCallback hierarchicalNodeCallback;

	public interface HierarchicalNodeCallback {
		void onUnsetDataDisplay();
	}

	public HierarchicalNodeInfo(HierarchicalDataProvider dataProvider, Cell<DictionaryHierarchicalNodeVO> hierarchicalCell, SingleSelectionModel<DictionaryHierarchicalNodeVO> selectionModel, HierarchicalNodeCallback hierarchicalNodeCallback) {
		super(dataProvider, hierarchicalCell, selectionModel, null);

		this.hierarchicalNodeCallback = hierarchicalNodeCallback;

	}

	@Override
	public void unsetDataDisplay() {
		super.unsetDataDisplay();
		hierarchicalNodeCallback.onUnsetDataDisplay();
	}

}
