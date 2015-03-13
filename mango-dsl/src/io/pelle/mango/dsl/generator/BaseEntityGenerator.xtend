package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.client.base.vo.IVOEntity
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.query.EntityQuery

class BaseEntityGenerator {

	@Inject
	extension NameUtils

	@Inject
	extension TypeUtils

	def attributeDescriptorsFromExtends(Entity entity) '''
		«IF entity.extends != null»
			«FOR attribute : entity.extends.attributes»
				«attribute.compileEntityAttributeDescriptor(entity)»
			«ENDFOR»
		«ENDIF»
	'''

	def compileGetAttributeDescriptors(Entity entity) '''
		
		public static «IAttributeDescriptor.name»<?>[] getAttributeDescriptors() {
			
			return new «IAttributeDescriptor.name»[]{

				«IF !EntityQuery.isExtendedByOtherEntity(entity)»

						«IVOEntity.ID_FIELD_NAME.attributeConstantName»,
						
						«FOR attribute : entity.attributes»
							«attribute.name.attributeConstantName»,
						«ENDFOR»
						«IF entity.extends != null»
							«FOR attribute : entity.extends.attributes»
								«attribute.name.attributeConstantName»,
							«ENDFOR»
						«ENDIF»
						«FOR infoVOEntityAttribute : infoVOEntityAttributes().entrySet»
							«infoVOEntityAttribute.key.attributeDescriptorConstantName»,
						«ENDFOR»
				«ENDIF»
			};
		}
	'''

}
