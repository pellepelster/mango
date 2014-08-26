package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.query.EntityQuery
import java.util.ArrayList
import java.util.List
import io.pelle.mango.dsl.query.ModelQuery

class EntityImportExportWSDL {

	@Inject
	extension XmlSchema
	
	@Inject
	extension XmlNameUtils

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
	<?xml version="1.0" encoding="UTF-8"?>

	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:sws="http://www.springframework.org/schema/web-services"
		xsi:schemaLocation="http://www.springframework.org/schema/beans  
							http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
							http://www.springframework.org/schema/web-services  
							http://www.springframework.org/schema/web-services/web-services-2.0.xsd  
							http://www.springframework.org/schema/context  
							http://www.springframework.org/schema/context/spring-context-3.0.xsd">

		<bean class="org.springframework.ws.server.endpoint.mapping.PayloadRootAnnotationMethodEndpointMapping"/>

		«FOR entity : ModelQuery.createQuery(model).allEntities»
			<!-- «entity» -->
			<bean id="«entity.entityImportExportWebserviceEndpointBeanId»" class="«entity.entityImportExportWebserviceEndpointFullQualifiedName»">
				<property name="xmlVOImporter" ref="xmlVOImporter" />
			</bean>
			
			<bean id="«entity.entityImportExportWSDLBeanId»" class="org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition">
				<property name="wsdl" value="classpath:/«entity.entityImportExportWSDLFullQualifiedFileName»" />
			</bean>
		«ENDFOR»
	</beans>  
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
