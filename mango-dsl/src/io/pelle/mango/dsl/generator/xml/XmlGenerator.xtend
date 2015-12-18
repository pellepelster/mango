/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.xml

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import javax.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

class XmlGenerator implements IGenerator {

	@Inject
	extension XmlSchema

	@Inject
	extension EntityImportExportWSDL

	@Inject
	extension XmlNameUtils

	@Inject
	extension NameUtils

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {

		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.entityImportExportAppliationContextFullQualifiedFileName,
				GeneratorConstants.SERVER_GEN_OUTPUT, model.entityImportExportAppliationContext)
		}

		for (entity : resource.allContents.toIterable.filter(Entity)) {

			fsa.generateFile(entity.xsdFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT, entity.xmlSchema(true))
			fsa.generateFile(entity.entityImportExportWSDLFullQualifiedFileName, GeneratorConstants.XML_GEN_OUTPUT,
				entity.entityImportExportWSDL)

			fsa.generateFile(entity.entityImportExportWebserviceEndpointFullQualifiedName.classFileName,
				GeneratorConstants.SERVER_GEN_OUTPUT, entity.entityImportExportWebserviceEndpoint)
		}
	}
}
