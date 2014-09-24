package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.AttributeDescriptor
import io.pelle.mango.client.base.vo.ChangeTrackingArrayList
import io.pelle.mango.client.base.vo.EntityAttributeDescriptor
import io.pelle.mango.client.base.vo.EnumerationAttributeDescriptor
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.client.base.vo.StringAttributeDescriptor
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.BinaryDataType
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanDataType
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.Datatype
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityDataType
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.EntityType
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.IntegerDataType
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.LongDataType
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.SimpleTypeType
import io.pelle.mango.dsl.mango.SimpleTypes
import io.pelle.mango.dsl.mango.StringDataType
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.mango.ValueObjectEntityAttribute
import java.math.BigDecimal
import java.util.ArrayList
import java.util.List

class TypeUtils {

	@Inject
	extension ServerNameUtils serverNameUtils

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
		switch (simpleTypes)
		{
			case BIGDECIMAL: {
				return BigDecimal.name
			}
			case BOOLEAN: {
				return Boolean.name
			}
			case INTEGER: {
				return Integer.name
			}
			case LONG: {
				return Long.name
			}
			case STRING: {
				return String.name
			}
			default: {
				throw new RuntimeException("simple type '" + simpleTypes.toString + "'")
			}
			
		}
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

	def String entityVOFullQualifiedName(Entity entity)
	{
		serverNameUtils.entityFullQualifiedName(entity)
	}
	
	//-----------------
	// entity
	//-----------------
	def dispatch String getType(Entity entity)
	{
		entity.entityVOFullQualifiedName
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
	
	def dispatch compileEntityAttributeDescriptor(EnumerationEntityAttribute entityAttribute, Entity entity) '''
	public static «EnumerationAttributeDescriptor.name»<«entityAttribute.type.type»> «entityAttribute.name.attributeConstantName» = new «EnumerationAttributeDescriptor.name»<«entityAttribute.type.type»>(«entity.entityConstantName», "«entityAttribute.name.attributeName»", «entityAttribute.typeClass», «entityAttribute.typeClass», «entityAttribute.naturalKeyOrder»);
	'''
	
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
		return entityVOFullQualifiedName(dataType.entity)
	}

	//-----------------
	// ValueObjectEntityAttribute
	//-----------------
	def dispatch String getType(ValueObjectEntityAttribute entityAttribute)
	{
		getTypeWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getRawType(ValueObjectEntityAttribute entityAttribute)
	{
		entityAttribute.type.type
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
	
	def dispatch String getInitializer(ValueObjectEntityAttribute entityAttribute)
	{
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY: 
				"new " + ArrayList.name +  "<" + entityAttribute.type.type + ">()"
			default: 
				null
		}
	}
		
	def String parseSimpleTypeFromString(SimpleTypes simpleTypes, String parameterName)
	{
		switch simpleTypes {
			case LONG: {
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
}