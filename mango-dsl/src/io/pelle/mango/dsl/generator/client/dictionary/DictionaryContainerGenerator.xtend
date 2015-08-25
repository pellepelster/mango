/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.dictionary

import com.google.inject.Inject
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel
import io.pelle.mango.client.base.modules.dictionary.model.containers.EditableTableModel
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.mango.ColumnLayout
import io.pelle.mango.dsl.mango.ColumnLayoutData
import io.pelle.mango.dsl.mango.DictionaryComposite
import io.pelle.mango.dsl.mango.DictionaryContainer
import io.pelle.mango.dsl.mango.DictionaryContainerContent
import io.pelle.mango.dsl.mango.DictionaryControl
import io.pelle.mango.dsl.mango.DictionaryControlGroup
import io.pelle.mango.dsl.mango.DictionaryEditableTable
import io.pelle.mango.dsl.mango.DictionaryTabFolder
import io.pelle.mango.dsl.query.EntityQuery
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess
import com.google.common.collect.Collections2
import com.google.common.base.Function
import java.util.Collection
import io.pelle.mango.dsl.mango.DictionaryReferenceList

class DictionaryContainerGenerator {

	@Inject
	extension DictionaryNameUtils

	@Inject
	extension DictionaryControls

	private Function<DictionaryComposite, DictionaryContainerContent> CONTAINER_CONTENTS = new Function<DictionaryComposite, DictionaryContainerContent>() {
			
			override apply(DictionaryComposite input) {
				return input
			}
			
		}

	def dictionaryConstant(DictionaryContainer dictionaryContainer) '''
		public «dictionaryContainer.dictionaryClassFullQualifiedName» «dictionaryContainer.dictionaryConstantName» = new «dictionaryContainer.dictionaryClassFullQualifiedName»(this);
	'''

	def dispatch dictionaryClass(List<DictionaryContainerContent> dictionaryContainerContents) '''
		«FOR dictionaryContainer : dictionaryContainerContents.filter(DictionaryContainer)»
			«dictionaryContainer.dictionaryConstant»
		«ENDFOR»
		
		«FOR dictionaryControl : dictionaryContainerContents.filter(DictionaryControl)»
			«dictionaryControl.dictionaryControlConstant»
		«ENDFOR»
	'''

	def dictionaryContainerContentsConstructor(Collection<DictionaryContainerContent> dictionaryContainerContents) '''
		«FOR dictionaryContainer : dictionaryContainerContents.filter(DictionaryContainer)»
			this.getChildren().add(«dictionaryContainer.dictionaryConstantName»);
		«ENDFOR»
		
		«FOR dictionaryControl : dictionaryContainerContents.filter(DictionaryControl)»
			this.getControls().add(«dictionaryControl.dictionaryConstantName»);
		«ENDFOR»
		
		«FOR dictionaryControl : dictionaryContainerContents.filter(DictionaryControl)»
			«dictionaryControl.dictionaryControlConstantSetters»
		«ENDFOR»
		
	'''

	def dictionaryContainerContentsConstants(List<DictionaryContainerContent> dictionaryContainerContents) '''
		«FOR dictionaryContainer : dictionaryContainerContents.filter(DictionaryContainer)»
			public «dictionaryContainer.dictionaryClassFullQualifiedName» «dictionaryContainer.dictionaryConstantName» = rootComposite.«dictionaryContainer.dictionaryConstantName»;
		«ENDFOR»
		
		«FOR dictionaryControl : dictionaryContainerContents.filter(DictionaryControl)»
			public «dictionaryControl.dictionaryControlType» «dictionaryControl.dictionaryConstantName» = rootComposite.«dictionaryControl.dictionaryConstantName»;
		«ENDFOR»
	'''

	def dispatch dictionaryGenerator(DictionaryComposite dictionaryContainer, IFileSystemAccess fsa) {
		dictionaryContainer.containercontents.dictionaryGenerator(fsa)
		fsa.generateFile(dictionaryContainer.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryContainer.dictionaryClass)
	}

