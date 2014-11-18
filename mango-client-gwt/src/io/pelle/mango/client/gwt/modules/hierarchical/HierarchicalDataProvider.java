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

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class HierarchicalDataProvider extends AsyncDataProvider<DictionaryHierarchicalNodeVO> {

	private HierarchicalConfigurationVO hierarchicalConfiguration;

	private DictionaryHierarchicalNodeVO parentHierarchicalNode;

	private boolean showAddNodes;

	private boolean isRootProvider;

	private List<DictionaryHierarchicalNodeVO> items = new ArrayList<DictionaryHierarchicalNodeVO>();;

	public HierarchicalDataProvider(HierarchicalConfigurationVO hierarchicalConfiguration, DictionaryHierarchicalNodeVO hierarchicalNode, boolean showAddNodes, boolean isRootProvider) {
		this.hierarchicalConfiguration = hierarchicalConfiguration;
		this.parentHierarchicalNode = hierarchicalNode;
		this.showAddNodes = showAddNodes;
		this.isRootProvider = isRootProvider;
	}

	@Override
	protected void onRangeChanged(HasData<DictionaryHierarchicalNodeVO> display) {

		Long parentId = null;
		String parentClassname = null;

		final List<DictionaryHierarchicalNodeVO> addNodes = new ArrayList<DictionaryHierarchicalNodeVO>();

		if (parentHierarchicalNode != null) {
			parentId = parentHierarchicalNode.getVoId();
			parentClassname = parentHierarchicalNode.getVoClassName();

			if (showAddNodes || isRootProvider) {
				for (Map.Entry<String, List<String>> entry : hierarchicalConfiguration.getDictionaryHierarchy().entrySet()) {
					if (entry.getValue().contains(parentHierarchicalNode.getDictionaryName())) {
						DictionaryHierarchicalNodeVO addHierarchicalNode = new DictionaryHierarchicalNodeVO();
						addHierarchicalNode.setDictionaryName(entry.getKey());
						addHierarchicalNode.setLabel(DictionaryUtil.getDictionaryAdd(DictionaryModelProvider.getDictionary(entry.getKey())));
						addHierarchicalNode.setHasChildren(false);
						addHierarchicalNode.setParentClassName(parentClassname);
						addHierarchicalNode.setParentVOId(parentId);
						addHierarchicalNode.getData().put(HierarchicalTreeModel.HIERARCHICAL_ADD_NODE, true);
						addNodes.add(addHierarchicalNode);
					}
				}
			}
		} else {
			if (showAddNodes || isRootProvider) {
				for (Map.Entry<String, List<String>> entry : hierarchicalConfiguration.getDictionaryHierarchy().entrySet()) {
					if (entry.getValue().isEmpty()) {
						DictionaryHierarchicalNodeVO addHierarchicalNode = new DictionaryHierarchicalNodeVO();
						addHierarchicalNode.setDictionaryName(entry.getKey());
						addHierarchicalNode.setLabel(DictionaryUtil.getDictionaryAdd(DictionaryModelProvider.getDictionary(entry.getKey())));
						addHierarchicalNode.setHasChildren(false);
						addHierarchicalNode.getData().put(HierarchicalTreeModel.HIERARCHICAL_ADD_NODE, true);
						addNodes.add(addHierarchicalNode);
					}
				}
			}
		}

		MangoClientWeb.getInstance().getRemoteServiceLocator().getHierachicalService().getChildNodes(hierarchicalConfiguration.getId(), parentId, parentClassname, new AsyncCallback<List<DictionaryHierarchicalNodeVO>>() {
			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException(caught);
			}

			@Override
			public void onSuccess(List<DictionaryHierarchicalNodeVO> result) {
				result.addAll(addNodes);
				items.clear();
				items.addAll(result);
				updateRowData(0, result);
			}
		});
	}

	public List<DictionaryHierarchicalNodeVO> getItems() {
		return items;
	}

}