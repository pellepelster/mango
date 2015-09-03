/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.web

import com.google.gwt.core.client.GWT
import com.google.gwt.core.ext.Generator
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider
import io.pelle.mango.client.base.modules.navigation.NavigationTreeProvider
import io.pelle.mango.client.base.util.GeneratedFactoriesProvider
import io.pelle.mango.client.base.util.MayCreate
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.DictionaryCustomComposite
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.NavigationNode
import javax.inject.Inject
import io.pelle.mango.client.base.util.IGeneratedFactory

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class GWTClient extends BaseServices {

	@Inject 
	extension DictionaryNameUtils

	def gwtClientModule(Model model) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 1.6.4//EN" "http://google-web-toolkit.googlecode.com/svn/tags/1.6.4/distro-source/core/src/gwt-module.dtd">
		<module>
		<inherits name="com.google.gwt.user.User" />
		
			<source path="«GeneratorConstants.CLIENT_PACKAGE_POSTFIX»" />

		</module>
	'''

	def generatedFactoriesInterface(Model model) '''
		package «model.modelPackageName»;
		
		interface «model.generatedFactoriesInterfaceName» extends «IGeneratedFactory.name» {
		}
	'''
	
	def generatedFactoriesGenerator(Model model) '''
	
		package «model.gwtRebindPackageName»;
		
		import java.io.PrintWriter;

		import com.google.gwt.core.ext.GeneratorContext;
		import com.google.gwt.core.ext.TreeLogger;
		import com.google.gwt.core.ext.UnableToCompleteException;
		import com.google.gwt.core.ext.typeinfo.JClassType;
		import com.google.gwt.core.ext.typeinfo.TypeOracle;
		import java.lang.Object;
		
		class «model.generatedFactoriesGeneratorClassName» extends «Generator.name» {

			@Override
			public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
			
				PrintWriter pw = context.tryCreate(logger, "«model.modelPackageName»", "GeneratedFactoryImpl");
				
				if (pw != null) {
					
					pw.println("package «model.modelPackageName»;");
					pw.println();
					pw.println("public class GeneratedFactoryImpl extends io.pelle.mango.gwt.rebind.GeneratedFactoriesGenerator implements «model.generatedFactoriesInterfaceFullQualifiedName» {");
					pw.println("	public GeneratedFactoryImpl() {");
					
					TypeOracle oracle = context.getTypeOracle();
					
					for (JClassType type : oracle.getTypes()) {
						if (type.getAnnotation(«MayCreate.name».class) != null) {
							String name = type.getQualifiedSourceName();
							pw.println("register(\"" + name + "\", ");
							pw.println("    new FactoryMethod() {");
							pw.println("        public Object create() {");
							pw.println("            return new " + name + "();");
							pw.println("        }");
							pw.println("    });");
						}
					}
					
					pw.println("    }");
					pw.println("}");
					context.commit(logger, pw);
				}
				
				return "«model.modelPackageName».GeneratedFactoryImpl";
			}
		}
	'''
	
	def gwtClientConfiguration(Model model) '''
	
		package «model.modelPackageName»;
		
		public class «model.gwtClientConfigurationName()» {
	
			private «model.gwtClientConfigurationName()»() {
				«model.generatedFactoriesInterfaceFullQualifiedName» factory = «GWT.name».create(«model.generatedFactoriesInterfaceFullQualifiedName».class);
				«GeneratedFactoriesProvider.name».registerBaseGeneratedFactory(factory);
			}
			
			private static «model.gwtClientConfigurationName()» instance;
		
			public static «model.gwtClientConfigurationName()» registerAll()
			{
				if (instance == null) {
					registerDictionaries();
					registerEnumerationValueParser();
					registerValueObjectEntityDescriptors();
					registerCustomCompositeFactories();
					«IF !model.eAllContents.filter(NavigationNode).isEmpty»
						registerNavigation();
					«ENDIF»
					
					instance = new «model.gwtClientConfigurationName()»();
				}
				
				return instance;
			}

			public static void registerEnumerationValueParser()
			{
				«DictionaryModelProvider.name».registerEnumerationConverter(new «model.enumerationValueParserFullQualifiedName»());
			}

			public static void registerValueObjectEntityDescriptors()
			{
				«FOR entity : model.eAllContents().toIterable.filter(Entity)»
				«VOMetaModelProvider.name».registerEntityDescriptor(«entity.voFullQualifiedName».«entity.entityConstantName»);
				«ENDFOR»
			}

			public static void registerDictionaries()
			{
				«FOR dictionary : model.eAllContents().toIterable.filter(Dictionary)»
					«DictionaryModelProvider.name».registerDictionary(«model.dictionaryClassFullQualifiedName».«dictionary.dictionaryConstantName»);
				«ENDFOR»
			}
	
			public static void registerCustomCompositeFactories()
			{
				«FOR customComposite : model.eAllContents().toIterable.filter(DictionaryCustomComposite)»
					io.pelle.mango.client.web.modules.dictionary.container.ContainerFactory.getInstance().registerCustomCompositeFactory("«customComposite.type»", "«customComposite.dictionaryCustomCompositeFactoryClassFullQualifiedName»");
				«ENDFOR»
			}
	
			«IF !model.eAllContents.filter(NavigationNode).isEmpty»
			public static void registerNavigation()
			{
				«NavigationTreeProvider.name».addRootNavigationElement(«model.navigationNodeClassName».ROOT);
			}
			«ENDIF»
	}
	'''

}