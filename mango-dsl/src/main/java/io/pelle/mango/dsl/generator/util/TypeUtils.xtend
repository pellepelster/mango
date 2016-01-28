package io.pelle.mango.dsl.generator.util

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.AttributeDescriptor
import io.pelle.mango.client.base.vo.BigDecimalAttributeDescriptor
import io.pelle.mango.client.base.vo.BooleanAttributeDescriptor
import io.pelle.mango.client.base.vo.ChangeTrackingArrayList
import io.pelle.mango.client.base.vo.EntityAttributeDescriptor
import io.pelle.mango.client.base.vo.EnumerationAttributeDescriptor
import io.pelle.mango.client.base.vo.IAttributeDescriptor
import io.pelle.mango.client.base.vo.IntegerAttributeDescriptor
import io.pelle.mango.client.base.vo.LongAttributeDescriptor
import io.pelle.mango.client.base.vo.StringAttributeDescriptor
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.BinaryDataType
import io.pelle.mango.dsl.mango.BinaryEntityAttribute
import io.pelle.mango.dsl.mango.BooleanDataType
import io.pelle.mango.dsl.mango.BooleanEntityAttribute
import io.pelle.mango.dsl.mango.Cardinality
import io.pelle.mango.dsl.mango.Datatype
import io.pelle.mango.dsl.mango.DateDataType
import io.pelle.mango.dsl.mango.DateEntityAttribute
import io.pelle.mango.dsl.mango.DecimalDataType
import io.pelle.mango.dsl.mango.DecimalEntityAttribute
import io.pelle.mango.dsl.mango.DoubleDataType
import io.pelle.mango.dsl.mango.DoubleEntityAttribute
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityAttribute
import io.pelle.mango.dsl.mango.EntityDataType
import io.pelle.mango.dsl.mango.EntityEntityAttribute
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.FloatDataType
import io.pelle.mango.dsl.mango.FloatEntityAttribute
import io.pelle.mango.dsl.mango.IntegerDataType
import io.pelle.mango.dsl.mango.IntegerEntityAttribute
import io.pelle.mango.dsl.mango.LongDataType
import io.pelle.mango.dsl.mango.LongEntityAttribute
import io.pelle.mango.dsl.mango.MapEntityAttribute
import io.pelle.mango.dsl.mango.SimpleTypeType
import io.pelle.mango.dsl.mango.SimpleTypes
import io.pelle.mango.dsl.mango.StringDataType
import io.pelle.mango.dsl.mango.StringEntityAttribute
import io.pelle.mango.dsl.mango.ValueObject
import io.pelle.mango.dsl.mango.ValueObjectEntityAttribute
import java.math.BigDecimal
import java.util.ArrayList
import java.util.Date
import java.util.List

abstract class TypeUtils {

	@Inject
	extension NameUtils

	def String entityVOFullQualifiedName(Entity entity)

	def BaseAttributeUtils getAttributeUtils()

	def AttributeGeneratorFactory getAttributeGeneratorFactory()

