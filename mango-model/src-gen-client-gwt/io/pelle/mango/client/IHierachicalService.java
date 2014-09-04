package io.pelle.mango.client;

public interface IHierachicalService {
	java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>
	 getRootNodes(java.lang.String id
	 );
	io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO
	 getConfigurationById(java.lang.String id
	 );
	java.util.List<io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO>
	 getConfigurations();
	Boolean
	 hasChildren(java.lang.String voClassName, 
	 java.lang.Long voId
	 );
	java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>
	 getChildNodes(java.lang.String id, 
	 java.lang.Long voId, 
	 java.lang.String voClassName
	 );
}
