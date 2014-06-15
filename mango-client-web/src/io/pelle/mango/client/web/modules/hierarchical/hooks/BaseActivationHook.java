package io.pelle.mango.client.web.modules.hierarchical.hooks;

import io.pelle.mango.client.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.web.modules.hierarchical.HierarchicalTreeModule;

public class BaseActivationHook {
	public void onActivate(DictionaryHierarchicalNodeVO hierarchicalNodeVO) {
		HierarchicalTreeModule.openModuleForNode(hierarchicalNodeVO);
	}
}