	def infoVOEntityAttributes() {

		var List<AttributeGeneratorFactory.AttributeGenerator> infoVOEntityAttributes = newArrayList()

		infoVOEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("createDate", typeof(Date), this, attributeUtils))
		infoVOEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("updateDate", typeof(Date), this, attributeUtils))

		infoVOEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("createUser", typeof(String), this, attributeUtils))
		infoVOEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("updateUser", typeof(String), this, attributeUtils))

		return infoVOEntityAttributes
	}

	//-----------------
	// common
	//-----------------
	def String getTypeClassWithCardinality(Cardinality cardinality, String type) {
		switch cardinality {
			case Cardinality.ONETOMANY:
				List.name + ".class"
			default:
				type + ".class"
		}
	}

	def String getTypeWithCardinality(Cardinality cardinality, String type) {
		switch cardinality {
			case Cardinality.ONETOMANY:
				List.name + "<" + type + ">"
			default:
				type
		}
	}

	def String getMemberTypeWithCardinality(Cardinality cardinality, String type) {
		switch cardinality {
			case Cardinality.ONETOMANY:
				ChangeTrackingArrayList.name + "<" + type + ">"
			default:
				type
		}
	}

	def compileEntityAttributeDescriptor(Class<?> attributeType, String attributeName, Entity parentEntity) '''
		public static «AttributeDescriptor.name»<«attributeType.name»> «attributeName.attributeDescriptorConstantName» = new «AttributeDescriptor.name»<«attributeType.name»>(«parentEntity.metaDescriptorConstantName», "«attributeName»", «attributeType.name».class);
	'''

	//-----------------
	// SimpleTypes
	//-----------------
	def dispatch String getType(SimpleTypes simpleTypes) {
		switch (simpleTypes) {
			case BIGDECIMAL: {
				return BigDecimal.name
			}
			case BOOLEAN: {
				return "boolean"
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

	def dispatch String getWrapperType(SimpleTypes simpleTypes) {
		switch (simpleTypes) {
			case BOOLEAN: {
				return typeof(Boolean).name
			}
			default: {
				return simpleTypes.type
			}
		}
	}

	def dispatch String getMemberType(SimpleTypes simpleTypes) {
		return getType(simpleTypes)
	}

	def dispatch String getType(SimpleTypeType simpleTypeType) {
		simpleTypeType.type.type
	}

	def dispatch String getWrapperType(Object type) {
		type.type
	}


	//-----------------
	// Datatype
	//-----------------
	def dispatch String getType(Datatype dataType) {
		throw new RuntimeException(String.format("Datatype '%s' not supported", dataType.typeClass))
	}

	def dispatch String getMemberType(Datatype valueObjectType) {
		return getType(valueObjectType)
	}
	
	def dispatch String getTypeClass(Datatype dataType) {
		getType(dataType) + ".class"
	}

	def dispatch String getRawType(Datatype dataType) {
		getType(dataType)
	}

	def dispatch String getRawTypeClass(Datatype dataType) {
		getTypeClass(dataType)
	}

	//-----------------
	// entity
	//-----------------
	def dispatch String getType(Entity entity) {
		entity.entityVOFullQualifiedName
	}

	def dispatch String getMemberType(Entity entity) {
		return getType(entity)
	}

	def dispatch String getTypeClass(Entity entity) {
		getType(entity) + ".class"
	}

	def dispatch String getTypeClass(ValueObject valueObject) {
		getType(valueObject) + ".class"
	}

	def dispatch String getRawType(Entity entity) {
		getType(entity)
	}
	
	def dispatch String getRawType(ValueObject valueObject) {
		getType(valueObject)
	}

	def compileEntityAttributeDescriptorCommon(EntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «IAttributeDescriptor.name»<«getType(entityAttribute)»> «entityAttribute.name.attributeConstantName» = new «AttributeDescriptor.name»<«getType(entityAttribute)»>(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)», «getRawTypeClass(entityAttribute)», false, «attributeUtils.naturalKeyOrder(entityAttribute)»);
	'''

	def dispatch compileEntityAttributeDescriptor(EntityAttribute entityAttribute, String metaDescriptorConstantName) {
		entityAttribute.compileEntityAttributeDescriptorCommon(metaDescriptorConstantName)
	}

	def dispatch String getType(EntityAttribute entityAttribute) {
		throw new RuntimeException(String.format("EntityAttribute '%s' not supported", entityAttribute.typeClass))
	}

	def dispatch String getMemberType(EntityAttribute entityAttribute) {
		return getType(entityAttribute)
	}

	def dispatch String getRawType(EntityAttribute entityAttribute) {
		entityAttribute.type
	}

	def dispatch String getRawType(ValueObjectEntityAttribute entityAttribute) {
		entityAttribute.type.type
	}

	def dispatch String getTypeClass(ValueObjectEntityAttribute entityAttribute) {
		getTypeClassWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getRawTypeClass(EntityAttribute entityAttribute) {
		getRawType(entityAttribute) + ".class"
	}

	def dispatch String getRawTypeClass(ValueObjectEntityAttribute entityAttribute) {
		entityAttribute.type.rawTypeClass
	}

	//-----------------
	// MapEntityAttribute
	//-----------------
	def dispatch String getType(MapEntityAttribute entityAttribute) {
		return "java.util.Map<" + entityAttribute.keyType.type + ", " + entityAttribute.valueType.type + ">"
	}

	def dispatch String getMemberType(MapEntityAttribute entityAttribute) {
		return getType(entityAttribute)
	}
	
	def dispatch String getRawTypeClass(MapEntityAttribute entityAttribute) {
		return "java.util.Map.class"
	}

	def dispatch String getTypeClass(MapEntityAttribute entityAttribute) {
		return "java.util.Map.class"
	}

	def dispatch String getInitializer(MapEntityAttribute entityAttribute) {
		return "new java.util.HashMap<" + entityAttribute.keyType.type + ", " + entityAttribute.valueType.type + ">()"
	}

	//-----------------
	// EnumerationDataType
	//-----------------
	def dispatch String getType(EnumerationDataType dataType) {
		return getType(dataType.enumeration)
	}
	
	def dispatch String getMemberType(EnumerationDataType entityAttribute) {
		return getType(entityAttribute)
	}

	def dispatch String getType(Enumeration enumeration) {
		var ClientNameUtils clientNameUtils = new ClientNameUtils
		return clientNameUtils.enumerationFullQualifiedName(enumeration)
	}

	def dispatch compileEntityAttributeDescriptor(EnumerationEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «EnumerationAttributeDescriptor.name»<«entityAttribute.type.type»> «entityAttribute.name.attributeConstantName» = new «EnumerationAttributeDescriptor.name»<«entityAttribute.type.type»>(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»", «entityAttribute.typeClass», «entityAttribute.typeClass», «attributeUtils.naturalKeyOrder(entityAttribute)»);
	'''

	//-----------------
	// binary
	//-----------------
	def dispatch String getType(BinaryDataType dataType) {
		return "byte[]"
	}
	
	def dispatch String getMemberType(BinaryDataType dataType) {
		return getType(dataType)
	}
	
	def dispatch String getType(BinaryEntityAttribute entityAttribute) {
		return "byte[]"
	}

	def dispatch String getTypeClass(EntityAttribute entityAttribute) {
		return entityAttribute.type + ".class"
	}

	//-----------------
	// boolean
	//-----------------
	def dispatch String getType(BooleanDataType dataType) {
		return "boolean"
	}
	
	def dispatch String getWrapperType(BooleanDataType dataType) {
		typeof(Boolean).name
	}
	
	def dispatch String getMemberType(BooleanDataType dataType) {
		return getType(dataType)
	}

	def dispatch String getType(BooleanEntityAttribute entityAttribute) {
		return "boolean"
	}

	def dispatch String getWrapperType(BooleanEntityAttribute dataType) {
		typeof(Boolean).name
	}

	def dispatch compileEntityAttributeDescriptor(BooleanEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «BooleanAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «BooleanAttributeDescriptor.name»(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»");
	'''

	//-----------------
	// decimal
	//-----------------
	def dispatch String getType(DecimalDataType dataType) {
		return typeof(BigDecimal).name
	}

	def dispatch String getMemberType(DecimalDataType dataType) {
		return getType(dataType)
	}

	def dispatch String getType(DecimalEntityAttribute entityAttribute) {
		return BigDecimal.name
	}

	def dispatch compileEntityAttributeDescriptor(DecimalEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «BigDecimalAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «BigDecimalAttributeDescriptor.name»(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»");
	'''

	//-----------------
	// float
	//-----------------
	def dispatch String getType(FloatDataType dataType) {
		return typeof(Float).name
	}

	def dispatch String getMemberType(FloatDataType dataType) {
		return getType(dataType)
	}
	
	def dispatch String getType(FloatEntityAttribute entityAttribute) {
		return Float.name
	}

	//-----------------
	// double
	//-----------------
	def dispatch String getType(DoubleDataType dataType) {
		return typeof(Double).name
	}
	
	def dispatch String getMemberType(DoubleDataType dataType) {
		return getType(dataType)
	}

	def dispatch String getType(DoubleEntityAttribute entityAttribute) {
		return Double.name
	}

	//-----------------
	// date
	//-----------------
	def dispatch String getType(DateDataType dataType) {
		return typeof(Date).name
	}

	def dispatch String getMemberType(DateDataType dataType) {
		return getType(dataType)
	}
	
	def dispatch String getType(DateEntityAttribute entityAttribute) {
		return Date.name
	}

	//-----------------
	// string
	//-----------------
	def dispatch String getType(StringDataType dataType) {
		return typeof(String).name
	}

	def dispatch String getMemberType(StringDataType dataType) {
		return getType(dataType)
	}
	
	def dispatch String getType(StringEntityAttribute entityAttribute) {
		getTypeWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getMemberType(StringEntityAttribute entityAttribute) {
		getMemberTypeWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getTypeClass(StringEntityAttribute entityAttribute) {
		getTypeClassWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	def dispatch String getRawType(StringEntityAttribute entityAttribute) {
		String.name
	}

	def dispatch compileEntityAttributeDescriptor(StringEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «StringAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «StringAttributeDescriptor.name»(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)», «entityAttribute.minLength», «entityAttribute.maxLength», «attributeUtils.naturalKeyOrder(entityAttribute)»);
	'''

	def getMaxLength(StringEntityAttribute stringEntityAttribute) {
		if (stringEntityAttribute.type != null && stringEntityAttribute.type.maxLength > 0) {
			return stringEntityAttribute.type.maxLength
		} else {
			return StringAttributeDescriptor.NO_LENGTH_LIMIT
		}
	}

	def getMinLength(StringEntityAttribute stringEntityAttribute) {
		if (stringEntityAttribute.type != null && stringEntityAttribute.type.minLength > 0) {
			return stringEntityAttribute.type.minLength
		} else {
			return StringAttributeDescriptor.NO_LENGTH_LIMIT
		}
	}

	//-----------------
	// integer
	//-----------------
	def dispatch compileEntityAttributeDescriptor(IntegerEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «IntegerAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «IntegerAttributeDescriptor.name»(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»");
	'''

	def dispatch String getMemberType(IntegerDataType dataType) {
		return getType(dataType)
	}
	
	def dispatch String getType(IntegerDataType dataType) {
		return typeof(Integer).name
	}

	def dispatch String getType(IntegerEntityAttribute entityAttribute) {
		return Integer.name
	}

	//-----------------
	// long
	//-----------------
	def dispatch compileEntityAttributeDescriptor(LongEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		public static «LongAttributeDescriptor.name» «entityAttribute.name.attributeConstantName» = new «LongAttributeDescriptor.name»(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»");
	'''
	
	def dispatch String getMemberType(LongDataType dataType) {
		return getType(dataType)
	}

	def dispatch String getType(LongDataType dataType) {
		return typeof(Long).name
	}

	def dispatch String getType(LongEntityAttribute entityAttribute) {
		return Long.name
	}

	//-----------------
	// EntityDataType
	//-----------------
	def dispatch String getType(EntityDataType dataType) {
		return entityVOFullQualifiedName(dataType.entity)
	}
	
	def dispatch String getMemberType(EntityDataType dataType) {
		return getType(dataType)
	}

	//-----------------
	// ValueObjectEntityAttribute
	//-----------------
	def dispatch String getType(ValueObjectEntityAttribute entityAttribute) {
		getTypeWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}
	
	def dispatch String getMemberType(ValueObjectEntityAttribute entityAttribute) {
		return getType(entityAttribute)
	}

	//-----------------
	// EntityEntityAttribute
	//-----------------
	def dispatch String getType(EntityEntityAttribute entityAttribute) {
		getTypeWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getMemberType(EntityEntityAttribute entityAttribute) {
		getMemberTypeWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getTypeClass(EntityEntityAttribute entityAttribute) {
		getTypeClassWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}

	def dispatch String getRawType(EntityEntityAttribute entityAttribute) {
		getType(entityAttribute.type)
	}

	def dispatch String getRawTypeClass(EntityEntityAttribute entityAttribute) {
		getRawType(entityAttribute.type) + ".class"
	}

	def dispatch compileEntityAttributeDescriptor(EntityEntityAttribute entityAttribute, String metaDescriptorConstantName) '''
		«IF entityAttribute.cardinality == Cardinality.ONETOMANY»
			«entityAttribute.compileEntityAttributeDescriptorCommon(metaDescriptorConstantName)»
		«ELSE»
			public static «EntityAttributeDescriptor.name»<«getRawType(entityAttribute.type)»> «entityAttribute.name.attributeConstantName» = new «EntityAttributeDescriptor.name»<«getRawType(entityAttribute.type)»>(«metaDescriptorConstantName», "«entityAttribute.name.attributeName»", «getTypeClass(entityAttribute)», «attributeUtils.naturalKeyOrder(entityAttribute)»);
		«ENDIF»
	'''

	//-----------------
	// enumeration
	//-----------------
	def dispatch String getType(EnumerationEntityAttribute entityAttribute) {
		getTypeWithCardinality(entityAttribute.cardinality, getType(entityAttribute.type))
	}
	
	def dispatch String getMemberType(EnumerationEntityAttribute entityAttribute) {
		return getType(entityAttribute)
	}

	def dispatch String getRawType(EnumerationEntityAttribute entityAttribute) {
		getType(entityAttribute.type)
	}

	def dispatch String getTypeClass(EnumerationEntityAttribute entityAttribute) {
		getTypeClassWithCardinality(entityAttribute.cardinality, getRawType(entityAttribute))
	}

	//-------------------------------------------------------------------------
	// initializer 
	//-------------------------------------------------------------------------
	def dispatch String getInitializer(EntityAttribute entityAttribute) {
		null
	}

	def dispatch String getInitializer(BooleanEntityAttribute entityAttribute) {
		"false"
	}

	def dispatch String getInitializer(StringEntityAttribute entityAttribute) {
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY:
				"new " + ChangeTrackingArrayList.name + "<" + String.name + ">()"
			default:
				null
		}
	}

	def dispatch String getInitializer(EntityEntityAttribute entityAttribute) {
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY:
				"new " + ChangeTrackingArrayList.name + "<" + getRawType(entityAttribute) + ">()"
			default:
				null
		}
	}

	def dispatch String getInitializer(EnumerationEntityAttribute entityAttribute) {
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY:
				"new " + ArrayList.name + "<" + entityAttribute.type.type + ">()"
			default:
				null
		}
	}

	def dispatch String getInitializer(ValueObjectEntityAttribute entityAttribute) {
		switch entityAttribute.cardinality {
			case Cardinality.ONETOMANY:
				"new " + ArrayList.name + "<" + entityAttribute.type.type + ">()"
			default:
				null
		}
	}

	def String parseSimpleTypeFromString(SimpleTypes simpleTypes, String parameterName) {
		switch simpleTypes {
			case LONG: {
				return "java.lang.Long.parseLong(" + parameterName + ")"
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
