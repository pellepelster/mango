/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.server.service.GWTServices
import io.pelle.mango.dsl.generator.server.service.SpringServices
import io.pelle.mango.dsl.generator.xml.EntityImportExportWSDL
import io.pelle.mango.dsl.generator.xml.XmlNameUtils
import io.pelle.mango.dsl.generator.xml.XmlSchema
import io.pelle.mango.dsl.generator.xml.XmlVOMapper
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.Service
import javax.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

class ServerGenerator implements IGenerator {

	@Inject
	extension GWTServices

	@Inject
	extension XmlSchema

	@Inject
	extension EntityImportExportWSDL

	@Inject
	extension XmlVOMapper
	
	@Inject
	extension XmlNameUtils
	
	@Inject
	extension SpringServices

	@Inject
	extension ServerNameUtils

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.gwtRemoteServicesApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.gwtRemoteServicesApplicationContext)
			fsa.generateFile(model.serviceSpringNameApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.springServices)
			fsa.generateFile(model.xmlVOMapperFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.xmlVOMapper)
		}

		for (service : resource.allContents.toIterable.filter(Service)) {
			//fsa.generateFile(service.restControllerFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, service.restServiceController)
		}
		
		for (entity: resource.allContents.toIterable.filter(Entity)) {
			fsa.generateFile(entity.xsdFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT, entity.xmlSchema(true))
			fsa.generateFile(entity.entityImportExportWSDLFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT, entity.entityImportExportWSDL)
		}
		
	}

}
