package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.query.EntityQuery
import java.util.ArrayList
import java.util.List

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
	   xmlns:xsd="http://www.w3.org/2001/XMLSchema">
	 
		<types>
			«entity.xmlSchemas(new ArrayList())»
		</types>
	 
		<message name="ImportEntityRequest">
			<part name="entity" type="«entity.xsdQualifier»:«entity.xsdElementListName»"/>
		</message>
	   
		<message name="ImportEntityResponse">
			<part name="result" type="xsd:string"/>
		</message>
	
		<portType name="ImportEntityPortType">
			<operation name="import">
				<input message="tns:ImportEntityRequest"/>
				<output message="tns:ImportEntityResponse"/>
			</operation>
		</portType>
	
		<binding name="ImportEntityBinding" type="tns:ImportEntityPortType">
			<soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
			<operation name="ImportEntity">
				<soap:operation soapAction="«entity.entityImportExportWSDLSoapAction»"/>
				<input>
					<soap:body use="literal"/>
				</input>
				<output>
					<soap:body use="literal"/>
				</output>
			</operation>
		</binding>
	
		<service name="ImportEntityService">
			<documentation>WSDL File for HelloService</documentation>
			<port binding="tns:ImportEntityBinding" name="ImportEntityPortType">
				<soap:address
				location="/ImportEntityService">
			</port>
		</service>
		
	</definitions>
	'''

	def xmlSchemas(Entity entity, List<Entity> visitedEntities) '''
		«entity.xmlSchema»
		<!-- «visitedEntities.add(entity)» -->
		«FOR referencedEntity : EntityQuery.createQuery(entity).referencedEntities»
			«IF !visitedEntities.contains(referencedEntity)»
				<!-- «referencedEntity.name» -->
				«xmlSchemas(referencedEntity, visitedEntities)»
			«ENDIF»
		«ENDFOR»
	'''
}
