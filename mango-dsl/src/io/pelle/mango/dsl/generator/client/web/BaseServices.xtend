package io.pelle.mango.dsl.generator.client.web

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.ServiceMethod
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter

class BaseServices {

	@Inject
	extension NameUtils;

	def methodParameters(List<JvmFormalParameter> parameters) '''
		«FOR parameter : parameters SEPARATOR ", "»
			«parameter.parameterType.qualifiedName» «parameter.name»
		«ENDFOR»
	'''

	def methodTypeParameter(ServiceMethod method) '''
		«IF method.typeParameter != null»<«method.typeParameter.qualifiedName»«FOR constraint : method.typeParameter.constraints SEPARATOR ","»«constraint.qualifiedName»«ENDFOR»>«ENDIF»
	'''

	def serviceMethod(ServiceMethod method) '''
		«method.methodTypeParameter» «method.returnType.type.qualifiedName» «method.methodName»(«method.params.methodParameters»);
	'''

}
