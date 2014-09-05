package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.client.base.db.vos.IHierarchicalVO
import io.pelle.mango.client.base.vo.AttributeDescriptor
import io.pelle.mango.client.base.vo.ChangeTrackingArrayList
import io.pelle.mango.client.base.vo.EntityAttributeDescriptor
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.client.base.vo.IBaseVO
import io.pelle.mango.client.base.vo.StringAttributeDescriptor
import io.pelle.mango.client.base.vo.query.SelectQuery
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.BinaryDataType
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanDataType
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.CustomEntityAttribute
import io.pelle.mango.dsl.mango.CustomType
import io.pelle.mango.dsl.mango.Datatype
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityDataType
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EntityType
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.GenericEntityAttribute
import io.pelle.mango.dsl.mango.GenericType
import io.pelle.mango.dsl.mango.GenericTypeDefinition
import io.pelle.mango.dsl.mango.IntegerDataType
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.LongDataType
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.MangoEntityAttribute
import io.pelle.mango.dsl.mango.MangoType
import io.pelle.mango.dsl.mango.MangoTypes
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.SimpleTypeType
import io.pelle.mango.dsl.mango.SimpleTypes
import io.pelle.mango.dsl.mango.StringDataType
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.server.base.IBaseClientEntity
import java.util.List

import static io.pelle.mango.dsl.mango.MangoTypes.*
import static io.pelle.mango.dsl.mango.SimpleTypes.*

class TypeUtils {
	
	@Inject
	extension NameUtils

	@Inject
	extension AttributeUtils

	//-----------------
	// common
	//-----------------
	def String getTypeClassWithCardinality(Cardinality cardinality, String type)
	{
		switch cardinality {
			case Cardinality.ONETOMANY: 
				List.name + ".class"
			default: 
				type + ".class"
		}
	}

	def String getTypeWithCardinality(Cardinality cardinality, String type)
	{
		switch cardinality {
			case Cardinality.ONETOMANY: 
				List.name + "<" + type + ">"
			default: 
				type
		}
	}

	//-----------------
	// SimpleTypes
	//-----------------
	def dispatch String getType(SimpleTypes simpleTypes)
	{
		return simpleTypes.literal
	}
	
	def dispatch String getType(SimpleTypeType simpleTypeType)
	{
		simpleTypeType.type.type
	}
	
	
	//-----------------
	// Datatype
	//-----------------
	def dispatch String getType(Datatype dataType)
	{
		throw new RuntimeException(String.format("Datatype '%s' not supported", dataType.typeClass))
	}

	def dispatch String getTypeClass(Datatype dataType)
	{
		getType(dataType) + ".class"
	}

	def dispatch String getRawType(Datatype dataType)
	{
		getType(dataType)
	}

	def dispatch String getRawTypeClass(Datatype dataType)
	{
		getTypeClass(dataType)
	}
	
	//-----------------
	// entity
	//-----------------
	def dispatch String getType(Entity entity)
	{
		entity.entityFullQualifiedName
	}

	def dispatch String getType(EntityType entityType)
	{
		getTypeWithCardinality(entityType.cardinality, entityType.type.type)
		
	}
	
	def dispatch String getTypeClass(Entity entity)
	{
		getType(entity) + ".class"
	}
	
	def dispatch String getRawType(Entity entity)
	{
		getType(entity)
	}
	
	//-----------------
	// MangoEntityAttribute
	//-----------------
	def dispatch String getType(MangoEntityAttribute entityAttribute)
	{
		if (entityAttribute.generic != null) {
			return entityAttribute.type.type  + "<" + entityAttribute.generic.type + ">"
		}
		else {
			return entityAttribute.type.type 
		}
	}
	
	//-----------------
	// EntityAttribute
	//-----------------
	def compileEntityAttributeDescriptorCommon(EntityAttribute entityAttribute, Entity entity) '''
	public static «IAttributeDescriptor.name»<«getType(entityAttribute)»> «entityAttribute.name.attributeConstantName» = new «AttributeDescriptor.name»<«getType(entityAttribute)»>(«IF entity == null»«entityAttribute.parentEntity.entityConstantName»«ELSE»«entity.entityConstantName»«ENDIF», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)», «getRawTypeClass(entityAttribute)», false, «entityAttribute.naturalKeyOrder»);
	'''	

