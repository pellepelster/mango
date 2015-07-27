package io.pelle.mango.dsl.generator.server

import com.google.inject.Inject
import io.pelle.mango.client.base.db.vos.IBaseHierarchical
import io.pelle.mango.client.base.vo.EntityDescriptor
import io.pelle.mango.client.base.vo.IEntityDescriptor
import io.pelle.mango.client.base.vo.IVOEntity
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.dsl.generator.BaseEntityGenerator
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.DateEntityAttribute
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.query.EntityQuery
import io.pelle.mango.dsl.query.datatype.StringDatatypeQuery
import io.pelle.mango.server.base.BaseEntity
import java.util.ArrayList

class EntityGenerator extends BaseEntityGenerator {

	@Inject
	extension EntityUtils
	
	@Inject
	extension ServerAttributeUtils

	@Inject
	extension ServerNameUtils

	@Inject
	ClientNameUtils clientNameUtils

	@Inject
	extension ServerTypeUtils typeUtils
	
	def compileVODAO(Entity entity) '''
		package «entity.packageName»;
		
		public class «entity.voDAOName» extends io.pelle.mango.db.dao.AbstractVODAO<«clientNameUtils.voFullQualifiedName(entity)»> implements «entity.voDAOInterfaceFullQualifiedName» {
			
			public «entity.voDAOName»() {
				super(«clientNameUtils.voFullQualifiedName(entity)».class);
			}
			
		}
	'''
	
	def compileVODAOInterface(Entity entity) '''
		package «entity.packageName»;
		
		public interface «entity.voDAOInterfaceName» extends io.pelle.mango.db.dao.IVOEntityDAO<«clientNameUtils.voFullQualifiedName(entity)»> {
		}
	'''
	
	
	def compileEntityDAO(Entity entity) '''
		package «entity.packageName»;
		
		public class «entity.entityDAOName» extends io.pelle.mango.db.dao.AbstractEntityDAO<«entity.type»> implements «entity.entityDAOInterfaceFullQualifiedName» {
			
			public «entity.entityDAOName»() {
				super(«entity.typeClass»);
			}
			
		}
	'''

	def compileEntityDAOInterface(Entity entity) '''
		package «entity.packageName»;
		
		public interface «entity.entityDAOInterfaceName» extends io.pelle.mango.db.dao.IVOEntityDAO<«entity.type»> {
		}
	'''
	
	def compileEntity(Entity entity) '''
		«System.out.println(String.format("generating entity '%s' (entityDisableIdField: %s", entity.entityName, entity.entityDisableIdField))»
		package «entity.packageName»;
		
		import javax.persistence.*;
		import org.hibernate.annotations.GenericGenerator;
		
		@Entity
		@Table(name = "«entity.entityTableName»")
		@GenericGenerator( name="«entity.name.toLowerCase()»_generator", strategy="native")
		«IF EntityQuery.isExtendedByOtherEntity(entity)»
		@javax.persistence.Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
		«ENDIF»
		«IF entity.extends != null»
		«ELSE»
		«ENDIF»
		@SuppressWarnings("all")
		public class «entity.entityName» extends «IF entity.extends != null»«entityFullQualifiedName(entity.extends)»«ELSEIF entity.jvmtype != null»«entity.jvmtype.qualifiedName»«ELSE»«BaseEntity.name»«ENDIF» implements io.pelle.mango.client.base.db.vos.IInfoVOEntity «IF entity.entityHierarchical», «IBaseHierarchical.name»«ENDIF» {
		
			public static final «IEntityDescriptor.name»<«entity.entityFullQualifiedName»> «entity.entityConstantName» = new «EntityDescriptor.name»<«entity.type»>(«entity.typeClass», "«entity.name»", "«entity.label»", "«entity.pluralLabel»");

			«entity.attributeDescriptorsFromExtends»

			«entity.compileGetAttributeDescriptors»
		
			«IF entity.extends == null && !entity.entityDisableIdField»
			public static «LongAttributeDescriptor.name» «IVOEntity.ID_FIELD_NAME.attributeConstantName» = new «LongAttributeDescriptor.name»(«entity.entityConstantName», "«IVOEntity.ID_FIELD_NAME»");

			@Id
			@Column(name = "«entity.entityTableIdColumnName»")
			
			@GeneratedValue(generator = "«entity.name.toLowerCase()»_generator" )
			private long id;
			«getterSetter("long", "id")»
			«ENDIF»
			
			«FOR attribute : entity.attributes»
			«changeTrackingAttributeGetterSetter(entity, attribute)»
			«ENDFOR»
			
			«FOR infoVOEntityAttribute : infoVOEntityAttributes()»
				«infoVOEntityAttribute.generate(entity)»
			«ENDFOR»
			
			«entity.compileNaturalKey»

			«IF entity.entityHierarchical»
				«FOR hierarchicalEntityAttribute : hierarchicalEntityAttributes()»
					«hierarchicalEntityAttribute.generate(entity)»
				«ENDFOR»
			«ENDIF»
			
			@Override
			public String toString() {
				return com.google.common.base.Objects.toStringHelper(this).«FOR naturalKeyAttribute : entity.naturalKeyFields SEPARATOR "."»addValue(«naturalKeyAttribute.attributeName.getterName»())«ENDFOR»«IF !entity.naturalKeyFields.empty».«ENDIF»toString();
			}
		}
	'''

