package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute

class AttributeUtils {

	@Inject
	extension NameUtils

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
