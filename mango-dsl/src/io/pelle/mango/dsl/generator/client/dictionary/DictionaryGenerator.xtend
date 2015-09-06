package io.pelle.mango.dsl.generator.client.dictionary

import com.google.inject.Inject
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModel
import io.pelle.mango.client.base.modules.dictionary.model.containers.CompositeModel
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel
import io.pelle.mango.client.base.modules.dictionary.model.search.FilterModel
import io.pelle.mango.client.base.modules.dictionary.model.search.ResultModel
import io.pelle.mango.client.base.modules.dictionary.model.search.SearchModel
import io.pelle.mango.dsl.emf.EmfModelQuery
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.server.EntityUtils
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.DictionaryContainer
import io.pelle.mango.dsl.mango.DictionaryEditor
import io.pelle.mango.dsl.mango.DictionaryFilter
import io.pelle.mango.dsl.mango.DictionaryResult
import io.pelle.mango.dsl.mango.DictionarySearch
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import org.eclipse.xtext.generator.IFileSystemAccess
import io.pelle.mango.dsl.mango.DictionaryContainerContent
import io.pelle.mango.dsl.mango.DictionaryComposite
import io.pelle.mango.dsl.mango.DictionaryControl

class DictionaryGenerator {

	@Inject
	extension DictionaryNameUtils

	@Inject
	extension DictionaryControls

	@Inject
	extension EntityUtils

	@Inject
	extension DictionaryContainerGenerator

	def dictionaryGenerator(Model model, IFileSystemAccess fsa) {

		for (dictionary : model.eAllContents.toIterable.filter(Dictionary)) {
			dictionary.dictionaryGenerator(fsa)
		}

		fsa.generateFile(model.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, model.dictionaryClass)
	}

	def dictionaryClass(Model model) '''
		
		package «model.modelPackageName»;
		
		@«SuppressWarnings.name»("all")
		public class «model.dictionaryClassName» {
		
			private «model.dictionaryClassName»() {
			}
		
			«FOR dictionary : model.eAllContents.toIterable.filter(Dictionary)»
			«dictionary.dictionaryConstant»;
			«ENDFOR»
		
		}
	'''
	
	def dictionaryI18N(Model model) '''
		«FOR dictionary : model.eAllContents.toIterable.filter(Dictionary)»
			«dictionary.dictionaryI18N»
		«ENDFOR»
	'''

	def dictionaryI18N(Dictionary dictionary) '''
		«dictionary.name.toLowerCase».label=«dictionary.label»
		«dictionary.name.toLowerCase».pluralLabel=«dictionary.pluralLabel»
		«dictionary.dictionarysearch.dictionaryI18N(dictionary.name.toLowerCase)»
		«dictionary.dictionaryeditor.dictionaryI18N(dictionary.name.toLowerCase)»
	'''

	def dispatch dictionaryI18N(DictionarySearch dictionarySearch, String prefix) '''
		«prefix».«dictionarySearch.name.toLowerCase».label=«dictionarySearch.label»
		«dictionarySearch.dictionaryfilters.forEach[it.dictionaryI18N(prefix + "." + dictionarySearch.name.toLowerCase)]»
	'''

	def dispatch dictionaryI18N(DictionaryEditor dictionaryEditor, String prefix) '''
		«prefix».«dictionaryEditor.name.toLowerCase».label=«dictionaryEditor.label»
		«dictionaryEditor.containercontents.forEach[it.dictionaryI18N(prefix + "." + dictionaryEditor.name.toLowerCase)]»
	'''

	def dispatch dictionaryI18N(DictionaryFilter dictionaryFilter, String prefix) '''
		«dictionaryFilter.containercontents.forEach[it.dictionaryI18N(prefix + "." + dictionaryFilter.name.toLowerCase)]»
	'''

	def dispatch dictionaryI18N(DictionaryContainerContent dictionaryContainerContent, String prefix) '''
	'''

	def dispatch dictionaryI18N(DictionaryComposite dictionaryComposite, String prefix) '''
		«dictionaryComposite.containercontents.forEach[it.dictionaryI18N(prefix + "." + dictionaryComposite.name.toLowerCase)]»
	'''

	def dispatch dictionaryI18N(DictionaryControl dictionaryControl, String prefix) '''
		«IF dictionaryControl.baseControl != null && dictionaryControl.baseControl.labels != null»
			«prefix».«dictionaryControl.name.toLowerCase».label=«dictionaryControl.baseControl.labels.label»
		«ENDIF»
	'''

