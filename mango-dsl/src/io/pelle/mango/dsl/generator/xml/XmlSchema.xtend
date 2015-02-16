package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.server.EntityUtils
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.DateEntityAttribute
import io.pelle.mango.dsl.mango.DecimalEntityAttribute
import io.pelle.mango.dsl.mango.DoubleEntityAttribute
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.FloatEntityAttribute
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.query.EntityQuery
import io.pelle.mango.dsl.generator.util.AttributeUtils

class XmlSchema {

	@Inject
	extension XmlNameUtils

	@Inject
	extension EntityUtils

	@Inject
	extension AttributeUtils

	def xmlSchema(Entity entity, boolean generateImports) '''
	<xsd:schema
	xmlns="«entity.xmlNamespace»"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	targetNamespace="«entity.xmlNamespace»"
	«entity.xmlEntityNamespaces»
	«IF entity.extends != null»
	xmlns:«entity.extends.xmlNamespacePrefix»="«entity.extends.xmlNamespace»"
	«ENDIF»
	elementFormDefault="unqualified">

	«IF generateImports»
		«FOR referencedEntity : EntityQuery.createQuery(entity).getReferencedEntities()»
		<xsd:import namespace="«referencedEntity.xmlNamespace»" schemaLocation="«referencedEntity.xmlSchemaLocation»"/>
		«ENDFOR»
	«ENDIF»
	
	«IF entity.extends != null»
	<xsd:import namespace="«entity.extends.xmlNamespace»" schemaLocation="«entity.extends.xmlSchemaLocation»"/>
	«ENDIF»
	
	<xsd:element name="«entity.xsdElementListName»">
		<xsd:complexType>
			<xsd:sequence>
				<xsd:element name="«entity.xsdElementName»" type="«entity.xsdTypeName»" minOccurs="0" maxOccurs="unbounded" />
			</xsd:sequence>
		</xsd:complexType>
	</xsd:element>

	<xsd:complexType name="«entity.xsdTypeName»">
		«IF entity.extends != null»
		<xsd:complexContent>
		<xsd:extension base="«entity.extends.xmlNamespacePrefix»:«entity.extends.xsdTypeName»">
		«ENDIF»
		<xsd:sequence>
			<xsd:element name="id" type="xsd:integer"/>
			«FOR attribute : entity.attributes»
				«xsdAttribute(entity, attribute)»
			«ENDFOR»
		</xsd:sequence>
		«IF entity.extends != null»
		</xsd:extension>
		</xsd:complexContent>
		«ENDIF»
	</xsd:complexType>

	«entity.xsdElementReferenceListWrapper»

	<xsd:complexType name="«entity.xsdTypeReferenceName»">
		<xsd:sequence>
			<xsd:element name="id" type="xsd:integer"/>
			«FOR attribute : entity.attributes»
				«IF entity.naturalKeyFields.contains(attribute)»
					«xsdAttribute(entity, attribute)»
				«ENDIF»
			«ENDFOR»
			</xsd:sequence>
	</xsd:complexType>
	
	<xsd:complexType name="MapType">
		<xsd:sequence>
			<xsd:element name="key" type="xsd:anyType"/>
			<xsd:element name="value" type="xsd:anyType"/>
		</xsd:sequence>
	</xsd:complexType>
	
	</xsd:schema>
	'''


	//- xsd types -------------------------------------------------------------
	def dispatch xsdType (EntityAttribute entityAttribute) {
		throw new RuntimeException("xsdType (type) '" + entityAttribute.toString() + "' not implemented")
	}

	// simple types
	def dispatch xsdType(StringEntityAttribute entityAttribute) '''xsd:string'''
	def dispatch xsdType(IntegerEntityAttribute entityAttribute) '''xsd:integer'''
	def dispatch xsdType(BooleanEntityAttribute entityAttribute) '''xsd:boolean'''
	def dispatch xsdType(LongEntityAttribute entityAttribute) '''xsd:long'''
	def dispatch xsdType(BinaryEntityAttribute entityAttribute) '''xsd:hexBinary'''
	def dispatch xsdType(DateEntityAttribute entityAttribute) '''xsd:date'''
	def dispatch xsdType(DecimalEntityAttribute entityAttribute)'''xsd:decimal'''
	def dispatch xsdType(FloatEntityAttribute entityAttribute)'''xsd:float'''
	def dispatch xsdType(DoubleEntityAttribute entityAttribute)'''xsd:double'''
	def dispatch xsdType(MapEntityAttribute entityAttribute)'''«entityAttribute.parentEntity.xmlNamespacePrefix»:MapType'''
	def dispatch xsdType(EntityEntityAttribute entityAttribute) ''''''
	def dispatch xsdType(EnumerationEntityAttribute entityAttribute) ''''''

	//- xsd attributes ------------------------------------------------------------
	def dispatch xsdAttribute(Entity entity, EntityAttribute attribute) '''
	<xsd:element name="«attribute.name»" type="«attribute.xsdType»" />
	'''
	
	def dispatch xsdAttribute(Entity entity, EntityEntityAttribute entityEntityAttribute) '''
	«IF (entityEntityAttribute.cardinality == Cardinality::ONETOMANY || entityEntityAttribute.cardinality == Cardinality::MANYTOMANY)»
		<xsd:element name="«entityEntityAttribute.name»" type="«IF EntityQuery.getEntity(entityEntityAttribute) != entity»«EntityQuery.getEntity(entityEntityAttribute).xmlNamespacePrefix»:«ENDIF»«EntityQuery.getEntity(entityEntityAttribute).xsdElementReferenceListWrapperName»" />
		«ELSE»
		<xsd:element name="«entityEntityAttribute.name»" type="«IF EntityQuery.getEntity(entityEntityAttribute) != entity»«EntityQuery.getEntity(entityEntityAttribute).xmlNamespacePrefix»:«ENDIF»«EntityQuery.getEntity(entityEntityAttribute).xsdTypeReferenceName»" />
	«ENDIF»
	'''
	
	def dispatch xsdAttribute(Entity entity, EnumerationEntityAttribute attribute) '''
	<xsd:element name="«attribute.name»">
		<xsd:complexType>
		<xsd:sequence minOccurs="0" maxOccurs="unbounded">
			<xsd:element name="KeyValue">
				<xsd:complexType>
					<xsd:simpleContent>
						<xsd:extension base="xsd:string">
							<xsd:attribute type="xsd:string" name="key"/>
							<xsd:attribute type="xsd:string" name="value"/>
						</xsd:extension>
					</xsd:simpleContent>
				</xsd:complexType>
			</xsd:element>
			</xsd:sequence>
		</xsd:complexType>
	</xsd:element>
	'''
		
	def xsdElementReferenceListWrapper(Entity entity) '''
	<xsd:complexType name="«entity.xsdElementReferenceListWrapperName»">
		<xsd:sequence>
			<xsd:element name="«entity.xsdElementReferenceName»" type="«entity.xsdTypeReferenceName»" minOccurs="0" maxOccurs="unbounded" />
			</xsd:sequence>
	</xsd:complexType>
	'''
}
