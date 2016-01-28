/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.dictionary

import com.google.common.base.CaseFormat
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.DictionaryContainer
import io.pelle.mango.dsl.mango.DictionaryControl
import io.pelle.mango.dsl.mango.DictionaryCustomComposite
import io.pelle.mango.dsl.mango.DictionaryEditor
import io.pelle.mango.dsl.mango.DictionaryFilter
import io.pelle.mango.dsl.mango.DictionaryResult
import io.pelle.mango.dsl.mango.DictionarySearch
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.NavigationNode

class DictionaryNameUtils extends ClientNameUtils {

	def camelCaseToUnderScore(String camelCase) {
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
	}

	def constantName(String constant) {
		return constant.camelCaseToUnderScore.toUpperCase
	}
	
	//-------------------------------------------------------------------------
	// Model
	//-------------------------------------------------------------------------
	def dictionaryClassName(Model model) {
		return model.modelName.toFirstUpper() + "DictionaryModel";
	}

	def dictionaryClassFullQualifiedName(Model model) {
		return model.modelPackageName + '.' + model.dictionaryClassName;
	}

	//-------------------------------------------------------------------------
	// Dictionary
	//-------------------------------------------------------------------------
	def dictionaryClassName(Dictionary dictionary) {
		return dictionary.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(Dictionary dictionary) {
		return dictionary.packageName + '.' + dictionary.dictionaryClassName;
	}

	def dictionaryConstantName(Dictionary dictionary) {
		dictionary.name.constantName
	}
	
	def dispatch dictionaryConstant(Dictionary dictionary) '''
	public static final «dictionary.dictionaryClassFullQualifiedName» «dictionary.dictionaryConstantName» = new «dictionary.dictionaryClassFullQualifiedName»();
	'''

	//-------------------------------------------------------------------------
	// NavigationNode
	//-------------------------------------------------------------------------
	def navigationNodeClassName(NavigationNode navigationNode) {
		return navigationNode.name.toFirstUpper;
	}

	def navigationyNodeClassFullQualifiedName(NavigationNode navigationNode) {
		return navigationNode.packageName + '.' + navigationNode.navigationNodeClassName;
	}

	def navigationNodeConstantName(NavigationNode navigationNode) {
		navigationNode.name.constantName
	}

	//-------------------------------------------------------------------------
	// Navigation
	//-------------------------------------------------------------------------
	def navigationNodeClassName(Model model) {
		return model.modelName.toFirstUpper + "NavigationModel";
	}

	def navigationyNodeClassFullQualifiedName(Model model) {
		return model.modelPackageName + '.' + model.navigationNodeClassName;
	}

	//-------------------------------------------------------------------------
	// DictionaryEditor
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionaryEditor dictionaryEditor) {
		return dictionaryEditor.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionaryEditor dictionaryEditor) {
		return dictionaryEditor.packageName + '.' + dictionaryEditor.dictionaryClassName;
	}

	def dictionaryConstantName(DictionaryEditor dictionaryEditor) {
		dictionaryEditor.name.constantName
	}
	
	def dispatch dictionaryConstant(DictionaryEditor dictionaryEditor) '''
	public «dictionaryEditor.dictionaryClassFullQualifiedName» «dictionaryEditor.dictionaryConstantName» = new «dictionaryEditor.dictionaryClassFullQualifiedName»(this);
	'''

