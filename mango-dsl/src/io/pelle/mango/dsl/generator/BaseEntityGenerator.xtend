package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.Entity

class BaseEntityGenerator {

	@Inject 
	extension 
	ClientNameUtils

	@Inject
	extension EntityUtils
	
	def compileGetAttributeDescriptors(Entity entity) '''
		
		public static «IAttributeDescriptor.name»<?>[] getAttributeDescriptors() {
			
			return new «IAttributeDescriptor.name»[]{
				
				«IF !entity.isExtendedByOtherEntity»
				
					«FOR attribute : entity.attributes SEPARATOR ", "»
						«attribute.name.attributeConstantName»
					«ENDFOR»
					«IF !entity.attributes.empty»,«ENDIF»
					«IF entity.extends != null»
						«FOR attribute : entity.extends.attributes SEPARATOR ", "»
							«attribute.name.attributeConstantName»
						«ENDFOR»
					«ENDIF»
				«ENDIF»
			};
		}
	'''
}
