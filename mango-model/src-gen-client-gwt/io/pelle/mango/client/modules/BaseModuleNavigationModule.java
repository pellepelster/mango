package io.pelle.mango.client.modules;

public abstract class BaseModuleNavigationModule extends io.pelle.mango.client.base.module.BaseModule {

	public static final String MODULE_ID = "ModuleNavigation";

	public BaseModuleNavigationModule(String moduleUrl, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.module.IModule> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}

	
	public static final String NAVIGATIONTREEELEMENTNAME_PARAMETER_ID = "NavigationTreeElementName";
	
	public String getNavigationTreeElementName() {

		if (getParameters().containsKey("NavigationTreeElementName"))
		{
			Object parameterValue = parameters.get("NavigationTreeElementName");
		
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
	
	public boolean hasNavigationTreeElementName() {
		return getParameters().containsKey("NavigationTreeElementName");
	}


	public static java.util.Map<String, Object> getParameterMap(String navigationTreeElementName
	) {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		parameterMap.put(NAVIGATIONTREEELEMENTNAME_PARAMETER_ID, navigationTreeElementName);
		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
