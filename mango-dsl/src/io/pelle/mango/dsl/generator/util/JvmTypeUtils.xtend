package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.ValueObject
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations

class JvmTypeUtils {

	@Inject
	ClientNameUtils clientNameUtils

	@Inject
	extension ClientTypeUtils;
	
	@Inject
	extension IJvmModelAssociations jvmModelAssociations
	
	def String jvmType(List<JvmTypeReference> jvmTypeReferences) '''
		«FOR jvmTypeReference : jvmTypeReferences SEPARATOR ", "»«jvmTypeReference.jvmType»«ENDFOR»
	'''

	def String jvmTypeInternal(JvmTypeReference jvmTypeReference) {

		//		System.out.println("==============================================")
		//		System.out.println("jvmTypeReference: " + jvmTypeReference.toString)
		//		System.out.println("----------------------------------------------")
		//		for (e : jvmModelAssociations.getSourceElements(jvmTypeReference))
		//		{
		//			System.out.println("source element: " + e.toString)
		//		}		
		//		System.out.println("----------------------------------------------")
		//
		//		System.out.println("==============================================")
		//		System.out.println("jvmTypeReference.type: " + jvmTypeReference.type.toString)
		//		System.out.println("----------------------------------------------")
		//		for (e : jvmModelAssociations.getSourceElements(jvmTypeReference.type))
		//		{
		//			System.out.println("source element: " + e.toString)
		//		}		
		//		System.out.println("----------------------------------------------")
		var entity = jvmModelAssociations.getSourceElements(jvmTypeReference.type).findFirst[e|e instanceof Entity] as Entity
		if (entity != null) {
			return entity.entityVOFullQualifiedName
		}

		var valueObject = jvmModelAssociations.getSourceElements(jvmTypeReference.type).findFirst[e|e instanceof ValueObject] as ValueObject
		if (valueObject != null) {
			return clientNameUtils.voFullQualifiedName(valueObject)
		}

		var enumeration = jvmModelAssociations.getSourceElements(jvmTypeReference.type).findFirst[e|e instanceof Enumeration] as Enumeration
		if (enumeration != null) {
			return clientNameUtils.enumerationFullQualifiedName(enumeration)
		}

		return jvmTypeReference.qualifiedName
	}

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

	def String jvmType(JvmTypeReference jvmTypeReference) {

		if (jvmTypeReference instanceof JvmParameterizedTypeReference) {
			var JvmParameterizedTypeReference parameterizedTypeReference = jvmTypeReference

			if (!jvmTypeReference.arguments.empty) {
				return parameterizedTypeReference.type.qualifiedName + '<' + parameterizedTypeReference.arguments.jvmType + '>'
			} else {
				return parameterizedTypeReference.jvmTypeInternal
			}
		} else {
			return jvmTypeReference.jvmTypeInternal
		}

	}
}
