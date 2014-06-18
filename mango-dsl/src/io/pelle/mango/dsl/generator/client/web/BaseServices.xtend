package io.pelle.mango.dsl.generator.client.web

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.mango.MethodParameter
import io.pelle.mango.dsl.mango.ServiceMethod
import java.util.List

class BaseServices {

	@Inject extension ClientTypeUtils

	def methodParameters(List<MethodParameter> methodParameters) '''
		«FOR methodParameter : methodParameters SEPARATOR ", "»
			«methodParameter.methodParameter»
		«ENDFOR»
	'''

	def methodParameter(MethodParameter methodParameter) {
		methodParameter.type + " " + methodParameter.name.toFirstLower()
	}

	def serviceMethod(ServiceMethod serviceMethod) '''
		«IF serviceMethod.genericTypeDefinition != null»
		«serviceMethod.genericTypeDefinition.genericTypeDefinition»
		«ENDIF»
		«serviceMethod.serviceMethodReturnType»
		 «serviceMethod.name.toFirstLower()»(«serviceMethod.methodParameters.methodParameters»);
	'''

	def serviceMethodReturnType(ServiceMethod serviceMethod) '''
		«IF serviceMethod.returnType == null»
			void
		«ELSE»
			«serviceMethod.returnType.type»
		«ENDIF»
	'''

}