	def dispatch compileEntityAttributeDescriptor(EntityAttribute entityAttribute, Entity entity) {
		entityAttribute.compileEntityAttributeDescriptorCommon(entity)
	} 

	def dispatch String getType(EntityAttribute entityAttribute)
	{
		throw new RuntimeException(String.format("EntityAttribute '%s' not supported", entityAttribute.typeClass))
	}
	
	def dispatch String getRawType(EntityAttribute entityAttribute)
	{
		getType(entityAttribute)
	}
	

	def dispatch String getTypeClass(EntityAttribute entityAttribute)
	{
		getType(entityAttribute) + ".class"
	}

	def dispatch String getRawTypeClass(EntityAttribute entityAttribute)
	{
		getRawType(entityAttribute) + ".class"
	}
	
	//-----------------
	// CustomType
	//-----------------
	def dispatch String getType(CustomEntityAttribute entityAttribute)
	{
		if (entityAttribute.type != null)
		{
			getTypeWithCardinality(entityAttribute.cardinality, entityAttribute.type)
		}
	}

	def dispatch String getType(CustomType customType)
	{
		getTypeWithCardinality(customType.cardinality, customType.type)
	}
	
	//-----------------
	// MangoTypes
	//-----------------
	def dispatch String getType(MangoTypes mangoTypes)
	{
		switch mangoTypes {
			case IBASEVO: {
				return IBaseVO.name
			}
			case IBASECLIENTVO: {
				return IBaseClientEntity.name
			}
			case SELECTQUERY: {
				return SelectQuery.name
			}
			case IHIERARCHICALVO: {
				return IHierarchicalVO.name
			}
		}
	}

	def dispatch String getType(MangoType mangoType)
	{
		getTypeWithCardinality(mangoType.cardinality, getType(mangoType.type))
	}
	
	//-----------------
	// GenericType
	//-----------------
	def dispatch String getType(GenericType genericType)
	{
		getTypeWithCardinality(genericType.cardinality, genericType.genericTypeDefinition.name)
	}

	def dispatch String getType(GenericTypeDefinition genericTypeDefinition)
	{
		genericTypeDefinition.name
	}

	def dispatch String getType(GenericEntityAttribute genericEntityAttribute)
	{
		genericEntityAttribute.type.type
	}
	
	//-----------------
	// MapEntityAttribute
	//-----------------
	def dispatch String getType(MapEntityAttribute mapEntityAttribute)
	{
		return "java.util.Map<" + mapEntityAttribute.keyType.type + ", " + mapEntityAttribute.valueType.type + ">"
	}
	
	//-----------------
	// EnumerationDataType
	//-----------------
	def dispatch String getType(EnumerationDataType dataType)
	{
		return getType(dataType.enumeration)
	}
	
	def dispatch String getType(Enumeration enumeration)
	{
		var ClientNameUtils clientNameUtils = new ClientNameUtils
		return clientNameUtils.enumerationFullQualifiedName(enumeration)
	}
	
	
	//-----------------
	// BinaryDataType
	//-----------------
	def dispatch String getType(BinaryDataType dataType)
	{
		return "byte[]"
	}

	//-----------------
	// BooleanDataType
	//-----------------
	def dispatch String getType(BooleanDataType dataType)
	{
		return typeof(Boolean).name
	}

	//-----------------
	// StringDataType
	//-----------------
	def dispatch String getType(StringDataType dataType)
	{
		return typeof(String).name
	}

	//-----------------
	// IntegerDataType
	//-----------------
	def dispatch String getType(IntegerDataType dataType)
	{
		return typeof(Integer).name
	}

	//-----------------
	// LongDataType
	//-----------------
	def dispatch String getType(LongDataType dataType)
	{
		return typeof(Long).name
	}

	//-----------------
	// EntityDataType
	//-----------------
	def dispatch String getType(EntityDataType dataType)
	{
		return entityFullQualifiedName(dataType.entity)
	}
	
