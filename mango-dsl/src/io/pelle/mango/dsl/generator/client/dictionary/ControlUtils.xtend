package io.pelle.mango.dsl.generator.client.dictionary

import io.pelle.mango.dsl.mango.DictionaryControl
import io.pelle.mango.dsl.mango.DictionaryControlGroup
import io.pelle.mango.dsl.mango.DictionaryControlGroupOptionMultiFilterField
import io.pelle.mango.dsl.mango.DictionaryControlGroupOptionsContainer
import io.pelle.mango.dsl.mango.DictionaryIntegerControl
import io.pelle.mango.dsl.mango.DictionaryIntegerControlInputType
import io.pelle.mango.dsl.mango.MangoPackage

class ControlUtils {

	def hasWidth(DictionaryControl dictionaryControl) {
		return (dictionaryControl.baseControl != null && dictionaryControl.baseControl.width > 0)
	}

	def getWidth(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl.width
	}

	def getReadonly(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl != null && dictionaryControl.baseControl.readonly
	}

	def hasReadonly(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl != null && dictionaryControl.baseControl.eGet(MangoPackage.Literals.BASE_DICTIONARY_CONTROL__READONLY) != null
	}

	def getMandatory(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl != null && dictionaryControl.baseControl.mandatory
	}

	def hasMandatory(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl != null && dictionaryControl.baseControl.eGet(MangoPackage.Literals.BASE_DICTIONARY_CONTROL__MANDATORY) != null
	}

	def hasBaseControl(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl != null
	}

	def hasEntityAttribute(DictionaryControl dictionaryControl) {
		return dictionaryControl.hasBaseControl && dictionaryControl.baseControl.entityattribute != null
	}

	def getEntityAttribute(DictionaryControl dictionaryControl) {
		return dictionaryControl.baseControl.entityattribute
	}

	def <T> mulitFilterField(DictionaryControlGroup controlGroup) {
		return Boolean.TRUE.equals(controlGroup.getControlGroupOption(typeof(DictionaryControlGroupOptionMultiFilterField)))
	}

	// control group options
	def <T> getControlGroupOption(DictionaryControlGroupOptionsContainer controlGroupOptionsContainer, Class<T> controlGroupOptionType) {
		return controlGroupOptionsContainer.options.findFirst[e|controlGroupOptionType.isAssignableFrom(e.class)] as T
	}

	def <T> getControlGroupOption(DictionaryControlGroup controlGroup, Class<T> controlGroupOptionType) {
		if (controlGroup.controlGroupOptions != null) {
			return getControlGroupOption(controlGroup.controlGroupOptions, controlGroupOptionType)
		} else {
			return null;
		}
	}

	// integer control options
	def <T> getIntegerOption(DictionaryIntegerControl integerControl, Class<T> integerControlOptionType) {
		if (integerControl.options != null) {
			return integerControl.options.findFirst[e|integerControlOptionType.isAssignableFrom(e.class)] as T
		} else {
			return null;
		}
	}

	def <T> getIntegerControlInputType(DictionaryIntegerControl integerControl) {
		var options = integerControl.getIntegerOption(DictionaryIntegerControlInputType)

		if (options != null) {
			options.inputtype
		}
	}
}		
		
