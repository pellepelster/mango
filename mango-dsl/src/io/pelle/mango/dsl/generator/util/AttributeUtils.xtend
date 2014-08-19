package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.AttributeDescriptor
import io.pelle.mango.dsl.emf.EmfModelQuery
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute

class AttributeUtils {

	@Inject
	extension NameUtils

	//-------------------------------------------------------------------------
	// natural key
	//-------------------------------------------------------------------------
	def boolean isNaturalKeyAttribute(EntityAttribute entityAttribute) {
		return entityAttribute.parentEntity.naturalKeyAttributes.contains(entityAttribute)
	}
	
	def naturalKeyOrder(EntityAttribute entityAttribute) {
		if (entityAttribute.naturalKeyAttribute) {
			return EmfModelQuery.createEObjectQuery(entityAttribute).getParentByType(Entity).match.naturalKeyAttributes.
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

	def attribute(String attributeType, String attributeName) '''
		«attribute(attributeType, attributeName, null)»
	'''

	def attribute(String attributeType, String attributeName, String attributeInitializer) '''
		private «attributeType» «attributeName.attributeName»«IF attributeInitializer != null» = «attributeInitializer»«ENDIF»;
	'''

	def getter(String attributeType, String attributeName) '''
		public «attributeType» get«attributeName.toFirstUpper»() {
			return this.«attributeName»;
		}
	'''

	def setter(String attributeType, String attributeName) '''
		public void set«attributeName.toFirstUpper»(«attributeType» «attributeName») {
			this.«attributeName» = «attributeName»;
		}
	'''

	def changeTrackingSetter(String attributeType, String attributeName) '''
		public void set«attributeName.toFirstUpper»(«attributeType» «attributeName») {
			getChangeTracker().addChange("«attributeName»", «attributeName»);
			this.«attributeName» = «attributeName»;
		}
	'''

}
