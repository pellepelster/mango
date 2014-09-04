package io.pelle.mango.client.modules;

public abstract class BaseDictionaryQueryModule extends io.pelle.mango.client.base.module.BaseModule {

	public static final String MODULE_ID = "DictionaryQuery";

	public BaseDictionaryQueryModule(String moduleUrl, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.module.IModule> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}


	public static java.util.Map<String, Object> getParameterMap() {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