	def dictionaryGenerator(Dictionary dictionary, IFileSystemAccess fsa) {

		fsa.generateFile(dictionary.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionary.dictionaryClass)

		for (dictionaryLabelControl : dictionary.labelcontrols) {
			fsa.generateFile(dictionaryLabelControl.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryLabelControl.dictionaryControlClass)
		}

		if (dictionary.dictionaryeditor != null) {
			dictionary.dictionaryeditor.dictionaryGenerator(fsa)
		}

		if (dictionary.dictionarysearch != null) {
			dictionary.dictionarysearch.dictionaryGenerator(dictionary.entity, fsa)
		}

	}

	def dictionaryGenerator(DictionaryEditor dictionaryEditor, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryEditor.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryEditor.dictionaryClass)
		dictionaryEditor.containercontents.dictionaryGenerator(fsa)
	}

	def dictionaryClass(DictionaryEditor dictionaryEditor) '''
		
		package «dictionaryEditor.packageName»;
		
		@«SuppressWarnings.name»("all")
		public class «dictionaryEditor.dictionaryClassName» extends «EditorModel.name»<«EmfModelQuery.createEObjectQuery(dictionaryEditor).getParentByType(Dictionary).match.entity.
			voFullQualifiedName»> {
		
			private class RootComposite extends «CompositeModel.name» {
		
				«dictionaryEditor.containercontents.dictionaryClass»
		
				public RootComposite(«BaseModel.name»<?> parent) {
					super("«ICompositeModel.ROOT_COMPOSITE_NAME»", parent);
					«dictionaryEditor.containercontents.dictionaryContainerContentsConstructor»
				}
			}
			
			private RootComposite rootComposite = new RootComposite(this);
		
			public «dictionaryEditor.dictionaryClassName»(«BaseModel.name»<?> parent) {
				super("«dictionaryEditor.name»", parent);
			
				«IF dictionaryEditor.label != null»
					setLabel("«dictionaryEditor.label»");
				«ENDIF»
				
				setCompositeModel(rootComposite);
				
				«layoutSetter(dictionaryEditor.layout, dictionaryEditor.layoutdata, "rootComposite.")»
			}
			«dictionaryEditor.containercontents.dictionaryContainerContentsConstants»
		}
	'''

	def dictionaryClass(Dictionary dictionary) '''
		package «dictionary.packageName»;
		
		@«SuppressWarnings.name»("all")
		public class «dictionary.dictionaryClassName()» extends «DictionaryModel.name» {
		
			«FOR dictionaryControl : dictionary.labelcontrols»
				«dictionaryControl.dictionaryControlConstant»
			«ENDFOR»
		
			«IF dictionary.dictionaryeditor != null»
			«dictionary.dictionaryeditor.dictionaryConstant»
			«ENDIF»
		
			«IF dictionary.dictionarysearch != null»
			«dictionary.dictionarysearch.dictionaryConstant»
			«ENDIF»
		
			public «dictionary.dictionaryClassName»() {
			
				super("«dictionary.name»", null);
		
				setVoName(«dictionary.entity.voFullQualifiedName».class);
				
				«FOR labelcontrol : dictionary.labelcontrols»
				«labelcontrol.dictionaryControlConstantSetters»
				this.getLabelControls().add(«dictionaryConstantName(labelcontrol)»);
				«ENDFOR»
				
				«IF dictionary.dictionaryeditor != null»
				setEditorModel(«dictionary.dictionaryeditor.dictionaryConstantName»);
				«ENDIF»
		
				«IF dictionary.dictionarysearch != null»
				// dictionary search '«dictionary.dictionarysearch.name»'
				setSearchModel(«dictionary.dictionarysearch.dictionaryConstantName»);
				«ENDIF»
				
				«IF dictionary.label != null»
				setLabel("«dictionary.label»");
				«ELSEIF dictionary.entity != null && dictionary.entity.hasLabel»
				setLabel("«dictionary.entity.label»");
				«ENDIF»
		
				«IF dictionary.pluralLabel != null»
				setPluralLabel("«dictionary.pluralLabel»");
				«ELSEIF dictionary.entity != null && dictionary.entity.hasPluralLabel»
				setPluralLabel("«dictionary.entity.pluralLabel»");
				«ENDIF»
			}
		}
	'''

	def dictionaryFilterGenerator(DictionaryFilter dictionaryFilter, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryFilter.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryFilter.dictionaryClass)
		dictionaryFilter.containercontents.dictionaryGenerator(fsa)
	}

	def dictionaryResultGenerator(DictionaryResult dictionaryResult, Entity entity, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryResult.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionaryResult.dictionaryClass(entity))

	}

	def dictionaryGenerator(DictionarySearch dictionarySearch, Entity entity, IFileSystemAccess fsa) {

		fsa.generateFile(dictionarySearch.dictionaryClassFullQualifiedFileName, GeneratorConstants.CLIENT_GWT_GEN_OUTPUT, dictionarySearch.dictionaryClass)

		dictionarySearch.dictionaryresult.dictionaryResultGenerator(entity, fsa)

		for (dictionaryfilter : dictionarySearch.dictionaryfilters) {
			dictionaryfilter.dictionaryFilterGenerator(fsa)
		}

	}

	def dictionaryClass(DictionarySearch dictionarySearch) '''
		
		package «dictionarySearch.packageName»;
		
		@«SuppressWarnings.name»("all")
		public class «dictionarySearch.dictionaryClassName» extends «SearchModel.name»<«EmfModelQuery.createEObjectQuery(dictionarySearch).getParentByType(Dictionary).match.entity.
			voFullQualifiedName»> {
			
			«dictionarySearch.dictionaryresult.dictionaryConstant»
		
			«FOR dictionaryFilter : dictionarySearch.dictionaryfilters»
			«dictionaryFilter.dictionaryConstant»
			«ENDFOR»
			
			public «dictionarySearch.dictionaryClassName»(«BaseModel.name»<?> parent) {
			super("«dictionarySearch.name»", parent);
			
			«IF dictionarySearch.label != null»
				setLabel("«dictionarySearch.label»");
			«ENDIF»
			
				setResultModel(«dictionarySearch.dictionaryresult.dictionaryConstantName»);
		
				«FOR dictionaryfilter : dictionarySearch.dictionaryfilters»
				getFilterModels().add(«dictionaryfilter.dictionaryConstantName»);
				«ENDFOR»
				
			}
			
		}
	'''

	def dictionaryClass(DictionaryResult dictionaryResult, Entity entity) '''
		package «dictionaryResult.packageName»;
		
		@«SuppressWarnings.name»("all")
		public class «dictionaryResult.dictionaryClassName» extends «ResultModel.name»<«entity.voFullQualifiedName»> {
		
			«FOR dictionaryControl : dictionaryResult.resultcolumns»
				«dictionaryControl.dictionaryControlConstant»
			«ENDFOR»		
			
			public «dictionaryResult.dictionaryClassName»(«BaseModel.name»<?> parent) {
				super("«dictionaryResult.name»", parent);
		
				«FOR dictionaryControl : dictionaryResult.resultcolumns»
				this.getControls().add(«dictionaryControl.dictionaryConstantName»);
				«ENDFOR»		
		
				«FOR dictionaryControl : dictionaryResult.resultcolumns»
			«dictionaryControl.dictionaryControlConstantSetters»
				«ENDFOR»		
				
			}
			
		}
	'''

	def dictionaryClass(DictionaryFilter dictionaryFilter) '''
		package «dictionaryFilter.packageName»;
		
		@«SuppressWarnings.name»("all")
		public class «dictionaryFilter.dictionaryClassName» extends «FilterModel.name» {
		
			private class RootComposite extends «CompositeModel.name» {
		
				«dictionaryFilter.containercontents.dictionaryClass»
		
				public RootComposite(«BaseModel.name»<?> parent) {
					super("«ICompositeModel.ROOT_COMPOSITE_NAME»", parent);
					«dictionaryFilter.containercontents.dictionaryContainerContentsConstructor»
				}
			}
			
			private RootComposite rootComposite = new RootComposite(this);
		
				public «dictionaryFilter.dictionaryClassName»(«BaseModel.name»<?> parent) {
				super("«dictionaryFilter.name»", parent);
				
				setCompositeModel(rootComposite);
				«layoutSetter(dictionaryFilter.layout, dictionaryFilter.layoutdata, "rootComposite.")»
			}
			
			«dictionaryFilter.containercontents.dictionaryContainerContentsConstants»
		}
	'''

}
