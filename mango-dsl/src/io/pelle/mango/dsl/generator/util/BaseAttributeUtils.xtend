package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.AttributeDescriptor
import io.pelle.mango.dsl.emf.EmfModelQuery
import io.pelle.mango.dsl.generator.server.EntityUtils
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.mango.ValueObject

abstract class BaseAttributeUtils {

	@Inject
	extension NameUtils

	@Inject
	extension EntityUtils

	def TypeUtils getTypeUtils()

	// -------------------------------------------------------------------------
	// natural key
	// -------------------------------------------------------------------------
	def boolean hasNaturalKeyAttribute(EntityAttribute entityAttribute) {
		return entityAttribute.parentEntity.naturalKeyFields.contains(entityAttribute)
	}

	def naturalKeyOrder(EntityAttribute entityAttribute) {
		if (entityAttribute.hasNaturalKeyAttribute) {
			return EmfModelQuery.createEObjectQuery(entityAttribute).getParentByType(Entity).match.naturalKeyFields.
				indexOf(entityAttribute)
		} else {
			AttributeDescriptor.NO_NATURAL_KEY
		}

	}

	def Entity getParentEntity(EntityAttribute entityAttribute) {
		return entityAttribute.eContainer as Entity;
	}

	def attributeGetterSetter(String attributeType, String attributeName) '''
		«attribute(attributeType, attributeName)»
		«getter(attributeType, attributeName)»
		«setter(attributeType, attributeName)»
	'''

	def getterSetter(String attributeType, String attributeName) '''
		«getter(attributeType, attributeName)»
		«setter(attributeType, attributeName)»
	'''

	def attributeName(ValueObject valueObject) '''«valueObject.name.toFirstLower»'''

	def attribute(Class<?> type, String attributeName) '''
		«attribute(type.name, attributeName, null)»
	'''

	def attribute(String attributeType, String attributeName) '''
		«attribute(attributeType, attributeName, null)»
	'''

	def attribute(String attributeType, String attributeName,
		String attributeInitializer
	) '''
		private «attributeType» «attributeName.attributeName»«IF attributeInitializer != null» = «attributeInitializer»«ENDIF»;
	'''

	def getterName(String attributeName) '''get«attributeName.toFirstUpper»'''

	def setterName(String attributeName) '''set«attributeName.toFirstUpper»'''

	def getterName(EntityAttribute entityAttribute) '''«entityAttribute.name.getterName»'''

	def getter(Class<?> attributeType, String attributeName) {
		getter(attributeType.name, attributeName);
	}

	def getter(String attributeType, String attributeName) '''
		public «attributeType» «attributeName.getterName»() {
			return this.«attributeName»;
		}
	'''

	def setterName(EntityAttribute entityAttribute) '''«entityAttribute.name.setterName»'''

	def setter(Class<?> attributeType, String attributeName) {
		setter(attributeType.name, attributeName);
	}

	def setter(String attributeType, String attributeName) '''
		public void «attributeName.setterName»(«attributeType» «attributeName») {
			this.«attributeName» = «attributeName»;
		}
	'''

	def changeTrackingSetter(Class<?> attributeType, String attributeName) {
		changeTrackingSetter(attributeType.name, attributeName)
	}

	def changeTrackingSetter(String attributeType, String attributeName) '''
		public void set«attributeName.toFirstUpper»(«attributeType» «attributeName») {
			getMetadata().addChange("«attributeName»", «attributeName»);
			this.«attributeName» = «attributeName»;
		}
	'''

	def changeTrackingListSetter(String attributeType, String attributeName) '''
		public void set«attributeName.toFirstUpper»(«attributeType» «attributeName») {
			getMetadata().addChange("«attributeName»", «attributeName»);
			this.«attributeName».clear();
			this.«attributeName».addAll(«attributeName»);
		}
	'''

	def changeTrackingAttributeGetterSetterJpa(Class<?> attributeType, String attributeName, Entity entity) '''
		«typeUtils.compileEntityAttributeDescriptor(attributeType, attributeName, entity)»
		@Column(name = "«entity.entityTableColumnName(attributeName)»")
		«attribute(attributeType, attributeName)»
		«getter(attributeType, attributeName)»
		«changeTrackingSetter(attributeType, attributeName)»
	'''

	def changeTrackingAttributeGetterSetter(Class<?> attributeType, String attributeName, Entity entity) '''
		«typeUtils.compileEntityAttributeDescriptor(attributeType, attributeName, entity)»
		«attribute(attributeType, attributeName)»
		«getter(attributeType, attributeName)»
		«changeTrackingSetter(attributeType, attributeName)»
	'''

	def isEntityReference(EntityAttribute entityAttribute) {
		return entityAttribute instanceof EntityEntityAttribute
	}

	def isString(EntityAttribute entityAttribute) {
		return entityAttribute instanceof StringEntityAttribute
	}

	def Cardinality getCardinality(EntityAttribute entityAttribute) {

		if (entityAttribute instanceof EntityEntityAttribute) {
			return (entityAttribute as EntityEntityAttribute).cardinality
		} else if (entityAttribute instanceof StringEntityAttribute) {
			return (entityAttribute as StringEntityAttribute).cardinality
		} else {
			return null
		}
	}

	def hasCardinality(EntityAttribute entityAttribute) {
		getCardinality(entityAttribute) != null
	}

}