	def dispatch dictionaryGenerator(DictionaryReferenceList dictionaryContainer, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryContainer.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryContainer.dictionaryClass)
	}

	def dispatch dictionaryGenerator(DictionaryFileList dictionaryContainer, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryContainer.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryContainer.dictionaryClass)
	}

	def dispatch dictionaryGenerator(DictionaryTabFolder dictionaryContainer, IFileSystemAccess fsa) {
		dictionaryContainer.tabs.dictionaryGenerator(fsa)
		fsa.generateFile(dictionaryContainer.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryContainer.dictionaryClass)
	}

	def layoutSetter(ColumnLayout columnLayout, ColumnLayoutData columnLayoutData) {
		layoutSetter(columnLayout, columnLayoutData, "")
	}

	def layoutSetter(ColumnLayout columnLayout, ColumnLayoutData columnLayoutData, String setterPrefix) '''
	
		«IF columnLayoutData != null»
			«setterPrefix»setLayoutData(new io.pelle.mango.client.base.modules.dictionary.model.ColumnLayoutData(«columnLayoutData.columnspan»));
		«ENDIF»

		«IF columnLayout != null»
			«setterPrefix»setLayout(new io.pelle.mango.client.base.modules.dictionary.model.ColumnLayout(«columnLayout.columns»));
		«ENDIF»
	'''

	def dispatch dictionaryClass(DictionaryContainer dictionaryContainer) {
		throw new RuntimeException("not implemented")
	} 

	def dispatch dictionaryClass(DictionaryComposite dictionaryContainer) '''
			package «dictionaryContainer.packageName»;
			
			@«SuppressWarnings.name»("all")
			public class «dictionaryContainer.dictionaryClassName» extends io.pelle.mango.client.base.modules.dictionary.model.containers.CompositeModel {
				
				«dictionaryContainer.containercontents.dictionaryClass»
		
				public «dictionaryContainer.dictionaryClassName»(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
					super("«dictionaryContainer.name»", parent);
					«dictionaryContainer.containercontents.dictionaryContainerContentsConstructor»
					
					«layoutSetter(dictionaryContainer.layout, dictionaryContainer.layoutdata)»
				}
			}
	'''
	
	def dispatch dictionaryClass(DictionaryReferenceList dictionaryContainer) '''
			package «dictionaryContainer.packageName»;
			
			@«SuppressWarnings.name»("all")
			public class «dictionaryContainer.dictionaryClassName» extends io.pelle.mango.client.base.modules.dictionary.model.containers.ReferenceListModel {
				
				public «dictionaryContainer.dictionaryClassName»(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
					super("«dictionaryContainer.name»", parent);
					
					setAttributePath("«dictionaryContainer.entityattribute.name»");
				}
			}
	'''

	def dispatch dictionaryClass(DictionaryTabFolder dictionaryContainer) '''
			package «dictionaryContainer.packageName»;
			
			@«SuppressWarnings.name»("all")
			public class «dictionaryContainer.dictionaryClassName» extends io.pelle.mango.client.base.modules.dictionary.model.containers.TabFolderModel {
				
				/**
				* tabs «dictionaryContainer.tabs.length»
				*/
				«dictionaryContainer.tabs.dictionaryClass»
		
				public «dictionaryContainer.dictionaryClassName»(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
					super("«dictionaryContainer.name»", parent);
					«Collections2.transform(dictionaryContainer.tabs, CONTAINER_CONTENTS).dictionaryContainerContentsConstructor»
				}
			}
	'''

	def dispatch dictionaryClass(DictionaryFileList dictionaryContainer) '''
			package «dictionaryContainer.packageName»;
			
			@«SuppressWarnings.name»("all")
			public class «dictionaryContainer.dictionaryClassName» extends «FileListModel.name» {
				
				public «dictionaryContainer.dictionaryClassName»(«BaseModel.name»<?> parent) {
					super("«dictionaryContainer.name»", parent);
					setAttributePath("«dictionaryContainer.entityattribute.name»");
				}
			}
	'''
	
	def dispatch dictionaryGenerator(DictionaryEditableTable dictionaryContainer, IFileSystemAccess fsa) {
		dictionaryContainer.containercontents.dictionaryGenerator(fsa)
		fsa.generateFile(dictionaryContainer.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryContainer.dictionaryClass)
	}

	def dispatch dictionaryClass(DictionaryEditableTable dictionaryContainer) '''
			package «dictionaryContainer.packageName»;
			
			@«SuppressWarnings.name»("all")
			public class «dictionaryContainer.dictionaryClassName» extends «EditableTableModel.name» {
		
				«FOR dictionaryControl : dictionaryContainer.columncontrols»
					«dictionaryControl.dictionaryControlConstant»
				«ENDFOR»
		
				public «dictionaryContainer.dictionaryClassName»(«BaseModel.name»<?> parent) {
					
					super("«dictionaryContainer.name»", parent);
		
					«FOR dictionaryControl : dictionaryContainer.columncontrols»
						this.getControls().add(«dictionaryControl.dictionaryConstantName»);
						«dictionaryControl.dictionaryControlConstantSetters»
					«ENDFOR»
		
					setVoName(«EntityQuery.getEntity(dictionaryContainer.entityattribute).voFullQualifiedName».class);
					setAttributePath("«dictionaryContainer.entityattribute.name»");
				}
			}
	'''

	def dispatch dictionaryGenerator(Collection<DictionaryContainerContent> dictionaryContainerContents, IFileSystemAccess fsa) {

		for (dictionaryContainer : dictionaryContainerContents.filter(DictionaryContainer)) {
			dictionaryContainer.dictionaryGenerator(fsa)
		}

		for (dictionaryControl : dictionaryContainerContents.filter(DictionaryControlGroup)) {
			fsa.generateFile(dictionaryControl.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryControl.dictionaryControlClass)
		}
	}
}
