package io.pelle.mango.dsl.generator.client.dictionary

import com.google.inject.Inject
import io.pelle.mango.client.base.layout.IModuleUI
import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement
import io.pelle.mango.dsl.emf.EmfModelQuery
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.NavigationNode
import org.eclipse.xtext.generator.IFileSystemAccess

class DictionaryNavigationGenerator {

	@Inject 
	extension DictionaryNameUtils

	def dictionaryNavigationGenerator(Model model, IFileSystemAccess fsa) {

		if  (!model.eAllContents.toIterable.filter(typeof(NavigationNode)).empty) {
			
			for(navigationNode : model.eAllContents.toIterable.filter(typeof(NavigationNode))) {
				fsa.generateFile(navigationNode.navigationNodeClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, navigationNode.dictionaryNavigationNodeClass)
			}
			
			fsa.generateFile(model.navigationNodeClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, model.dictionaryNavigationRootNodeClass)
		}
	}


	def dictionaryNavigationRootNodeClass(Model model) '''
		package «model.modelPackageName»;
		
		public class «model.navigationNodeClassName» {
			public static class RootNavigationNode extends «NavigationTreeElement.name» {
			«FOR navigationNode : model.eAllContents.toIterable.filter(typeof(NavigationNode)).filter[!(it.eContainer instanceof NavigationNode)]»
			public «navigationNode.navigationyNodeClassFullQualifiedName» «navigationNode.navigationNodeConstantName» = new «navigationNode.navigationyNodeClassFullQualifiedName»();
			«ENDFOR»
		
				public RootNavigationNode() {
					super("ROOT");

					«FOR navigationNode : model.eAllContents.toIterable.filter(typeof(NavigationNode)).filter[!(it.eContainer instanceof NavigationNode)]»
					getChildren().add(«navigationNode.navigationNodeConstantName»);
					«ENDFOR»

					setLabel(RootNavigationNode.class.getName());
				}
			}
		
			public static RootNavigationNode ROOT = new RootNavigationNode();
		
		}
	'''

	def dictionaryNavigationNodeClass(NavigationNode navigationNode) '''

	package «navigationNode.packageName»;
	
	public class «navigationNode.navigationNodeClassName» extends «NavigationTreeElement.name» {


		«FOR childNavigationNode : navigationNode.eContents.filter(NavigationNode)»
		public «childNavigationNode.navigationyNodeClassFullQualifiedName» «childNavigationNode.navigationNodeConstantName» = new «childNavigationNode.navigationyNodeClassFullQualifiedName»();
		«ENDFOR»

		public «navigationNode.navigationNodeClassName»() {
			super("«navigationNode.name»");

			«FOR childNavigationNode : navigationNode.eContents.filter(NavigationNode)»
				getChildren().add(«childNavigationNode.navigationNodeConstantName»);
			«ENDFOR»
			
			setLabel("«navigationNode.name»");
			
			«IF navigationNode.module != null»
			setModuleLocator(«IModuleUI.UI_MODULE_ID_PARAMETER_NAME» + "=«navigationNode.module.name»");
			«ELSEIF navigationNode.dictionaryEditor != null»
			setModuleLocator("«IModuleUI.UI_MODULE_ID_PARAMETER_NAME»=DictionaryEditor&" + io.pelle.mango.client.modules.BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID + "=«EmfModelQuery.createEObjectQuery(navigationNode.dictionaryEditor).getParentByType(Dictionary).match.name»");
			«ELSEIF navigationNode.dictionarySearch != null»
			setModuleLocator("«IModuleUI.UI_MODULE_ID_PARAMETER_NAME»=DictionarySearch&" + io.pelle.mango.client.modules.BaseDictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID + "=«EmfModelQuery.createEObjectQuery(navigationNode.dictionarySearch).getParentByType(Dictionary).match.name»");
			«ELSEIF navigationNode.name != null»
			setModuleLocator("«IModuleUI.UI_MODULE_ID_PARAMETER_NAME»=ModuleNavigationOverview&" + io.pelle.mango.client.modules.BaseModuleNavigationModule.NAVIGATIONTREEELEMENTNAME_PARAMETER_ID + "=«navigationNode.name»");
			«ENDIF»
		}
	
	}
	'''

}