package io.pelle.mango.dsl.generator.server

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IEntityVOMapper
import io.pelle.mango.dsl.generator.xml.XmlNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

class SpringGenerator {
	
	@Inject 
	extension ServerNameUtils

	@Inject 
	extension XmlNameUtils

	def springApplicationContextProperties(Model model) '''
	applicationName=«model.modelName.toLowerCase»
	'''

	def compileBaseApplicationContext(Model model) '''
		package «model.baseApplicationContextPackageName»;

		@«Configuration.name»
		@«PropertySources.name»({ @«PropertySource.name»(value = "classpath:/«model.springApplicationContextPropertiesFullQualifiedFileName»") })
		public class «model.baseApplicationContextName» {
		
			@«Bean.name»
			public «model.xmlVOMapperFullQualifiedName» «model.modelName.toFirstLower»XMLVOMapper() {
				return new «model.xmlVOMapperFullQualifiedName»();
			}

			@«Bean.name»
			public «IEntityVOMapper.name» «model.modelName.toFirstLower»VOMapper() {
				return new «model.voMapperFullQualifiedName»();
			}
		}
	'''


	def compilePersistenceXml(Model model) '''
		<persistence version="1.0"
			xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
			http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd">
		
				<persistence-unit name="«persistenceUnitName(model)»">
				«FOR entity:model.eAllContents.toIterable.filter(Entity)»
				<class>«entityFullQualifiedName(entity)»</class>
				«ENDFOR»
				<exclude-unlisted-classes>false</exclude-unlisted-classes>
		
			</persistence-unit>
		</persistence>
	'''

}
