package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations

class JvmTypeUtils {

	@Inject
	extension IJvmModelAssociations jvmModelAssociations
	

	def <T> T getWrappedTypeType(JvmTypeReference jvmTypeReference, Class<T> typeClass) {
		var typeObject = jvmModelAssociations.getSourceElements(jvmTypeReference.type).findFirst[e | typeClass.isAssignableFrom(e.class)] as T
		return typeObject
	}
	
	def <T> boolean hasOnlyType(List<JvmFormalParameter> jvmFormalParameters, Class<T> typeClass) {
		return !jvmFormalParameters.exists[e | !e.parameterType.isWrappedTypeType(typeClass)]
	}
	
	def <T> boolean isWrappedTypeType(JvmTypeReference jvmTypeReference, Class<T> typeClass) {
		return getWrappedTypeType(jvmTypeReference, typeClass) != null
	}

}