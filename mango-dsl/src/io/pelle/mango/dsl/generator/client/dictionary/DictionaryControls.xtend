package io.pelle.mango.dsl.generator.client.dictionary

import com.google.inject.Inject
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl
import io.pelle.mango.client.base.modules.dictionary.model.controls.BaseControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.FileControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.HierarchicalControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.util.AttributeUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.DictionaryBigDecimalControl
import io.pelle.mango.dsl.mango.DictionaryBooleanControl
import io.pelle.mango.dsl.mango.DictionaryControl
import io.pelle.mango.dsl.mango.DictionaryControlGroup
import io.pelle.mango.dsl.mango.DictionaryDateControl
import io.pelle.mango.dsl.mango.DictionaryEnumerationControl
import io.pelle.mango.dsl.mango.DictionaryFileControl
import io.pelle.mango.dsl.mango.DictionaryHierarchicalControl
import io.pelle.mango.dsl.mango.DictionaryIntegerControl
import io.pelle.mango.dsl.mango.DictionaryReferenceControl
import io.pelle.mango.dsl.mango.DictionaryTextControl
import io.pelle.mango.dsl.mango.Labels

class DictionaryControls {

	@Inject 
	extension DictionaryNameUtils

	@Inject 
	extension TypeUtils
	
	@Inject 
	extension AttributeUtils

	def dictionaryControlClass(DictionaryControl dictionaryControl) ''''''

	def dispatch dictionaryControlType(DictionaryControl dictionaryControl) '''
	«BaseControlModel.name»<«IBaseControl.name»>
	'''
	def dispatch dictionaryControlConstantSetters(DictionaryControl dictionaryControl) ''''''

	def dispatch dictionaryConstant(DictionaryControl dictionaryControl) '''
	public «dictionaryControl.dictionaryControlType» «dictionaryControl.dictionaryConstantName» = new «dictionaryControl.dictionaryControlType»("«ModelUtil.getControlName(dictionaryControl)»", this);
	'''

/**	
	def dictionaryControlTypeSetters(DictionaryControl dictionaryControl, Type type) {
	}

«DEFINE dictionaryControlTypeSetters(DictionaryControl dictionaryControl) FOR DatatypeType»
	«IF this.type.baseDatatype.labels != null»
		«EXPAND dictionaryControlLabelSetters(this.type.baseDatatype.labels) FOR dictionaryControl»
	«ENDIF»
«ENDDEFINE»
	 */
	
	def dictionaryControlCommonSetters(DictionaryControl dictionaryControl) '''
		«IF dictionaryControl.baseControl != null»
		
			«dictionaryControl.dictionaryConstantName».setMandatory(«dictionaryControl.baseControl.mandatory.toString().toLowerCase()»);
			
			«IF dictionaryControl.baseControl.entityattribute != null»
				«dictionaryControl.dictionaryConstantName».setAttributePath("«dictionaryControl.baseControl.entityattribute.name»");
				
				«IF dictionaryControl.baseControl.entityattribute.naturalKeyAttribute»
					// natural key attribute
					«dictionaryControl.dictionaryConstantName».setMandatory(true);
				«ENDIF»
			«ENDIF»
		
			«IF dictionaryControl.baseControl.labels != null»
				«dictionaryControlLabelSetters(dictionaryControl, dictionaryControl.baseControl.labels)»
				//IF dictionaryControl.baseControl.entityattribute != null
				//	dictionaryControl.baseControl.entityattribute.dictionaryControlTypeSetters
				//ENDIF
			«ENDIF»
		«ENDIF»
	'''	
	def dictionaryControlLabelSetters(DictionaryControl dictionaryControl, Labels labels) '''
		«IF labels != null»
			«IF labels.label != null»
				«dictionaryControl.dictionaryConstantName».setLabel("«labels.label»");
			«ENDIF»
		
			«IF labels.columnLabel != null»
				«dictionaryControl.dictionaryConstantName».setColumnLabel("«labels.columnLabel»");
			«ENDIF»
		
			«IF labels.editorLabel != null»
				«dictionaryControl.dictionaryConstantName».setEditorLabel("«labels.editorLabel»");
			«ENDIF»
		
			«IF labels.filterLabel != null»
				«dictionaryControl.dictionaryConstantName».setFilterLabel("«labels.filterLabel»");
			«ENDIF»
		«ENDIF»
	'''

	//-------------------------------------------------------------------------
	// DictionaryTextControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryTextControl dictionaryControl) '''
	«TextControlModel.name»
	'''

	def dispatch dictionaryControlConstantSetters(DictionaryTextControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryIntegerControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryIntegerControl dictionaryControl) '''
	«IntegerControlModel.name»
	'''

	//-------------------------------------------------------------------------
	// DictionaryBigDecimalControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryBigDecimalControl dictionaryControl) '''
	«BigDecimalControlModel.name»
	'''

	//-------------------------------------------------------------------------
	// DictionaryBooleanControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryBooleanControl dictionaryControl) '''
	«BooleanControlModel.name»
	'''

	//-------------------------------------------------------------------------
	// DictionaryDateControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryDateControl dictionaryControl) '''
	«DateControlModel.name»
	'''

	//-------------------------------------------------------------------------
	// DictionaryEnumerationControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryEnumerationControl dictionaryControl) '''
	«EnumerationControlModel.name»
	'''

	//-------------------------------------------------------------------------
	// DictionaryReferenceControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryReferenceControl dictionaryControl) '''
	«ReferenceControlModel»<«ModelUtil.getEntityAttribute(dictionaryControl).type»>
	'''
	
	def  dispatch dictionaryConstant(DictionaryReferenceControl dictionaryControl) '''
	public «dictionaryControl.dictionaryClassFullQualifiedName» «dictionaryControl.dictionaryConstantName» = new «dictionaryControl.dictionaryClassFullQualifiedName»(this);
	'''

	def dictionaryControlClass(DictionaryReferenceControl dictionaryControl) '''
	package «dictionaryControl.packageName»;
	
	public class «dictionaryControl.dictionaryClassName» extends «dictionaryControl.dictionaryControlType» {

		«FOR dictionaryLabelControl : dictionaryControl.labelcontrols»
			«dictionaryLabelControl.dictionaryConstant»
		«ENDFOR»

		public «dictionaryControl.dictionaryClassName»(de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel<?> parent) {
			
			super("«ModelUtil.getControlName(dictionaryControl)»", parent);

			«FOR dictionaryLabelControl : dictionaryControl.labelcontrols»
				«dictionaryControl.dictionaryControlConstantSetters»
			«ENDFOR»

			«FOR dictionaryLabelControl : dictionaryControl.labelcontrols»
				this.getLabelControls().add(«dictionaryLabelControl.dictionaryConstantName»);
			«ENDFOR»

			
		}
	}
	'''
	
	def dispatch dictionaryControlConstantSetters(DictionaryReferenceControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	
		«IF dictionaryControl.dictionary != null»
			«dictionaryControl.dictionaryConstantName».setDictionaryName("«dictionaryControl.dictionary.name»");
		«ENDIF»
	'''

	//-------------------------------------------------------------------------
	// DictionaryHierarchicalControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryHierarchicalControl dictionaryControl) '''
	«HierarchicalControlModel.name»<«IBaseControl.name»>
	'''

	//-------------------------------------------------------------------------
	// DictionaryFileControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryFileControl dictionaryControl) '''
	«FileControlModel.name»<«IBaseControl.name»>
	'''

	//-------------------------------------------------------------------------
	// DictionaryControlGroup
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlConstantSetters(DictionaryControlGroup dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

}