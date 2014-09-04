package io.pelle.mango.client.modules;

public abstract class BaseDictionarySearchModule extends io.pelle.mango.client.base.module.BaseModule {

	public static final String MODULE_ID = "DictionarySearch";

	public BaseDictionarySearchModule(String moduleUrl, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.module.IModule> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}

	
	public static final String SEARCHDICTIONARYNAME_PARAMETER_ID = "SearchDictionaryName";
	
	public String getSearchDictionaryName() {

		if (getParameters().containsKey("SearchDictionaryName"))
		{
			Object parameterValue = parameters.get("SearchDictionaryName");
		
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
	
	public boolean hasSearchDictionaryName() {
		return getParameters().containsKey("SearchDictionaryName");
	}

	
	public static final String SEARCHTEXT_PARAMETER_ID = "SearchText";
	
	public String getSearchText() {

		if (getParameters().containsKey("SearchText"))
		{
			Object parameterValue = parameters.get("SearchText");
		
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
	
	public boolean hasSearchText() {
		return getParameters().containsKey("SearchText");
	}


	public static java.util.Map<String, Object> getParameterMap(String searchDictionaryName, 
	String searchText
	) {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		parameterMap.put(SEARCHDICTIONARYNAME_PARAMETER_ID, searchDictionaryName);
		parameterMap.put(SEARCHTEXT_PARAMETER_ID, searchText);
		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
