package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.query.EntityQuery
import java.util.List
import java.util.ArrayList

class XmlNameUtils {
	
	@Inject
	extension ServerNameUtils
	
	//=========================================================================
	// namespace/schemas
	//=========================================================================
	def String xmlNamespaceUrl(Model model) {
		return "http://" + model.modelName.toLowerCase()
	}

	def String xmlNamespace(Entity entity) {
		return xmlNamespaceUrl(ModelUtil.getRootModel(entity)) + "/" + entity.name.toLowerCase()
	}
	
	def String xmlNamespacePrefix(Entity entity) {
		return entity.name.toLowerCase()
	}

	def xmlSchemaLocation(Entity entity) {
		xsdFileName(entity);
	}

	def String xmlEntityNamespaces(Entity entity) {
		xmlEntityNamespaces(entity, new ArrayList)
	}

	def String xmlEntityNamespaces(Entity entity, List<Entity> visitedEntities) '''
		xmlns:«entity.xmlNamespacePrefix»="«entity.xmlNamespace»"
		«IF visitedEntities.add(entity)»«ENDIF»
		«FOR referencedEntity : EntityQuery.createQuery(entity).referencedEntities»
			«IF !visitedEntities.contains(referencedEntity)»
				«xmlEntityNamespaces(referencedEntity, visitedEntities)»
			«ENDIF»
		«ENDFOR»
	'''
	
	//=========================================================================
	// xsd
	//=========================================================================
	def xsdFullQualifiedFileName(Entity entity) {
		return entity.xsdFileName
	}

	def xsdFileName(Entity entity) {
		return entity.name.toLowerCase() + ".xsd"
	}

	def xsdTypeName(Entity entity) {
		entity.name.toFirstUpper() + "Type"
	}

	def xsdTypeReferenceName(Entity entity) {
		entity.name.toFirstUpper() + "ReferenceType"
	}

	def xsdElementName(Entity entity) {
		entity.name.toFirstUpper()
	}

	def xsdElementListName(Entity entity) {
		xsdElementName(entity) + "List"
	}

	def xsdElementReferenceName(Entity entity) {
		entity.name.toFirstUpper() + "Reference"
	}

	def xsdElementReferenceWrapperName(EntityEntityAttribute attribute) {
		attribute.name.toFirstUpper() + "ReferenceWrapper"
	}

	def xsdElementReferenceWrapperName(Entity entity) {
		entity.name.toFirstUpper() + "Reference"
	}

	def xsdElementReferenceListWrapperName(Entity entity) {
		entity.name.toFirstUpper() + "ReferenceList"
	}
	
	//-------------------------------------------------------------------------
	// xml vo mapper
	//-------------------------------------------------------------------------
	def xmlVOMapperName(Model model)  {
		return model.modelName.toFirstUpper() + 'XmlVOMapper';		
	}
	
	def xmlVOMapperPackage(Model model) {
		model.modelPackageName
	}

	def xmlVOMapperFullQualifiedName(Model model) {
		return model.xmlVOMapperPackage + "." + model.xmlVOMapperName;
	}
	
	def xmlVOMapperFullQualifiedFileName(Model model) {
		return xmlVOMapperFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	
	//=========================================================================
	// webservice
	//=========================================================================
	
	//-------------------------------------------------------------------------
	// wsdl
	//-------------------------------------------------------------------------
	def entityImportExportName(Entity entity)  {
		return entity.name.toLowerCase + "_import_export";		
	}
	
	def entityImportExportWSDLName(Entity entity)  {
		return entity.entityImportExportName + ".wsdl";		
	}
	
	def entityImportExportWSDLFullQualifiedFileName(Entity entity) {
		return entityImportExportWSDLName(entity);
	}
	
	def String entityImportExportWSDLNamespace(Entity entity) {
		return xmlNamespaceUrl(ModelUtil.getRootModel(entity)) + "/" + entity.name.toLowerCase()
	}
	

	def entityImportExportWSDLSoapAction(Entity entity) {
		return xmlNamespaceUrl(ModelUtil.getRootModel(entity)) + "/Import" + entity.name.toFirstUpper() + ".wsdl"
	}
	
	def entityImportExportWSDLBeanId(Entity entity) {
		return entity.name.toFirstLower + "ImportExportWSDLDefinition"
	}

	//-------------------------------------------------------------------------
	// endpoint
	//-------------------------------------------------------------------------
	def entityImportExportWebserviceEndpointPackage(Entity entity) {
		entity.packageName
	}

	def entityImportExportWebserviceEndpointName(Entity entity)  {
		return entity.name.toFirstUpper() + 'WebserviceEndpoint';		
	}

	def entityImportExportWebserviceEndpointFullQualifiedName(Entity entity) {
		return entity.entityImportExportWebserviceEndpointPackage + "." + entity.entityImportExportWebserviceEndpointName;
	}
	
	def entityImportExportWebserviceEndpointFullQualifiedFileName(Entity entity)
	{
		return entity.entityImportExportWebserviceEndpointFullQualifiedName.replaceAll("\\.", "/")  + ".java";
	}

	def entityImportExportWebserviceEndpointBeanId(Entity entity) {
		return entity.name.toFirstLower + "WebserviceEndpoint";
	}
	
	//-------------------------------------------------------------------------
	// application context
	//-------------------------------------------------------------------------
	def entityImportExportAppliationContextFullQualifiedFileName(Model model)
	{
		return model.modelName.toFirstUpper + "Webservices-gen.xml";
	}
	
}
