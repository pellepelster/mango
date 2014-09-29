package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.BaseVO
import io.pelle.mango.client.base.vo.EntityDescriptor
import io.pelle.mango.client.base.vo.IEntityDescriptor
import io.pelle.mango.client.base.vo.IVOEntity
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.dsl.generator.BaseEntityGenerator
import io.pelle.mango.dsl.generator.util.AttributeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.ValueObject
import io.pelle.mango.dsl.query.EntityQuery
import java.util.List

class VOGenerator extends BaseEntityGenerator {

	@Inject
	extension AttributeUtils

	@Inject
	extension ClientNameUtils

	@Inject
	extension ClientTypeUtils
	
	def compileVO(Entity entity) '''
		package «getPackageName(entity)»;
		
		@SuppressWarnings("all")
		public class «entity.voName» extends «IF entity.extends != null»«voFullQualifiedName(entity.extends)»«ELSE»«typeof(BaseVO).name»«ENDIF» implements io.pelle.mango.client.base.db.vos.IInfoVOEntity {
		
			public static final «IEntityDescriptor.name»<«entity.voFullQualifiedName»> «entity.entityConstantName» = new «EntityDescriptor.name»<«entity.type»>(«entity.typeClass»);

			public static «LongAttributeDescriptor.name» «IVOEntity.ID_FIELD_NAME.attributeConstantName» = new «LongAttributeDescriptor.name»(«entity.entityConstantName», "«IVOEntity.ID_FIELD_NAME»");

			«entity.attributeDescriptorsFromExtends»

			«entity.compileGetAttributeDescriptors»
		
			private long id;
			
			«getterSetter("long", IVOEntity.ID_FIELD_NAME)»
			
			«FOR attribute : entity.attributes»
				«attribute.changeTrackingAttributeGetterSetter»
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
			
			«FOR infoVOEntityAttribute : infoVOEntityAttributes().entrySet»
				«changeTrackingAttributeGetterSetter(infoVOEntityAttribute.value, infoVOEntityAttribute.key, entity)»
			«ENDFOR»
		}
	'''

	def String compileValueObjectCloneConstructors(ValueObject rootValueObject, ValueObject valueObject) '''
		«IF valueObject.extends != null»
		public «rootValueObject.voName»(«valueObject.extends.voFullQualifiedName» «valueObject.extends.attributeName») {
			
			«IF valueObject.extends.extends != null»
			super(«valueObject.extends.attributeName»);
			«ENDIF»
			
			«FOR attribute : valueObject.extends.attributes»
				this.«attribute.setterName»(«valueObject.extends.attributeName».«attribute.getterName»());
			«ENDFOR»
		}
		
		«compileValueObjectCloneConstructors(rootValueObject, valueObject.extends)»
		«ENDIF»
	'''

	def compileValueObjectSetterConstructors(ValueObject valueObject) '''
		«IF !valueObject.attributes.empty»
		public «valueObject.voName»(«FOR attribute : valueObject.attributes SEPARATOR ", "»«getType(attribute)» «attribute.attributeName»«ENDFOR») {

			«FOR attribute : valueObject.attributes»
				this.«attribute.setterName»(«attribute.attributeName»);
			«ENDFOR»
		}
		«ENDIF»
	'''

	def compileValueObject(ValueObject valueObject) '''
		package «getPackageName(valueObject)»;
		
		public class «valueObject.voName» «IF valueObject.extends != null»extends «voFullQualifiedName(valueObject.extends)»«ENDIF» {
		
			public «valueObject.voName»() {
			}
			
			«compileValueObjectCloneConstructors(valueObject, valueObject)»

			«valueObject.compileValueObjectSetterConstructors»
		
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

	def changeTrackingAttributeGetterSetter(EntityAttribute attribute) '''
		«attribute(getType(attribute), attribute.name, getInitializer(attribute))»
		«IF !EntityQuery.isExtendedByOtherEntity(attribute.parentEntity)»
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

			«FOR infoVOEntityAttribute : infoVOEntityAttributes().entrySet»
				if ("«infoVOEntityAttribute.key.attributeName»".equals(name))
				{
					return this.«infoVOEntityAttribute.key.attributeName»;
				}
			«ENDFOR»
		
			return super.get(name);
		}
	'''

	//- genericVOSetter -----------------------------------------------------------
	def dispatch genericVOSetterValue(EntityAttribute entityAttribute) '''
		set«entityAttribute.name.toFirstUpper()»((«entityAttribute.type») value);'''

	def dispatch genericVOSetterValue(EnumerationEntityAttribute enumerationEntityAttribute) '''
		if (value instanceof «String.name»)
		{
			set«enumerationEntityAttribute.name.toFirstUpper()»(«enumerationEntityAttribute.enumerationFullQualifiedName».valueOf((«String.name») value));
		}
		else
		{
			set«enumerationEntityAttribute.name.toFirstUpper()»((«enumerationEntityAttribute.type.type») value);
		}'''

	def genericVOSetter(Entity entity) '''
		public void set(java.lang.String name, java.lang.Object value) {
		
			getChangeTracker().addChange(name, value);
		
			«FOR attribute : entity.attributes»
			if ("«attribute.name»".equals(name))
			{
				«attribute.genericVOSetterValue»
				return;
			}
			«ENDFOR»
		
			«FOR infoVOEntityAttribute : infoVOEntityAttributes().entrySet»
				if ("«infoVOEntityAttribute.key.attributeName»".equals(name))
				{
					«infoVOEntityAttribute.key.setterName»(«infoVOEntityAttribute.key.attributeName»);
					return;
				}
			«ENDFOR»
		
			super.set(name, value);
		}
	'''

}
