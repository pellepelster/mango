package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.client.base.db.vos.IHierarchicalVO
import io.pelle.mango.client.base.vo.BaseVO
import io.pelle.mango.client.base.vo.EntityDescriptor
import io.pelle.mango.client.base.vo.IEntityDescriptor
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.dsl.generator.BaseEntityGenerator
import io.pelle.mango.dsl.generator.server.EntityUtils
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.ValueObject
import java.io.Serializable
import java.util.ArrayList
import java.util.List
import io.pelle.mango.client.base.vo.IEntity

class VOGenerator extends BaseEntityGenerator {

	@Inject
	extension EntityUtils
	
	@Inject
	extension ClientAttributeUtils

	@Inject
	extension ClientNameUtils

	@Inject
	extension ClientTypeUtils typeUtils
	
	def compileVO(Entity entity) '''
		package «getPackageName(entity)»;
		
		@SuppressWarnings("all")
		public class «entity.voName» extends «IF entity.extends != null»«voFullQualifiedName(entity.extends)»«ELSE»«typeof(BaseVO).name»«ENDIF» implements io.pelle.mango.client.base.db.vos.IInfoVOEntity «IF entity.entityHierarchical», «IHierarchicalVO.name»«ENDIF» {
		
			public static final «IEntityDescriptor.name»<«entity.voFullQualifiedName»> «entity.entityConstantName» = new «EntityDescriptor.name»<«entity.type»>(«entity.typeClass», "«entity.name»", "«entity.label»", "«entity.pluralLabel»");

			public static «LongAttributeDescriptor.name» «IEntity.ID_FIELD_NAME.attributeConstantName» = new «LongAttributeDescriptor.name»(«entity.entityConstantName», "«IEntity.ID_FIELD_NAME»");

			public «entity.voName»() {
			«FOR attribute : entity.attributes»
			«IF attribute.hasCardinality && attribute.cardinality != Cardinality::ONETOONE»
				getMetadata().addListChangeTracker(«attribute.attributeName»);
			«ENDIF»
			«ENDFOR»
			}

			«entity.attributeDescriptorsFromExtends»

			«entity.compileGetAttributeDescriptors»
		
			private long id;
			
			«getterSetter("long", IEntity.ID_FIELD_NAME)»
			
			«FOR attribute : entity.attributes»
				«attribute.changeTrackingAttributeGetterSetter(entity)»
			«ENDFOR»
			
			«entity.genericVOGetter»
			
			«entity.genericVOSetter»
			
			«entity.compileNaturalKey»

			«IF entity.entityHierarchical»
				«FOR hierarchicalVOAttribute : hierarchicalVOAttributes()»
					«hierarchicalVOAttribute.generate(entity)»
				«ENDFOR»
			«ENDIF»

			«FOR infoVOEntityAttribute : infoVOEntityAttributes()»
				«infoVOEntityAttribute.generate(entity)»
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
		
		public class «valueObject.voName» «IF valueObject.extends != null»extends «voFullQualifiedName(valueObject.extends)»«ENDIF» implements «Serializable.name» {
		
			public «valueObject.voName»() {
			}
			
			«compileValueObjectCloneConstructors(valueObject, valueObject)»

			«valueObject.compileValueObjectSetterConstructors»
		
			«FOR attribute : valueObject.attributes»
			«attribute.compileValueObjectAttribute»
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

	def changeTrackingAttributeGetterSetter(EntityAttribute attribute, Entity entity) '''
		«attribute.compileEntityAttributeDescriptor(entity)»
		
		«attribute(getMemberType(attribute), attribute.name, getInitializer(attribute))»
		
		«IF attribute.isEntityReference»
		«checkLoadedGetter(getType(attribute), attribute.name.attributeName)»
		«ELSE»
		«getter(getType(attribute), attribute.name.attributeName)»
		«ENDIF»

