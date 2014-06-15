package io.pelle.mango.server;

import io.pelle.mango.client.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.IHierachicalService;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;

import java.util.List;

public class HierachicalServiceImpl implements IHierachicalService {

	@Override
	public List<DictionaryHierarchicalNodeVO> getRootNodes(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HierarchicalConfigurationVO getConfigurationById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HierarchicalConfigurationVO> getConfigurations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasChildren(String voClassName, Long voId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DictionaryHierarchicalNodeVO> getChildNodes(String id, Long voId, String voClassName) {
		// TODO Auto-generated method stub
		return null;
	}

}
