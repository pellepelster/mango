package io.pelle.mango.dsl.generator.util

import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.DictionaryEditor
import io.pelle.mango.dsl.mango.DictionarySearch
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.ModuleDefinition
import io.pelle.mango.dsl.mango.PackageDeclaration
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ValueObject
import javax.management.Attribute
import org.eclipse.emf.ecore.EObject

class NameUtils {

	def String combinePackageName(String packageName1,String packageName2) {

		if (packageName1 != null && !packageName1.empty)
		{
			return packageName1 + "." + packageName2
		}
		else
		{
			return packageName2
		}
	}

	def dispatch String getPackageName(String packageName) {
		return packageName.toLowerCase.replaceAll("\\^", "");
	}
	
	def dispatch String getPackageName(Dictionary dictionary) {
		return combinePackageName(getPackageName(dictionary.eContainer), dictionary.name.packageName)
	}

	def dispatch String getPackageName(DictionaryEditor dictionaryEditor) {
		return combinePackageName(getPackageName(dictionaryEditor.eContainer), dictionaryEditor.name.packageName)
	}

	def dispatch String getPackageName(DictionarySearch dictionarySearch) {
		return combinePackageName(getPackageName(dictionarySearch.eContainer), dictionarySearch.name.packageName)
	}

	def dispatch String getPackageName(PackageDeclaration packageDeclaration) {
		return combinePackageName(getPackageName(packageDeclaration.eContainer), packageDeclaration.packageName.packageName)
	}

	def dispatch String getPackageName(EObject eObject) {
		if (eObject.eContainer == null)
		{
			return ""
		}
		else
		{
			return getPackageName(eObject.eContainer)
		}
	}

	def dispatch String getPackageName(Service service) {
		return combinePackageName(getPackageName(service.eContainer), service.name.packageName)
	}

	// entity
	def attributeName(Attribute attribute) {
		return attributeName(attribute.name)
	}

	// entity
	def attributeName(String attribute) {
		return attribute.toFirstLower
	}

	def attributeConstantName(String attribute) {
		return attribute.toUpperCase
	}

	def entityConstantName(Entity entity) {
		return entity.name.toUpperCase;
	}

	var String[] SQL_KEYWORDS = newArrayList( "order", "where", "select" )

	def entityTableName(Entity entity) {
		
		var entityTableName = entity.name.toLowerCase
		
		if (SQL_KEYWORDS.contains(entityTableName))
		{
			entityTableName = ModelUtil.getRootModel(entity).modelName.toLowerCase + '_' + entityTableName
		}
		
		return entityTableName
	}

	def entityTableIdColumnName(Entity entity) {
		return entity.name.toLowerCase + "_id"
	}

	def entityTableColumnName(EntityAttribute entityAttribute) {
		return GeneratorUtil.getParentEntity(entityAttribute).name.toLowerCase + "_" + entityAttribute.name.toLowerCase
	}

	def entityTableIdSequenceName(Entity entity) {
		return entity.name.toLowerCase + "_id_seq"
	}
	
	def gwtClientGeneratedModuleDefinitionFileName(Model model) {
		return model.modelName.toFirstUpper() + "Generated.gwt.xml";	
	}	

	def gwtClientModuleFullQualifiedFileName(Model model) {
		var nameUtils = new NameUtils
		nameUtils.modelPackageName(model).replaceAll("\\.", "/") + "/" + gwtClientGeneratedModuleDefinitionFileName(model);
	} 

	//-------------------------------------------------------------------------
	// value object
	//-------------------------------------------------------------------------
	def voName(ValueObject valueObject) {
		return valueObject.name.toFirstUpper;
	}

	def voFullQualifiedName(ValueObject valueObject) {
		return getPackageName(valueObject) + "." + voName(valueObject);
	}

	def voFullQualifiedFileName(ValueObject valueObject) {
		return voFullQualifiedName(valueObject).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// service 
	//-------------------------------------------------------------------------
	def serviceName(Service service) {
		return service.name.toFirstUpper;
	}
	
	def serviceSpringName(Service service) {
		return service.serviceName
	}

	

	//-------------------------------------------------------------------------
	// client configuration  
	//-------------------------------------------------------------------------
	def gwtClientconfigurationName(Model model) {
		return model.modelName.toFirstUpper + "ClientConfiguration";
	}

	def gwtClientconfigurationFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtClientconfigurationName(model);
	}

