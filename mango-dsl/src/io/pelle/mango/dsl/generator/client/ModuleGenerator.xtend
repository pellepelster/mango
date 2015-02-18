package io.pelle.mango.dsl.generator.client

import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.inject.Inject
import io.pelle.mango.client.base.module.BaseModule
import io.pelle.mango.client.base.module.IModule
import io.pelle.mango.dsl.mango.ModuleDefinition

class ModuleGenerator {

	@Inject extension ClientNameUtils

	@Inject extension ClientTypeUtils

	def compileBaseModuleDefinition(ModuleDefinition moduleDefinition) '''
package «getPackageName(moduleDefinition)»;

public abstract class «moduleDefinition.baseModuleDefinitionName» extends «BaseModule.name» {

	public static final String MODULE_ID = "«moduleDefinition.name»";

	public «moduleDefinition.baseModuleDefinitionName»(String moduleUrl, «AsyncCallback.name»<«IModule.name»> moduleCallback, java.util.Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);
	}

	«FOR moduleDefinitionParameter : moduleDefinition.moduleDefinitionParameters»
	
	public static final String «moduleDefinitionParameter.name.toUpperCase()»_PARAMETER_ID = "«moduleDefinitionParameter.name»";
	
	public «moduleDefinitionParameter.type.type» get«moduleDefinitionParameter.name.toFirstUpper()»() {

		if (getParameters().containsKey("«moduleDefinitionParameter.name»"))
		{
			Object parameterValue = parameters.get("«moduleDefinitionParameter.name»");
		
			if (parameterValue instanceof «moduleDefinitionParameter.type.type»)
			{
				return («moduleDefinitionParameter.type.type») parameterValue;
			}
			
			
			if (parameterValue instanceof java.lang.String)
			{
				return «parseSimpleTypeFromString(moduleDefinitionParameter.type, "parameterValue.toString()")»;
			}
			
			throw new RuntimeException("parameter value type mismatch, expected '«moduleDefinitionParameter.type.type»' but got '" + parameterValue.getClass().getName() + "'");
		}
		else
		{
			return null;
		}
	}
	
	public boolean has«moduleDefinitionParameter.name.toFirstUpper()»() {
		return getParameters().containsKey("«moduleDefinitionParameter.name»");
	}

	«ENDFOR»	

	public static java.util.Map<String, Object> getParameterMap(«moduleDefinitionParameters(moduleDefinition)») {
	
		java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();

		«FOR moduleDefinitionParameter : moduleDefinition.moduleDefinitionParameters»
			parameterMap.put(«moduleDefinitionParameter.name.toUpperCase()»_PARAMETER_ID, «moduleDefinitionParameter.name.toFirstLower()»);
		«ENDFOR»
		
		return parameterMap;
	}

	public boolean hasParameter(String parameterName) {
		return getParameters().containsKey(parameterName);
	}

}
'''

def moduleDefinitionParameters(ModuleDefinition moduleDefinition) '''
	«FOR moduleDefinitionParameter : moduleDefinition.moduleDefinitionParameters SEPARATOR ", "»
		«moduleDefinitionParameter.type.getType()» «moduleDefinitionParameter.name.toFirstLower()»
	«ENDFOR»
'''
}
