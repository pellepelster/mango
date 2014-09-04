package io.pelle.mango.client.modules;

public abstract class BaseDictionaryEditorModule extends io.pelle.mango.client.base.module.BaseModule {

	public static final String MODULE_ID = "DictionaryEditor";

	public BaseDictionaryEditorModule(String moduleUrl, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.module.IModule> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}

	
	public static final String EDITORDICTIONARYNAME_PARAMETER_ID = "EditorDictionaryName";
	
	public String getEditorDictionaryName() {

		if (getParameters().containsKey("EditorDictionaryName"))
		{
			Object parameterValue = parameters.get("EditorDictionaryName");
		
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
	
	public boolean hasEditorDictionaryName() {
		return getParameters().containsKey("EditorDictionaryName");
	}

	
	public static final String ID_PARAMETER_ID = "Id";
	
	public Long getId() {

		if (getParameters().containsKey("Id"))
		{
			Object parameterValue = parameters.get("Id");
		
			if (parameterValue instanceof Long)
			{
				return (Long) parameterValue;
			}
			
			
			if (parameterValue instanceof java.lang.String)
			{
				return java.lang.Long.parseLong(parameterValue.toString());
			}
			
			throw new RuntimeException("parameter value type mismatch, expected 'Long' but got '" + parameterValue.getClass().getName() + "'");
		}
		else
		{
			return null;
		}
	}
	
	public boolean hasId() {
		return getParameters().containsKey("Id");
	}


	public static java.util.Map<String, Object> getParameterMap(String editorDictionaryName, 
	Long id
	) {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		parameterMap.put(EDITORDICTIONARYNAME_PARAMETER_ID, editorDictionaryName);
		parameterMap.put(ID_PARAMETER_ID, id);
		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
