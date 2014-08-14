package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.Model

class XmlNameUtils {
	
	@Inject
	extension ServerNameUtils
	
	def String xsdNamespaceUrl(Model model) {
		return "http://" + model.name.toLowerCase()
	}

	def String xsdNamespace(Entity entity) {
		return xsdNamespaceUrl(ModelUtil.getRootModel(entity)) + "/" + entity.name.toLowerCase()
	}

	def String xsdQualifier(Entity entity) {
		return entity.name.toLowerCase()
	}

	def xsdSchemaLocation(Entity entity) {
		xsdFileName(entity);
	}

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
	
	def xmlVOMapperName(Model model)  {
		return model.name.toFirstUpper() + 'XmlVOMapper';		
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
}