	// jpa annotations
	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, EntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»")
	'''

	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, BinaryEntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»", length = 10 * 1024 * 1024)
		@javax.persistence.Lob
	'''

	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, EnumerationEntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»")
		@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
		@javax.persistence.ElementCollection(fetch=javax.persistence.FetchType.EAGER)
		@CollectionTable(name = "«entityAttribute.entityTableColumnName»", joinColumns = @JoinColumn(name = "«entityAttribute.entityTableColumnName»_id") )
		«ENDIF»
	'''

	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, DateEntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»")
		@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	'''
 
	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, MapEntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»")
		@ElementCollection(fetch = FetchType.EAGER)
		@JoinTable(name = "«entityAttribute.parentEntity.entityTableName»_«entityAttribute.name.toLowerCase»", joinColumns = @JoinColumn(name = "id"))
	'''

	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, StringEntityAttribute entityAttribute) '''
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
			@ElementCollection(fetch=javax.persistence.FetchType.EAGER)
			@CollectionTable(name = "«entityAttribute.entityTableColumnName»", joinColumns = @JoinColumn(name = "«entityAttribute.entityTableColumnName»_id") )
		«ELSE»
			@Column(name = "«entityAttribute.entityTableColumnName»"«IF StringDatatypeQuery.createQuery(entityAttribute.type).hasMaxLength», length = «StringDatatypeQuery.createQuery(entityAttribute.type).maxLength»«ENDIF»)
		«ENDIF»
	'''
	
	def dispatch compileEntityAttributeJpaAnnotations(Entity entity, EntityEntityAttribute entityAttribute) '''
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
		@OneToMany()
		@JoinTable(name = "«entity.entityTableName»_«entityAttribute.entityTableColumnName»_«EntityQuery.getRootEntity(EntityQuery.getEntity(entityAttribute)).entityTableName»" )
		«ELSE»
		@OneToOne()
		@JoinColumn( name = "«entityAttribute.attributeName»_«EntityQuery.getEntity(entityAttribute).entityTableIdColumnName»", referencedColumnName = "«EntityQuery.getRootEntity(EntityQuery.getEntity(entityAttribute)).entityTableIdColumnName»", foreignKey = @ForeignKey( name = "«entityAttribute.attributeName»_«EntityQuery.getEntity(entityAttribute).entityTableIdColumnName»") )
		«ENDIF»
	'''

	def jpaChangeTrackingAttributeGetterSetter(String attributeName, String attributeType) '''
		@Column(name = "«attributeName.entityTableColumnName»")
		«attribute(attributeType, attributeName)»
		«getter(attributeType, attributeName)»
		«changeTrackingSetter(attributeName, attributeName)»
	'''


	def changeTrackingAttributeGetterSetter(Entity entity, EntityAttribute attribute) '''
		«compileEntityAttributeJpaAnnotations(entity, attribute)»
		«attribute(getType(attribute), attribute.name, getInitializer(attribute))»
		«attribute.compileEntityAttributeDescriptor(attribute.parentEntity)»
		«getter(getType(attribute), attribute.name.attributeName)»
		«changeTrackingSetter(getType(attribute), attribute.name.attributeName)»
	'''

	def compileGetAttributeDescriptors(Entity entity) {
		
		var extraAttributes = new ArrayList<AttributeGeneratorFactory.AttributeGenerator>
		 
		if (entity.entityHierarchical) {
			extraAttributes.addAll(hierarchicalEntityAttributes())
		}

		compileGetAttributeDescriptors(entity, extraAttributes)
	}
	
	override getTypeUtils() {
		typeUtils
	}
	
}
