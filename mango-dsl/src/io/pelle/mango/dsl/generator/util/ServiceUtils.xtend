package io.pelle.mango.dsl.generator.util

import io.pelle.mango.dsl.mango.MethodParameter
import io.pelle.mango.dsl.mango.SimpleDatatypeEntityAttribute
import java.util.List
import io.pelle.mango.dsl.mango.MethodReturnType
import io.pelle.mango.dsl.mango.ServiceMethod

class ServiceUtils {

	def onlySimpleTypes(List<MethodParameter> methodParameters) {

		for (MethodParameter methodParameter : methodParameters) {

			if (!SimpleDatatypeEntityAttribute.isAssignableFrom(methodParameter.class)) {
				return false
			}
		}

		return true
	}

	def isSimpleType(MethodReturnType methodReturnType) {

		if (!SimpleDatatypeEntityAttribute.isAssignableFrom(methodReturnType.class)) {
			return false
		}

		return true
	}

	def hasReturn(ServiceMethod serviceMethod) {
		return serviceMethod.returnType != null
	}

}