		«IF attribute.hasCardinality && attribute.cardinality != Cardinality::ONETOONE»
		«changeTrackingListSetter(getType(attribute), attribute.name.attributeName)»
		«ELSE»
		«changeTrackingSetter(getType(attribute), attribute.name.attributeName)»
		«ENDIF»
		
	'''

	def checkLoadedGetter(String attributeType, String attributeName) '''
		public «attributeType» «attributeName.getterName»() {
			getMetadata().checkLoaded("«attributeName»");
			return this.«attributeName»;
		}
	'''

	//- genericVOGetter -----------------------------------------------------------
	def genericVOGetter(Entity entity) '''
		public Object get(java.lang.String name) {
		
			«FOR attribute : entity.attributes»
			if ("«attribute.name»".equals(name))
			{
				return this.«attribute.getterName»();
			}
			«ENDFOR»

			«IF entity.entityHierarchical»
			if («typeof(IHierarchicalVO).name».FIELD_PARENT.getAttributeName().equals(name))
			{
				return getParent();
			}
			«ENDIF»

			«FOR infoVOEntityAttribute : infoVOEntityAttributes()»
			if ("«infoVOEntityAttribute.attributeName.attributeName»".equals(name))
			{
				return this.«infoVOEntityAttribute.attributeName.attributeName»;
			}
			«ENDFOR»
		
			return super.get(name);
		}
	'''

	//- genericVOSetter -----------------------------------------------------------
	def dispatch genericVOSetterValue(EntityAttribute entityAttribute) '''
		set«entityAttribute.name.toFirstUpper()»((«entityAttribute.type») value);'''

	def dispatch genericVOSetterValue(EnumerationEntityAttribute enumerationEntityAttribute) '''
		«IF enumerationEntityAttribute.cardinality == Cardinality.ONETOMANY»
		if (value instanceof java.util.List) {
			
			java.util.List enumValues = (java.util.List) value;
			
			for (Object enumValue : enumValues) {
				
				if (enumValue instanceof java.lang.String) {
					get«enumerationEntityAttribute.name.toFirstUpper()»().add(«enumerationEntityAttribute.type.type».valueOf(enumValue.toString()));
				}
				else {
					get«enumerationEntityAttribute.name.toFirstUpper()»().add((«enumerationEntityAttribute.type.type») enumValue);
				}
			}
		}
		«ELSE»
		if (value instanceof «String.name»)
		{
			set«enumerationEntityAttribute.name.toFirstUpper()»(«enumerationEntityAttribute.type.type».valueOf((«String.name») value));
		} else {
			set«enumerationEntityAttribute.name.toFirstUpper()»((«enumerationEntityAttribute.type.type») value);
		}
		«ENDIF»
		'''

	def genericVOSetter(Entity entity) '''
		public void set(java.lang.String name, java.lang.Object value) {
		
			getMetadata().addChange(name, value);
		
			«FOR attribute : entity.attributes»
			if ("«attribute.name»".equals(name))
			{
				«attribute.genericVOSetterValue»
				return;
			}
			«ENDFOR»
			
			«IF entity.entityHierarchical»
			if («typeof(IHierarchicalVO).name».FIELD_PARENT.getAttributeName().equals(name))
			{
				setParent((«typeof(IHierarchicalVO).name») value);
				return;
			}
			«ENDIF»
		
			«FOR infoVOEntityAttribute : infoVOEntityAttributes()»
				if ("«infoVOEntityAttribute.attributeName.attributeName»".equals(name))
				{
					«infoVOEntityAttribute.attributeName.setterName»(«infoVOEntityAttribute.attributeName.attributeName»);
					return;
				}
			«ENDFOR»
		
			super.set(name, value);
		}
	'''


	def compileGetAttributeDescriptors(Entity entity) {
		
		var extraAttributes = new ArrayList<AttributeGeneratorFactory.AttributeGenerator>
		 
		if (entity.entityHierarchical) {
			extraAttributes.addAll(hierarchicalVOAttributes())
		}

		compileGetAttributeDescriptors(entity, extraAttributes)
	}
	
	override getTypeUtils() {
		typeUtils
	}
	
	
}
