package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.db.vos.Length
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.query.EntityQuery
import io.pelle.mango.dsl.query.datatype.StringDatatypeQuery

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
	
		// validation annotations
	def dispatch validationAnnotation(EntityAttribute entityAttribute) {}

	def dispatch validationAnnotation(StringEntityAttribute stringEntityAttribute) {
		
		if (StringDatatypeQuery.createQuery(stringEntityAttribute.type).hasMaxLength)
		{
			return "@" + Length.name + "( maxLength = " + StringDatatypeQuery.createQuery(stringEntityAttribute.type).maxLength + ")"
		}
	}
}
