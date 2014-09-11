package io.pelle.mango.dsl.generator.server

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.EntityDescriptor
import io.pelle.mango.client.base.vo.IEntityDescriptor
import io.pelle.mango.client.base.vo.IVOEntity
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.dsl.generator.BaseEntityGenerator
import io.pelle.mango.dsl.generator.util.AttributeUtils
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.query.EntityQuery
import io.pelle.mango.server.base.BaseEntity

class EntityGenerator extends BaseEntityGenerator {

	@Inject
	extension AttributeUtils

	@Inject
	extension NameUtils

	@Inject
	extension TypeUtils

	def compileEntity(Entity entity) '''
		package «getPackageName(entity)»;
		
		import javax.persistence.*;
		
		@Entity
		@Table(name = "«entity.entityTableName»")
		«IF EntityQuery.isExtendedByOtherEntity(entity)»
		@javax.persistence.Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
		@javax.persistence.PrimaryKeyJoinColumn(name="«entity.entityTableIdColumnName»")
		«ENDIF»
		«IF entity.extends != null»
		«ELSE»
		«ENDIF»
		public class «entityName(entity)» extends «IF entity.extends != null»«entityFullQualifiedName(entity.extends)»«ELSEIF entity.
				jvmtype != null»«entity.jvmtype.qualifiedName»«ELSE»«BaseEntity.name»«ENDIF» {
		
				public static final «IEntityDescriptor.name»<«entity.entityFullQualifiedName»> «entity.entityConstantName» = new «EntityDescriptor.name»<«entity.type»>(«entity.typeClass»);
		
				public static «LongAttributeDescriptor.name» «IVOEntity.ID_FIELD_NAME.attributeConstantName» = new «LongAttributeDescriptor.name»(«entity.entityConstantName», "«IVOEntity.ID_FIELD_NAME»");
		
				«entity.attributeDescriptorsFromExtends»
		
				«entity.compileGetAttributeDescriptors»
		
			«IF entity.extends == null»
			@Id
			@Column(name = "«entity.entityTableIdColumnName»")
			@GeneratedValue(strategy = GenerationType.TABLE, generator = "«entity.entityTableIdSequenceName»")
			@SequenceGenerator(name = "«entity.entityTableIdSequenceName»", sequenceName = "«entity.entityTableIdSequenceName»", allocationSize = 1)
			private long id;
			«getterSetter("long", "id")»
			«ENDIF»
			
			«FOR attribute : entity.attributes»
			«attribute.changeTrackingAttributeGetterSetter»
			«ENDFOR»
		
		}
	'''

	// jpa annotations
	def dispatch compileEntityAttributeJpaAnnotations(EntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»")
	'''

	def dispatch compileEntityAttributeJpaAnnotations(BinaryEntityAttribute entityAttribute) '''
		@Column(name = "«entityAttribute.entityTableColumnName»", length = 10 * 1024 * 1024)
		@javax.persistence.Lob
	'''

	def dispatch compileEntityAttributeJpaAnnotations(StringEntityAttribute entityAttribute) '''
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
			@javax.persistence.ElementCollection(fetch=javax.persistence.FetchType.EAGER)
		«ELSE»
			@Column(name = "«entityAttribute.entityTableColumnName»")
		«ENDIF»
	'''

	def dispatch compileEntityAttributeJpaAnnotations(EntityEntityAttribute entityAttribute) '''
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
		@OneToMany()
		«ELSE»
		@OneToOne()
		«ENDIF»
	'''

	def changeTrackingAttributeGetterSetter(EntityAttribute attribute) '''
		«attribute.compileEntityAttributeJpaAnnotations»
		«attribute(getType(attribute), attribute.name, getInitializer(attribute))»
		«attribute.compileEntityAttributeDescriptor(attribute.parentEntity)»
		
		«getter(getType(attribute), attribute.name.attributeName)»
		
		«changeTrackingSetter(getType(attribute), attribute.name.attributeName)»
	'''

}