	//-------------------------------------------------------------------------
	// DictionarySearch
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionarySearch dictionarySearch) {
		return dictionarySearch.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionarySearch dictionarySearch) {
		return dictionarySearch.packageName + '.' + dictionarySearch.dictionaryClassName;
	}

	def dictionaryConstantName(DictionarySearch dictionarySearch) {
		dictionarySearch.name.constantName
	}

	def dispatch dictionaryConstant(DictionarySearch dictionarySearch) '''
	public «dictionarySearch.dictionaryClassFullQualifiedName» «dictionarySearch.dictionaryConstantName» = new «dictionarySearch.dictionaryClassFullQualifiedName»(this);
	'''
	
	//-------------------------------------------------------------------------
	// DictionaryFilter
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionaryFilter dictionaryFilter) {
		return dictionaryFilter.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionaryFilter dictionaryFilter) {
		return dictionaryFilter.packageName + '.' + dictionaryFilter.dictionaryClassName;
	}

	def dictionaryConstantName(DictionaryFilter dictionaryFilter) {
		dictionaryFilter.name.constantName
	}

	def dispatch dictionaryConstant(DictionaryFilter dictionaryFilter) '''
	public «dictionaryFilter.dictionaryClassFullQualifiedName» «dictionaryFilter.dictionaryConstantName» = new «dictionaryFilter.dictionaryClassFullQualifiedName»(this);
	'''
	
	//-------------------------------------------------------------------------
	// DictionaryResult
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionaryResult  dictionaryResult) {
		return dictionaryResult.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionaryResult  dictionaryResult) {
		return dictionaryResult.packageName + '.' + dictionaryResult.dictionaryClassName;
	}

	def dictionaryConstantName(DictionaryResult  dictionaryResult) {
		dictionaryResult.name.constantName
	}
	
	def dispatch dictionaryConstant(DictionaryResult dictionaryResult) '''
	public «dictionaryResult.dictionaryClassFullQualifiedName» «dictionaryResult.dictionaryConstantName» = new «dictionaryResult.dictionaryClassFullQualifiedName»(this);
	'''

	//-------------------------------------------------------------------------
	// DictionaryControl
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionaryControl dictionaryControl) {
		return dictionaryControl.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionaryControl dictionaryControl) {
		return dictionaryControl.packageName + '.' + dictionaryControl.dictionaryClassName;
	}

	def dictionaryConstantName(DictionaryControl dictionaryControl) {
		ModelUtil.getControlName(dictionaryControl).constantName
	}

	//-------------------------------------------------------------------------
	// DictionaryContainer
	//-------------------------------------------------------------------------
	def dictionaryClassName(DictionaryContainer dictionaryContainer) {
		return dictionaryContainer.name.toFirstUpper();
	}

	def dictionaryClassFullQualifiedName(DictionaryContainer dictionaryContainer) {
		return dictionaryContainer.packageName + '.' + dictionaryContainer.dictionaryClassName;
	}

	def dictionaryConstantName(DictionaryContainer dictionaryContainer) {
		dictionaryContainer.name.constantName
	}
	
	//-------------------------------------------------------------------------
	// DictionaryCustomComposite
	//-------------------------------------------------------------------------
	def customCompositePackageName(DictionaryCustomComposite dictionaryContainer) {
		return ModelUtil.getRootModel(dictionaryContainer).modelPackageName + "." + GeneratorConstants.CLIENT_PACKAGE_POSTFIX;
	}
	
	def dictionaryCustomCompositeClassName(DictionaryCustomComposite dictionaryContainer) {
		return dictionaryContainer.type.toFirstUpper() + "Impl";
	}

	def dictionaryCustomCompositeClassFullQualifiedName(DictionaryCustomComposite dictionaryContainer) {
		return dictionaryContainer.customCompositePackageName + '.' + dictionaryContainer.dictionaryCustomCompositeClassName;
	}

	//-------------------------------------------------------------------------
	// DictionaryCustomComposite (GWT)
	//-------------------------------------------------------------------------

	def customCompositeGwtPackageName(DictionaryCustomComposite dictionaryContainer) {
		return ModelUtil.getRootModel(dictionaryContainer).modelPackageName +  "." + GeneratorConstants.CLIENT_PACKAGE_POSTFIX;
	}

	def dictionaryCustomCompositeGwtClassName(DictionaryCustomComposite dictionaryContainer) {
		return dictionaryContainer.type.toFirstUpper() + "GwtImpl";
	}

	def dictionaryCustomCompositeGwtClassFullQualifiedName(DictionaryCustomComposite dictionaryContainer) {
		return dictionaryContainer.customCompositeGwtPackageName + '.' + dictionaryContainer.dictionaryCustomCompositeGwtClassName;
	}

}
