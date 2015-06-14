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

import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;

public class HierarchicalTree extends Div {

	public HierarchicalTree(HierarchicalConfigurationVO hierarchicalConfiguration, boolean showAddnodes, SimpleCallback<DictionaryHierarchicalNodeVO> nodeSelectionHandler) {
		CellTree.Resources treeResources = GWT.create(CellTree.BasicResources.class);

		HierarchicalTreeModel hierarchicalTreeModel = new HierarchicalTreeModel(hierarchicalConfiguration, showAddnodes, nodeSelectionHandler);
		CellTree cellTree = new CellTree(hierarchicalTreeModel, null, treeResources);
		add(cellTree);

	}

}
