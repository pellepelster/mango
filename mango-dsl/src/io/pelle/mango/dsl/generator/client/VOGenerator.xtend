package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.client.base.db.vos.NaturalKey
import io.pelle.mango.client.base.vo.BaseVO
import io.pelle.mango.client.base.vo.EntityDescriptor
import io.pelle.mango.client.base.vo.IEntityDescriptor
import io.pelle.mango.client.base.vo.IVOEntity
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.AttributeUtils
import io.pelle.mango.dsl.generator.BaseEntityGenerator
import io.pelle.mango.dsl.generator.EntityUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.ValueObject
import java.util.List

class VOGenerator extends BaseEntityGenerator {

	@Inject
	extension AttributeUtils

	@Inject
	extension ClientNameUtils

	@Inject
	extension ClientTypeUtils
	
	@Inject
	extension EntityUtils

	def compileVO(Entity entity) '''
		package «getPackageName(entity)»;
		
		@SuppressWarnings("serial")
		public class «entity.voName» extends «IF entity.extends != null»«voFullQualifiedName(entity.extends)»«ELSE»«typeof(BaseVO).name»«ENDIF» {
		
			public static final «IEntityDescriptor.name»<«entity.voFullQualifiedName»> «entity.entityConstantName» = new «EntityDescriptor.name»<«entity.type»>(«entity.typeClass»);

			«compileGetAttributeDescriptors(entity)»
			
			public static «LongAttributeDescriptor.name» «IVOEntity.ID_FIELD_NAME.attributeConstantName» = new «LongAttributeDescriptor.name»(«entity.entityConstantName», "«IVOEntity.ID_FIELD_NAME»");

			«IF entity.extends != null»
				«FOR attribute : entity.extends.attributes»
				«attribute.compileEntityAttributeDescriptor(attribute.parentEntity)»
				«ENDFOR»
			«ENDIF»
		
			private long id;
			
			«getterSetter("long", IVOEntity.ID_FIELD_NAME)»
			
			«FOR attribute : entity.attributes»
				«attribute.compileVOAttribute»
			«ENDFOR»
			
			«entity.genericVOGetter»
			
			«entity.genericVOSetter»
			
			«IF !entity.naturalKeyAttributes.isEmpty»
				@java.lang.Override
				public String getNaturalKey() 
				{
					java.lang.StringBuffer sb = new java.lang.StringBuffer();
					«FOR naturalKeyAttribute : entity.naturalKeyAttributes SEPARATOR "sb.append(\", \");"»
					sb.append(this.get«naturalKeyAttribute.name.toFirstUpper()»());
					«ENDFOR»
					return sb.toString();
				}
				
			«ENDIF»
		}
	'''

	def compileValueObject(ValueObject valueObject) '''
		package «getPackageName(valueObject)»;
		
		public class «valueObject.voName» «IF valueObject.extends != null»extends «voFullQualifiedName(valueObject.extends)»«ENDIF» {
		
			public «valueObject.voName»() {
			}
		
			«FOR attribute : valueObject.attributes»
			«attribute.compileValueObjectAttribute»
			«ENDFOR»
		
		}
	'''

	def compileEnumeration(Enumeration enumeration) '''
		package «getPackageName(enumeration)»;
		
		public enum «enumeration.enumerationName» {
		
			«FOR enumerationValue : enumeration.enumerationValues SEPARATOR ", "»
			«enumerationValue.toUpperCase»
			«ENDFOR»
		
		}
	'''

	def compileValueObjectConstructor(List<EntityAttribute> attributes) {
	}

	def compileValueObjectAttribute(EntityAttribute entityAttribute) '''
		«attribute(getType(entityAttribute), entityAttribute.name, getInitializer(entityAttribute))»
		«getter(getType(entityAttribute), entityAttribute.name.attributeName)»
		«setter(getType(entityAttribute), entityAttribute.name.attributeName)»
	'''

	def compileVOAttribute(EntityAttribute entityAttribute) '''
		«changeTrackingAttributeGetterSetter(entityAttribute)»
	'''

	def changeTrackingAttributeGetterSetter(EntityAttribute attribute) '''
		«IF attribute.parentEntity.naturalKeyAttributes.contains(attribute)»@«NaturalKey.name»( order = «ModelUtil.getParentEntity(attribute).naturalKeyAttributes.indexOf(attribute)»)«ENDIF»
		«attribute(getType(attribute), attribute.name, getInitializer(attribute))»
		«IF !attribute.parentEntity.extendedByOtherEntity»
		«attribute.compileEntityAttributeDescriptor(attribute.parentEntity)»
		«ENDIF»
		«getter(getType(attribute), attribute.name.attributeName)»
		«changeTrackingSetter(getType(attribute), attribute.name.attributeName)»
	'''

	//- genericVOGetter -----------------------------------------------------------
	def genericVOGetter(Entity entity) '''
		public Object get(java.lang.String name) {
		
			«FOR attribute : entity.attributes»
			if ("«attribute.name»".equals(name))
			{
				return this.«attribute.name»;
			}
			«ENDFOR»
		
			return super.get(name);
		}
	'''

	//- genericVOSetter -----------------------------------------------------------
	def genericVOSetter(Entity entity) '''
		public void set(java.lang.String name, java.lang.Object value) {
		
			getChangeTracker().addChange(name, value);
		
			«FOR attribute : entity.attributes»
			if ("«attribute.name»".equals(name))
			{
				set«attribute.name.toFirstUpper()»((«attribute.type») value);
				return;
			}
			«ENDFOR»
		
			super.set(name, value);
		}
	'''

}
