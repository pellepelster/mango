package io.pelle.mango.client.modules;

public abstract class BaseModuleHierarchicalTreeModule extends io.pelle.mango.client.base.module.BaseModule {

	public static final String MODULE_ID = "ModuleHierarchicalTree";

	public BaseModuleHierarchicalTreeModule(String moduleUrl, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.module.IModule> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}

	
	public static final String HIERARCHICALTREEID_PARAMETER_ID = "HierarchicalTreeId";
	
	public String getHierarchicalTreeId() {

		if (getParameters().containsKey("HierarchicalTreeId"))
		{
			Object parameterValue = parameters.get("HierarchicalTreeId");
		
			if (parameterValue instanceof String)
			{
				return (String) parameterValue;
			}
			
			
			if (parameterValue instanceof java.lang.String)
			{
				return parameterValue.toString();
			}
			
			throw new RuntimeException("parameter value type mismatch, expected 'String' but got '" + parameterValue.getClass().getName() + "'");
		}
		else
		{
			return null;
		}
	}
	
	public boolean hasHierarchicalTreeId() {
		return getParameters().containsKey("HierarchicalTreeId");
	}

	
	public static final String SHOWADDNODES_PARAMETER_ID = "ShowAddNodes";
	
	public Boolean getShowAddNodes() {

		if (getParameters().containsKey("ShowAddNodes"))
		{
			Object parameterValue = parameters.get("ShowAddNodes");
		
			if (parameterValue instanceof Boolean)
			{
				return (Boolean) parameterValue;
			}
			
			
			if (parameterValue instanceof java.lang.String)
			{
				return java.lang.Boolean.parseBoolean(parameterValue.toString());
			}
			
			throw new RuntimeException("parameter value type mismatch, expected 'Boolean' but got '" + parameterValue.getClass().getName() + "'");
		}
		else
		{
			return null;
		}
	}
	
	public boolean hasShowAddNodes() {
		return getParameters().containsKey("ShowAddNodes");
	}


	public static java.util.Map<String, Object> getParameterMap(String hierarchicalTreeId, 
	Boolean showAddNodes
	) {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		parameterMap.put(HIERARCHICALTREEID_PARAMETER_ID, hierarchicalTreeId);
		parameterMap.put(SHOWADDNODES_PARAMETER_ID, showAddNodes);
		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
