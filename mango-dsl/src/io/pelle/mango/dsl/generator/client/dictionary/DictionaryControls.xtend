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
import io.pelle.mango.dsl.mango.BaseDataType
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.DateEntityAttribute
import io.pelle.mango.dsl.mango.DecimalEntityAttribute
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
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EnumerationAttributeType
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.Labels
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute

class DictionaryControls {

	@Inject 
	extension DictionaryNameUtils

	@Inject 
	extension TypeUtils
	
	@Inject 
	extension AttributeUtils

	def dispatch dictionaryControlConstant(DictionaryControl dictionaryControl) '''
	public «dictionaryControl.dictionaryControlType» «dictionaryControl.dictionaryConstantName» = new «dictionaryControl.dictionaryControlType»("«ModelUtil.getControlName(dictionaryControl)»", this);
	'''
	
	def dispatch dictionaryControlConstant(DictionaryReferenceControl dictionaryControl) '''
	public «dictionaryControl.dictionaryClassFullQualifiedName» «dictionaryControl.dictionaryConstantName» = new «dictionaryControl.dictionaryClassFullQualifiedName»(this);
	'''

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
			«ENDIF»
			
			«IF dictionaryControl.baseControl.entityattribute != null»
			«dictionaryControl.datatypeLabelSetter(dictionaryControl.baseControl.entityattribute)»
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

	def dictionaryControlClass(DictionaryControl dictionaryControl) ''''''

	def dictionaryControlClass(DictionaryReferenceControl dictionaryControl) '''
	package «dictionaryControl.packageName»;
	
	public class «dictionaryControl.dictionaryClassName» extends «dictionaryControl.dictionaryControlType» {

		«FOR dictionaryLabelControl : dictionaryControl.labelcontrols»
			«dictionaryLabelControl.dictionaryControlConstant»
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
	
	//-------------------------------------------------------------------------
	// DictionaryBaseControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryControl dictionaryControl) '''
	«BaseControlModel.name»<«IBaseControl.name»>
	'''
	
	def dispatch dictionaryControlConstantSetters(DictionaryControl dictionaryControl) ''''''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, BaseDataType baseDataType) '''
		«IF baseDataType != null && baseDataType.label != null» 
			«dictionaryControl.dictionaryConstantName».setLabel("«baseDataType.label»");
		«ENDIF»
	'''
	
	//-------------------------------------------------------------------------
	// DictionaryTextControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryTextControl dictionaryControl) '''
	«TextControlModel.name»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, StringEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
		
		«IF entityAttribute.type != null && entityAttribute.type.maxLength > 0» 
			«dictionaryControl.dictionaryConstantName».setMaxLength(«entityAttribute.type.maxLength»);
		«ENDIF»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryTextControl dictionaryControl) '''
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
	
	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, IntegerEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''
	
	def dispatch String dictionaryControlConstantSetters(DictionaryIntegerControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryLongControl
	//-------------------------------------------------------------------------
	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, LongEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''
	
	//-------------------------------------------------------------------------
	// DictionaryLongControl
	//-------------------------------------------------------------------------
	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, MapEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''
	
	//-------------------------------------------------------------------------
	// DictionaryBigDecimalControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryBigDecimalControl dictionaryControl) '''
	«BigDecimalControlModel.name»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, DecimalEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryBigDecimalControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryBooleanControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryBooleanControl dictionaryControl) '''
	«BooleanControlModel.name»
	'''
	
	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, BooleanEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryBooleanControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryDateControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryDateControl dictionaryControl) '''
	«DateControlModel.name»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, DateEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryDateControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryEnumerationControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryEnumerationControl dictionaryControl) '''
	«EnumerationControlModel.name»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, EnumerationEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type)»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, EnumerationAttributeType enumerationAttributeType) '''
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, EnumerationDataType enumerationDataType) '''
		«dictionaryControl.datatypeLabelSetter(enumerationDataType.baseDataType)»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryEnumerationControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryReferenceControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryReferenceControl dictionaryControl) '''
	«ReferenceControlModel»<«ModelUtil.getEntityAttribute(dictionaryControl).type»>
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryReferenceControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	
		«IF dictionaryControl.dictionary != null»
			«dictionaryControl.dictionaryConstantName».setDictionaryName("«dictionaryControl.dictionary.name»");
		«ENDIF»
	'''

	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, EntityEntityAttribute entityAttribute) '''
		//return dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)
	'''


	//-------------------------------------------------------------------------
	// DictionaryHierarchicalControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryHierarchicalControl dictionaryControl) '''
	«HierarchicalControlModel.name»<«IBaseControl.name»>
	'''
	
	def dispatch String dictionaryControlConstantSetters(DictionaryHierarchicalControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''
	

	//-------------------------------------------------------------------------
	// DictionaryFileControl
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryFileControl dictionaryControl) '''
	«FileControlModel.name»<«IBaseControl.name»>
	'''
	
	def dispatch String datatypeLabelSetter(DictionaryControl dictionaryControl, BinaryEntityAttribute entityAttribute) '''
		«dictionaryControl.datatypeLabelSetter(entityAttribute.type.baseDataType)»
	'''

	def dispatch String dictionaryControlConstantSetters(DictionaryFileControl dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	//-------------------------------------------------------------------------
	// DictionaryControlGroup
	//-------------------------------------------------------------------------
	def dispatch dictionaryControlType(DictionaryControlGroup dictionaryControl) '''
	«dictionaryControl.dictionaryClassName»
	'''
	
	def dispatch String dictionaryControlConstantSetters(DictionaryControlGroup dictionaryControl) '''
		«IF dictionaryControl.ref != null»
			«dictionaryControl.ref.dictionaryControlConstantSetters»
		«ENDIF»
		
		«dictionaryControl.dictionaryControlCommonSetters»
	'''

	def isMulti(DictionaryControlGroup dictionaryControlGroup) {
		return (dictionaryControlGroup.controlGroupOptions != null && dictionaryControlGroup.controlGroupOptions.multiFilterField)
	}

	def dictionaryControlClass(DictionaryControlGroup dictionaryControlGroup) '''
	
		package «dictionaryControlGroup.packageName»;
	
		public class «dictionaryControlGroup.dictionaryClassName» extends io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel {
	
			«FOR dictionaryControl : dictionaryControlGroup.groupcontrols»
				«dictionaryControl.dictionaryControlConstant»
			«ENDFOR»
	
			public «dictionaryControlGroup.dictionaryClassName»(String name, io.pelle.mango.client.base.modules.dictionary.model.IBaseModel parent) {
				super(name, parent, «dictionaryControlGroup.isMulti»);
				
				«FOR dictionaryControl : dictionaryControlGroup.groupcontrols»
					this.getControls().add(«dictionaryControl.dictionaryConstantName»);
				«ENDFOR»
				
				«FOR dictionaryControl : dictionaryControlGroup.groupcontrols»
					«dictionaryControl.dictionaryControlConstantSetters»
				«ENDFOR»
				
			}
		}
	'''
	

}