package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.DateEntityAttribute
import io.pelle.mango.dsl.mango.DecimalEntityAttribute
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.query.EntityQuery

class XmlSchema {

	@Inject
	extension XmlNameUtils

	//- xsd types -------------------------------------------------------------
	def dispatch xsdType (EntityAttribute entityAttribute) {
		throw new RuntimeException("xsdType (type) '" + entityAttribute.toString() + "' not implemented")
	}

	// simple types
	def dispatch xsdType(StringEntityAttribute stringEntityAttribute) '''xsd:string'''
	def dispatch xsdType(IntegerEntityAttribute integerEntityAttribute) '''xsd:integer'''
	def dispatch xsdType(BooleanEntityAttribute booleanEntityAttribute) '''xsd:boolean'''
	def dispatch xsdType(LongEntityAttribute longEntityAttribute) '''xsd:long'''
	def dispatch xsdType(BinaryEntityAttribute binaryEntityAttribute) '''xsd:hexBinary'''
	def dispatch xsdType(DateEntityAttribute dateEntityAttribute) '''xsd:date'''
	def dispatch xsdType(DecimalEntityAttribute bigDecimalDatatype)'''xsd:decimal'''
	def dispatch xsdType(EntityEntityAttribute entityEntityAttribute) ''''''
	def dispatch xsdType(EnumerationEntityAttribute enumerationEntityAttribute) ''''''

	/**
 	«DEFINE xsdType FOR SimpleType-»«IF this.type == SimpleTypes::string-»xsd:string«ENDIF-»«IF this.type == SimpleTypes::long-»xsd:integer«ENDIF-»«IF this.type == SimpleTypes::integer-»xsd:integer«ENDIF-»«IF this.type == SimpleTypes::bigdecimal-»xsd:decimal«ENDIF-»«IF this.type == SimpleTypes::boolean-»xsd:boolean«ENDIF-»«IF this.type == SimpleTypes::reference-»unsupported type«ENDIF-»«ENDDEFINE»
	*/

	//- xsd attributes ------------------------------------------------------------
	def dispatch xsdAttribute(Entity entity, EntityAttribute attribute) '''
	<xsd:element name="«attribute.name»" type="«attribute.xsdType»" />
	'''
	
	def dispatch xsdAttribute(Entity entity, EntityEntityAttribute entityEntityAttribute) '''
	«IF (entityEntityAttribute.cardinality == Cardinality::ONETOMANY || entityEntityAttribute.cardinality == Cardinality::MANYTOMANY)»
		<xsd:element name="«entityEntityAttribute.name»" type="«IF EntityQuery.getEntity(entityEntityAttribute) != entity»«EntityQuery.getEntity(entityEntityAttribute).xsdQualifier»:«ENDIF»«EntityQuery.getEntity(entityEntityAttribute).xsdElementReferenceListWrapperName»" />
		«ELSE»
		<xsd:element name="«entityEntityAttribute.name»" type="«IF EntityQuery.getEntity(entityEntityAttribute) != entity»«EntityQuery.getEntity(entityEntityAttribute).xsdQualifier»:«ENDIF»«EntityQuery.getEntity(entityEntityAttribute).xsdTypeReferenceName»" />
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

	def xmlSchema(Entity entity) '''
		<xsd:schema
		xmlns="«entity.xsdNamespace»"
		xmlns:xsd="http://www.w3.org/2001/XMLSchema"
		targetNamespace="«entity.xsdNamespace»"
		«FOR referencedEntity : EntityQuery.createQuery(entity).getReferencedEntities()»
		xmlns:«entity.xsdQualifier»="«entity.xsdNamespace»"
		«ENDFOR»

		«IF entity.extends != null»
		xmlns:«entity.extends.xsdQualifier»="«entity.extends.xsdNamespace»"
		«ENDIF»
		
		elementFormDefault="unqualified">

		«FOR referencedEntity : EntityQuery.createQuery(entity).getReferencedEntities()»
		<xsd:import namespace="«referencedEntity.xsdNamespace»" schemaLocation="«referencedEntity.xsdSchemaLocation»"/>
		«ENDFOR»
		
		«IF entity.extends != null»
		<xsd:import namespace="«entity.extends.xsdNamespace»" schemaLocation="«entity.extends.xsdSchemaLocation»"/>
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
			<xsd:extension base="«entity.extends.xsdQualifier»:«entity.extends.xsdTypeName»">
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
				«IF entity.naturalKeyAttributes.contains(attribute)»
					«xsdAttribute(entity, attribute)»
				«ENDIF»
			«ENDFOR»
			</xsd:sequence>
		</xsd:complexType>
	</xsd:schema>
'''

/**
//- extra types ---------------------------------------------------------------
«DEFINE extraTypes(Entity entity, EntityAttribute attribute) FOR Type-»
«ENDDEFINE»
«DEFINE extraTypes(Entity entity, EntityAttribute attribute) FOR EntityReferenceType-»
«ENDDEFINE»
«DEFINE xsdEnumeration(String name, List[String] enumerationValues) FOR Object-»
<xsd:element name="«name»">
<xsd:simpleType>
<xsd:restriction base="xsd:string">
«FOREACH enumerationValues AS enumaration»
<xsd:enumeration value="«enumaration»" />
«ENDFOREACH»
</xsd:restriction>
</xsd:simpleType>
</xsd:element>
«ENDDEFINE»
* 
* 
* 	«FOR attribute : entity.attributes»
		«EXPAND extraTypes(this, attribute) FOR attribute.type»
	«ENDFOR»

 */
}
