/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.xml

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.xml.EntityImportExportWSDL
import io.pelle.mango.dsl.generator.xml.XmlNameUtils
import io.pelle.mango.dsl.generator.xml.XmlSchema
import io.pelle.mango.dsl.mango.Entity
import javax.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import io.pelle.mango.dsl.mango.Model

class XmlGenerator implements IGenerator {

	@Inject
	extension XmlSchema

	@Inject
	extension EntityImportExportWSDL

	@Inject
	extension XmlNameUtils

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {

		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.entityImportExportAppliationContextFullQualifiedFileName,
				GeneratorConstants.ENTITIES_GEN_OUTPUT, model.entityImportExportAppliationContext)
		}

		for (entity : resource.allContents.toIterable.filter(Entity)) {
			
			fsa.generateFile(entity.xsdFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT, entity.xmlSchema(true))
			fsa.generateFile(entity.entityImportExportWSDLFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT,
				entity.entityImportExportWSDL)
			
			fsa.generateFile(entity.entityImportExportWebserviceEndpointFullQualifiedFileName,
				GeneratorConstants.ENTITIES_GEN_OUTPUT, entity.entityImportExportWebserviceEndpoint)
		}

	}

}