	//-----------------
	// StringEntityAttribute
	//-----------------
	def dispatch String getType(StringEntityAttribute entityAttribute)
	{
		getTypeWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getTypeClass(StringEntityAttribute entityAttribute)
	{
		getTypeClassWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getRawType(StringEntityAttribute entityAttribute)
	{
		String.name
	}

	def dispatch compileEntityAttributeDescriptor(StringEntityAttribute entityAttribute, Entity entity) '''
	public static «StringAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «StringAttributeDescriptor.name»(«entity.entityConstantName», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)», «entityAttribute.maxLength», «entityAttribute.naturalKeyOrder»);
	'''
	
	def getMaxLength(StringEntityAttribute stringEntityAttribute) 
	{
		if (stringEntityAttribute.type != null)
		{
			return stringEntityAttribute.type.maxLength
		}
		else
		{
			return StringAttributeDescriptor.LENGTH_UNLIMITED
		}
	}

	//-----------------
	// BooleanEntityAttribute
	//-----------------
	def dispatch String getType(BooleanEntityAttribute entityAttribute)
	{
		return typeof(Boolean).name
	}
	
	//-----------------
	// IntegerEntityAttribute
	//-----------------
	def dispatch String getType(IntegerEntityAttribute entityAttribute)
	{
		return Integer.name
	}

	//-----------------
	// BinaryEntityAttribute
	//-----------------
	def dispatch String getType(BinaryEntityAttribute entityAttribute)
	{
		return "byte[]"
	}
	
	//-----------------
	// LongEntityAttribute
	//-----------------
	def dispatch String getType(LongEntityAttribute entityAttribute)
	{
		return Long.name
	}
	
	//-----------------
	// EntityEntityAttribute
	//-----------------
	def dispatch String getType(EntityEntityAttribute entityAttribute)
	{
		getTypeWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getTypeClass(EntityEntityAttribute entityAttribute)
	{
		getTypeClassWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getRawType(EntityEntityAttribute entityAttribute)
	{
		getType(entityAttribute.type)
	}

	def dispatch String getRawTypeClass(EntityEntityAttribute entityAttribute)
	{
		getRawType(entityAttribute.type) + ".class"
	}

	def dispatch compileEntityAttributeDescriptor(EntityEntityAttribute entityAttribute, Entity entity) '''
	«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
	«entityAttribute.compileEntityAttributeDescriptorCommon(null)»
	«ELSE»
public static «EntityAttributeDescriptor.name»<«getRawType(entityAttribute.type)»> «entityAttribute.name.attributeConstantName» = new «EntityAttributeDescriptor.name»<«getRawType(entityAttribute.type)»>(«entityAttribute.parentEntity.entityConstantName», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)»);
	«ENDIF»
'''	

	//-----------------
	// EnumerationEntityAttribute
	//-----------------
	def dispatch String getType(EnumerationEntityAttribute entityAttribute)
	{
		getType(entityAttribute.type)
	}

	def dispatch String getRawType(EnumerationEntityAttribute entityAttribute)
	{
		getType(entityAttribute.type)
	}

	//-------------------------------------------------------------------------
	// initializer 
	//-------------------------------------------------------------------------
	def dispatch String getInitializer(EntityAttribute entityAttribute)
	{
		null
	}

	def dispatch String getInitializer(BooleanEntityAttribute entityAttribute)
	{
		"false"
	}

	def dispatch String getInitializer(StringEntityAttribute entityAttribute)
	{
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY: 
				"new " + ChangeTrackingArrayList.name +  "<" + String.name + ">()"
			default: 
				null
		}
	}

	def dispatch String getInitializer(EntityEntityAttribute entityAttribute)
	{
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY: 
				"new " + ChangeTrackingArrayList.name  +"<" + getRawType(entityAttribute) + ">()"
			default: 
				null
		}
	}
	
	def String parseSimpleTypeFromString(SimpleTypes simpleTypes, String parameterName)
	{
		switch simpleTypes {
			case SimpleTypes::LONG: {
				return "java.lang.Long.parseLong(" + parameterName +")"
			}
			case BIGDECIMAL: {
				return "java.lang.BigDecimal.parse(" + parameterName + ")"
			}
			case BOOLEAN: {
				return "java.lang.Boolean.parseBoolean(" + parameterName + ")"
			}
			case INTEGER: {
				return "java.lang.Integer.parseInt(" + parameterName + ")"		
			}
			case STRING: {
				return parameterName
			}
			default: {
				throw new RuntimeException(String.format("simple type '%s' not implemented", simpleTypes))
			}
		}
	}


	def genericTypeDefinition(GenericTypeDefinition genericTypeDefinition) {
		if (genericTypeDefinition != null)
		{
			return "<" + genericTypeDefinition.name + " extends " + genericTypeDefinition.genericType.type + ">"
		}
	}

}