	def gwtClientconfigurationFullQualifiedNameFileName(Model model) {
		return gwtClientconfigurationFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// service interface  
	//-------------------------------------------------------------------------
	def serviceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper;
	}

	def serviceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + serviceInterfaceName(service);
	}

	def serviceInterfaceFullQualifiedFileName(Service service) {
		return serviceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// GWT service interface  
	//-------------------------------------------------------------------------
	def gwtServiceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper + "GWT";
	}

	def gwtServiceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtServiceInterfaceName(service);
	}

	def gwtServiceInterfaceFullQualifiedFileName(Service service) {
		return gwtServiceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// GWT Async interface  
	//-------------------------------------------------------------------------
	def gwtAsyncServiceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper + "GWTAsync";
	}

	def gwtAsyncServiceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtAsyncServiceInterfaceName(service);
	}

	def gwtAsyncServiceInterfaceFullQualifiedFileName(Service service) {
		return gwtAsyncServiceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// GWT Async interface  
	//-------------------------------------------------------------------------
	def gwtAsyncAdapterName(Service service) {
		return service.name.toFirstUpper + "GWTAsyncAdapter";
	}

	def gwtAsyncAdapterFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtAsyncAdapterName(service);
	}

	def gwtAsyncAdapterFullQualifiedFileName(Service service) {
		return gwtAsyncAdapterFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// GWT remote service locator 
	//-------------------------------------------------------------------------
	def gwtRemoteServiceLocatorName(Model model) {
		return model.modelName.toFirstUpper + "GwtRemoteServiceLocator";
	}

	def gwtRemoteServiceLocatorFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtRemoteServiceLocatorName(model);
	}

	def gwtRemoteServiceLocatorFullQualifiedFileName(Model model) {
		return gwtRemoteServiceLocatorFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	 
	//-------------------------------------------------------------------------
	// GWT remote service locator interface 
	//-------------------------------------------------------------------------
	def gwtRemoteServiceLocatorInterfaceName(Model model) {
		return "I" + model.modelName.toFirstUpper + "GwtRemoteServiceLocator";
	}

	def gwtRemoteServiceLocatorInterfaceFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtRemoteServiceLocatorInterfaceName(model);
	}

	def gwtRemoteServiceLocatorInterfaceFullQualifiedFileName(Model model) {
		return gwtRemoteServiceLocatorInterfaceFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// module definition
	//-------------------------------------------------------------------------
	def baseModuleDefinitionName(ModuleDefinition moduleDefinition) {
		return "Base" + moduleDefinition.name.toFirstUpper + "Module";
	}

	def baseModuleDefinitionFullQualifiedName(ModuleDefinition moduleDefinition) {
		return getPackageName(moduleDefinition) + "." + baseModuleDefinitionName(moduleDefinition);
	}

	def baseModuleDefinitionFullQualifiedFileName(ModuleDefinition moduleDefinition) {
		return baseModuleDefinitionFullQualifiedName(moduleDefinition).replaceAll("\\.", "/")  + ".java";
	}
	
	def modelPackageName(Model model) {
		return ModelUtil.getSingleRootPackage(model).packageName.packageName
	}

	//-----------------------
	// vo mapper	
	//-----------------------
	def voMapperName(Model model) {
		return model.modelName.toFirstUpper + "VOMapper";
	}

	def voMapperFullQualifiedName(Model model) {
		return modelPackageName(model) + "." + voMapperName(model);
	}

	def voMapperFullQualifiedFileName(Model model) {
		return voMapperFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}

	def springDBApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "DB-gen.xml"
	}

	def baseApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "BaseApplicationContext-gen.xml"
	}

	def springPersistenceXMLFullQualifiedFileName(Model model) {
		return "META-INF/persistence.xml"
	}

	def persistenceUnitName(Model model) {
		return model.modelName.toLowerCase
	}

	def jndiName(Model model) {
		return persistenceUnitName(model)
	}

}
