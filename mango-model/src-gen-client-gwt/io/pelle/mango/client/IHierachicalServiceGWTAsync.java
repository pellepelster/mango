package io.pelle.mango.client;

public interface IHierachicalServiceGWTAsync {

	
	void getRootNodes(java.lang.String id
	, com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>> callback
	)
	;
	
	void getConfigurationById(java.lang.String id
	, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO> callback
	)
	;
	
	void getConfigurations(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO>> callback
	)
	;
	
	void hasChildren(java.lang.String voClassName, 
	java.lang.Long voId
	, com.google.gwt.user.client.rpc.AsyncCallback<Boolean> callback
	)
	;
	
	void getChildNodes(java.lang.String id, 
	java.lang.Long voId, 
	java.lang.String voClassName
	, com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>> callback
	)
	;
}
