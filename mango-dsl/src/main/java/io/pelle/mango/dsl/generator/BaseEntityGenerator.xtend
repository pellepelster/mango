package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.client.base.vo.IEntity
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import java.util.List

abstract class BaseEntityGenerator {

	@Inject
	extension NameUtils

	def TypeUtils getTypeUtils()

	def attributeDescriptorsFromExtends(Entity entity) '''
		«IF entity.extends != null»
			«FOR attribute : entity.extends.attributes»
				«typeUtils.compileEntityAttributeDescriptor(attribute,  entity.metaDescriptorConstantName)»
			«ENDFOR»
		«ENDIF»
	'''

	def compileGetAttributeDescriptors(Entity entity, List<AttributeGeneratorFactory.AttributeGenerator> extraAttributes) '''
		
		public static «IAttributeDescriptor.name»<?>[] getAttributeDescriptors() {
			
			return new «IAttributeDescriptor.name»[]{

				«IEntity.ID_FIELD_NAME.attributeConstantName»,

				«FOR attribute : entity.attributes»
					«attribute.name.attributeConstantName»,
				«ENDFOR»
				
				«IF entity.extends != null»
					«FOR attribute : entity.extends.attributes»
						«attribute.name.attributeConstantName»,
					«ENDFOR»
				«ENDIF»
				
				«FOR infoVOEntityAttribute : typeUtils.infoVOEntityAttributes()»
					«infoVOEntityAttribute.attributeName.attributeDescriptorConstantName»,
				«ENDFOR»
				
				«FOR extraAttribute : extraAttributes»
					«extraAttribute.attributeName.attributeDescriptorConstantName»,
				«ENDFOR»
			};
		}
	'''

}
