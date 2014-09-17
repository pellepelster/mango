package io.pelle.mango.dsl.generator.util

import io.pelle.mango.dsl.mango.ServiceMethod
import io.pelle.mango.dsl.mango.SimpleDatatypeEntityAttribute
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter

class ServiceUtils {

	def onlySimpleTypes(List<JvmFormalParameter> methodParameters) {

		for (JvmFormalParameter methodParameter : methodParameters) {

			if (!SimpleDatatypeEntityAttribute.isAssignableFrom(methodParameter.class)) {
				return false
			}
		}

		return false
	}

	def hasReturn(ServiceMethod serviceMethod) {
		return serviceMethod.returnType != null
	}

}