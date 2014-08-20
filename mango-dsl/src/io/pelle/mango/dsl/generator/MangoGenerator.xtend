package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientGenerator
import io.pelle.mango.dsl.generator.server.EntityGenerator
import io.pelle.mango.dsl.generator.server.ServerGenerator
import io.pelle.mango.dsl.generator.server.SpringGenerator
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import io.pelle.mango.dsl.generator.xml.XmlGenerator

class MangoGenerator implements IGenerator {

	@Inject 
	extension EntityGenerator

	@Inject 
	extension VOMapperGenerator

	@Inject 
	extension NameUtils
	
	@Inject 
	extension ClientGenerator clientGenerator

	@Inject 
	extension ServerGenerator serverGenerator

	@Inject 
	extension XmlGenerator xmlGenerator

	@Inject 
	extension SpringGenerator

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {

		clientGenerator.doGenerate(resource, fsa);
		serverGenerator.doGenerate(resource, fsa);
		xmlGenerator.doGenerate(resource, fsa);

		for (entity : resource.allContents.toIterable.filter(Entity)) {
			fsa.generateFile(entity.entityFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, entity.compileEntity)
		}

		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.springDBApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileSpringDBApplicationContext)
			fsa.generateFile(model.springPersistenceXMLFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compilePersistenceXml)
			fsa.generateFile(model.baseApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileBaseApplicationContext)
			fsa.generateFile(model.voMapperFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileVOMapper)
		}
		
	}

}
