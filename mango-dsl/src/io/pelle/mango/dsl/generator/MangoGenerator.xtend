package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientGenerator
import io.pelle.mango.dsl.generator.server.ServerGenerator
import io.pelle.mango.dsl.generator.server.SpringGenerator
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.generator.xml.XmlGenerator
import io.pelle.mango.dsl.mango.Model
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

class MangoGenerator implements IGenerator {

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

		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.springDBApplicationContextFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.compileSpringDBApplicationContext)
			fsa.generateFile(model.springPersistenceXMLFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.compilePersistenceXml)
			fsa.generateFile(model.baseApplicationContextFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.compileBaseApplicationContext)
			fsa.generateFile(model.voMapperFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.compileVOMapper)
		}
		
	}

}
