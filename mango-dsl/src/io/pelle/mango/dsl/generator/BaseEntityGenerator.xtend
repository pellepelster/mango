package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.Entity

class BaseEntityGenerator {

	@Inject 
	extension ClientNameUtils

	def compileGetAttributeDescriptors(Entity entity) '''
		
		public static «IAttributeDescriptor.name»<?>[] getAttributeDescriptors() {
			
			return new «IAttributeDescriptor.name»[]{
				«FOR attribute : entity.attributes SEPARATOR ", "»
					«attribute.name.attributeConstantName»
				«ENDFOR»
			};
		}
	'''
}
