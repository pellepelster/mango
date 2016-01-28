package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.server.service.ServiceNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.query.EntityQuery
import io.pelle.mango.dsl.query.ModelQuery
import java.util.ArrayList
import java.util.List
import java.util.Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

class EntityImportExportWSDL {

	@Inject
	extension XmlSchema
	
	@Inject
	extension XmlNameUtils

	@Inject
	extension ServiceNameUtils

	def entityImportExportWSDL(Entity entity) '''
	<definitions name="«entity.name»ImportExport"
	   targetNamespace="«entity.entityImportExportWSDLNamespace»"
	   xmlns="http://schemas.xmlsoap.org/wsdl/"
	   xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
	   xmlns:tns="«entity.entityImportExportWSDLNamespace»"
	   «entity.xmlEntityNamespaces»
	   xmlns:xsd="http://www.w3.org/2001/XMLSchema">

		<types>
			«entity.xmlSchemas(new ArrayList())»
		</types>
	 
		<message name="«entity.name.toFirstUpper»ImportRequest">
			<part name="entity" type="«entity.xmlNamespacePrefix»:«entity.xsdElementListName»"/>
		</message>

		<message name="«entity.name.toFirstUpper»ImportResponse">
			<part name="result" type="xsd:string"/>
		</message>
	
		<portType name="«entity.name.toFirstUpper»ImportPortType">
			<operation name="ImportEntity">
				<input message="tns:«entity.name.toFirstUpper»ImportRequest"/>
				<output message="tns:«entity.name.toFirstUpper»ImportResponse"/>
			</operation>
		</portType>
	
		<binding name="«entity.name.toFirstUpper»ImportBinding" type="tns:«entity.name.toFirstUpper»ImportPortType">
			<soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
			<operation name="ImportEntity">
				<soap:operation soapAction="ImportEntity"/>
				<input>
					<soap:body use="literal"/>
				</input>
				<output>
					<soap:body use="literal"/>
				</output>
			</operation>
		</binding>
	
		<service name="«entity.name.toFirstUpper»ImportService">
			<documentation>WSDL for «entity.name.toFirstUpper» import/export</documentation>
			<port binding="tns:«entity.name.toFirstUpper»ImportBinding" name="«entity.name.toFirstUpper»ImportPortType">
				<soap:address location="webservices/«entity.name.toFirstUpper»ImportService" />
			</port>
		</service>
	
	</definitions>
	'''
	
	def entityImportExportAppliationContext(Model model) '''
		package «model.entityImportExportServicesApplicationContextPackageName»;
		
		@«Configuration.name»
		@«Import.name»({ io.pelle.mango.server.base.xml.MangoBaseXmlImportExportApplicationContext.class })
		public class «model.entityImportExportServicesApplicationContextName» implements org.springframework.context.ResourceLoaderAware {

			private org.springframework.core.io.ResourceLoader resourceLoader;
			
			@java.lang.Override
			public void setResourceLoader(org.springframework.core.io.ResourceLoader resourceLoader) {
				this.resourceLoader = resourceLoader;
			}
			
			«FOR entity : ModelQuery.createQuery(model).allEntities»
				@«Bean.name»
				@org.springframework.beans.factory.annotation.Autowired
				public «entity.entityImportExportWebserviceEndpointFullQualifiedName» «entity.entityImportExportWebserviceEndpointBeanId»(io.pelle.mango.server.base.xml.IXmlVOImporter xmlVOImporter) {
					
					«entity.entityImportExportWebserviceEndpointFullQualifiedName» result = new «entity.entityImportExportWebserviceEndpointFullQualifiedName»();
					result.setXmlVOImporter(xmlVOImporter);
					
					return result;
				}

				@«Bean.name»
				public org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition «entity.entityImportExportWSDLBeanId»() {
					
					org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition result = new org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition();
					result.setWsdl(resourceLoader.getResource("classpath:/«entity.entityImportExportWSDLFullQualifiedFileName»"));
					
					return result;
				}
			«ENDFOR»

			@«Bean.name»
			@«Autowired.name»
			public org.springframework.web.servlet.handler.SimpleUrlHandlerMapping «model.modelName.toFirstLower»XmlImportExportMappings(org.springframework.ws.soap.server.SoapMessageDispatcher soapMessageDispatcher) {
				
				org.springframework.web.servlet.handler.SimpleUrlHandlerMapping result = new org.springframework.web.servlet.handler.SimpleUrlHandlerMapping();

				«Properties.name» mappings = new «Properties.name»();

				«FOR entity : ModelQuery.createQuery(model).allEntities»
				mappings.put("«entity.entityImportExportWSDLFullQualifiedFileName»", «entity.entityImportExportWSDLBeanId»());
				«ENDFOR»
		
				result.setMappings(mappings);
			    result.setDefaultHandler(soapMessageDispatcher);
				
				return result; 
			}
		}
	'''
	
	def entityImportExportWebserviceEndpoint(Entity entity) '''
	package «entity.entityImportExportWebserviceEndpointPackage»;
	
	import io.pelle.mango.server.base.xml.IXmlVOImporter;	
	
	import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
	import org.springframework.ws.server.endpoint.annotation.RequestPayload;
	import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
	import org.w3c.dom.Element;
	
	@org.springframework.ws.server.endpoint.annotation.Endpoint
	public class «entity.entityImportExportWebserviceEndpointName» {
	
		private static final String TARGET_NAMESPACE = "«entity.entityImportExportWSDLNamespace»";
	
		@org.springframework.beans.factory.annotation.Autowired
		private IXmlVOImporter xmlVOImporter;
		
		@PayloadRoot(localPart = "«entity.xsdElementName»", namespace = TARGET_NAMESPACE)
		public @ResponsePayload Element importEntity(@RequestPayload Element element) {
			xmlVOImporter.importVOs(element);
			return element;
		}
		
		public void setXmlVOImporter(IXmlVOImporter xmlVOImporter) {
			this.xmlVOImporter = xmlVOImporter;
		}
	}	
	'''
	
	def String xmlSchemas(Entity entity, List<Entity> visitedEntities) '''
		«entity.xmlSchema(false)»
		«IF visitedEntities.add(entity)»«ENDIF»
		«FOR referencedEntity : EntityQuery.createQuery(entity).referencedEntities»
			«IF !visitedEntities.contains(referencedEntity)»
				«xmlSchemas(referencedEntity, visitedEntities)»
			«ENDIF»
		«ENDFOR»
	'''
}
