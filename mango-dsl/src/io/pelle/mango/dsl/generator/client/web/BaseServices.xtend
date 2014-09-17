package io.pelle.mango.dsl.generator.client.web

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.ServiceMethod
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter

class BaseServices {

	@Inject
	extension NameUtils;

	@Inject
	extension ClientTypeUtils;

	def methodParameters(List<JvmFormalParameter> parameters) '''
		«FOR parameter : parameters SEPARATOR ", "»
			«parameter.parameterType.qualifiedName» «parameter.name»
		«ENDFOR»
	'''

	def methodTypeParameter(ServiceMethod method) '''
		«IF method.typeParameter != null»<«method.typeParameter.qualifiedName» «FOR constraint : method.typeParameter.constraints SEPARATOR ","»«constraint.qualifiedName»«ENDFOR»>«ENDIF»
	'''

	def methodReturn(ServiceMethod method) '''
		«method.methodTypeParameter» «method.returnType.jvmType»
	'''

	def serviceMethod(ServiceMethod method) '''
		«method.methodReturn» «method.methodName»(«method.params.methodParameters»);
	'''

	def returnsVoid(ServiceMethod method) {
		return method.returnType.type.qualifiedName.equals("void")
	}
}
