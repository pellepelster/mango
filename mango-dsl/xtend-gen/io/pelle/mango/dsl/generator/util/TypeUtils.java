package io.pelle.mango.dsl.generator.util;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.ChangeTrackingArrayList;
import io.pelle.mango.client.base.vo.EntityAttributeDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.util.AttributeUtils;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.BinaryDataType;
import io.pelle.mango.dsl.mango.BinaryEntityAttribute;
import io.pelle.mango.dsl.mango.BooleanDataType;
import io.pelle.mango.dsl.mango.BooleanEntityAttribute;
import io.pelle.mango.dsl.mango.Cardinality;
import io.pelle.mango.dsl.mango.CustomEntityAttribute;
import io.pelle.mango.dsl.mango.CustomType;
import io.pelle.mango.dsl.mango.Datatype;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityAttributeType;
import io.pelle.mango.dsl.mango.EntityDataType;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;
import io.pelle.mango.dsl.mango.EntityType;
import io.pelle.mango.dsl.mango.Enumeration;
import io.pelle.mango.dsl.mango.EnumerationAttributeType;
import io.pelle.mango.dsl.mango.EnumerationDataType;
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute;
import io.pelle.mango.dsl.mango.Generic;
import io.pelle.mango.dsl.mango.GenericEntityAttribute;
import io.pelle.mango.dsl.mango.GenericType;
import io.pelle.mango.dsl.mango.GenericTypeDefinition;
import io.pelle.mango.dsl.mango.GenericTypeTypes;
import io.pelle.mango.dsl.mango.IntegerDataType;
import io.pelle.mango.dsl.mango.IntegerEntityAttribute;
import io.pelle.mango.dsl.mango.LongDataType;
import io.pelle.mango.dsl.mango.LongEntityAttribute;
import io.pelle.mango.dsl.mango.MangoEntityAttribute;
import io.pelle.mango.dsl.mango.MangoType;
import io.pelle.mango.dsl.mango.MangoTypes;
import io.pelle.mango.dsl.mango.MapEntityAttribute;
import io.pelle.mango.dsl.mango.SimpleTypeType;
import io.pelle.mango.dsl.mango.SimpleTypes;
import io.pelle.mango.dsl.mango.StringDataType;
import io.pelle.mango.dsl.mango.StringEntityAttribute;
import io.pelle.mango.dsl.mango.ValueObject;
import io.pelle.mango.dsl.mango.ValueObjectType;
import io.pelle.mango.server.base.IBaseClientEntity;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class TypeUtils {
  @Inject
  @Extension
  private NameUtils _nameUtils;
  
  @Inject
  @Extension
  private AttributeUtils _attributeUtils;
  
  public String getTypeClassWithCardinality(final Cardinality cardinality, final String type) {
    String _switchResult = null;
    if (cardinality != null) {
      switch (cardinality) {
        case ONETOMANY:
          String _name = List.class.getName();
          _switchResult = (_name + ".class");
          break;
        default:
          _switchResult = (type + ".class");
          break;
      }
    } else {
      _switchResult = (type + ".class");
    }
    return _switchResult;
  }
  
  public String getTypeWithCardinality(final Cardinality cardinality, final String type) {
    String _switchResult = null;
    if (cardinality != null) {
      switch (cardinality) {
        case ONETOMANY:
          String _name = List.class.getName();
          String _plus = (_name + "<");
          String _plus_1 = (_plus + type);
          _switchResult = (_plus_1 + ">");
          break;
        default:
          _switchResult = type;
          break;
      }
    } else {
      _switchResult = type;
    }
    return _switchResult;
  }
  
  protected String _getType(final ValueObjectType valueObjectType) {
    ValueObject _type = valueObjectType.getType();
    return this._nameUtils.voName(_type);
  }
  
  protected String _getType(final SimpleTypes simpleTypes) {
    return simpleTypes.getLiteral();
  }
  
  protected String _getType(final SimpleTypeType simpleTypeType) {
    SimpleTypes _type = simpleTypeType.getType();
    return this.getType(_type);
  }
  
  protected String _getType(final Datatype dataType) {
    String _typeClass = this.getTypeClass(dataType);
    String _format = String.format("Datatype \'%s\' not supported", _typeClass);
    throw new RuntimeException(_format);
  }
  
  protected String _getTypeClass(final Datatype dataType) {
    String _type = this.getType(dataType);
    return (_type + ".class");
  }
  
  protected String _getRawType(final Datatype dataType) {
    return this.getType(dataType);
  }
  
  protected String _getRawTypeClass(final Datatype dataType) {
    return this.getTypeClass(dataType);
  }
  
  protected String _getType(final Entity entity) {
    return this._nameUtils.entityFullQualifiedName(entity);
  }
  
  protected String _getType(final EntityType entityType) {
    Cardinality _cardinality = entityType.getCardinality();
    EntityAttributeType _type = entityType.getType();
    String _type_1 = this.getType(_type);
    return this.getTypeWithCardinality(_cardinality, _type_1);
  }
  
  protected String _getTypeClass(final Entity entity) {
    String _type = this.getType(entity);
    return (_type + ".class");
  }
  
  protected String _getRawType(final Entity entity) {
    return this.getType(entity);
  }
  
  protected String _getType(final MangoEntityAttribute entityAttribute) {
    Generic _generic = entityAttribute.getGeneric();
    boolean _notEquals = (!Objects.equal(_generic, null));
    if (_notEquals) {
      MangoTypes _type = entityAttribute.getType();
      String _type_1 = this.getType(_type);
      String _plus = (_type_1 + "<");
      Generic _generic_1 = entityAttribute.getGeneric();
      String _type_2 = this.getType(_generic_1);
      String _plus_1 = (_plus + _type_2);
      return (_plus_1 + ">");
    } else {
      MangoTypes _type_3 = entityAttribute.getType();
      return this.getType(_type_3);
    }
  }
  
  public CharSequence compileEntityAttributeDescriptorCommon(final EntityAttribute entityAttribute, final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public static ");
    String _name = IAttributeDescriptor.class.getName();
    _builder.append(_name, "");
    _builder.append("<");
    String _type = this.getType(entityAttribute);
    _builder.append(_type, "");
    _builder.append("> ");
    String _name_1 = entityAttribute.getName();
    String _attributeConstantName = this._nameUtils.attributeConstantName(_name_1);
    _builder.append(_attributeConstantName, "");
    _builder.append(" = new ");
    String _name_2 = AttributeDescriptor.class.getName();
    _builder.append(_name_2, "");
    _builder.append("<");
    String _type_1 = this.getType(entityAttribute);
    _builder.append(_type_1, "");
    _builder.append(">(");
    {
      boolean _equals = Objects.equal(entity, null);
      if (_equals) {
        Entity _parentEntity = this._attributeUtils.getParentEntity(entityAttribute);
        String _entityConstantName = this._nameUtils.entityConstantName(_parentEntity);
        _builder.append(_entityConstantName, "");
      } else {
        String _entityConstantName_1 = this._nameUtils.entityConstantName(entity);
        _builder.append(_entityConstantName_1, "");
      }
    }
    _builder.append(", \"");
    String _name_3 = entityAttribute.getName();
    String _attributeName = this._nameUtils.attributeName(_name_3);
    _builder.append(_attributeName, "");
    _builder.append("\", ");
    String _typeClass = this.getTypeClass(entityAttribute);
    _builder.append(_typeClass, "");
    _builder.append(", ");
    String _rawTypeClass = this.getRawTypeClass(entityAttribute);
    _builder.append(_rawTypeClass, "");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _compileEntityAttributeDescriptor(final EntityAttribute entityAttribute, final Entity entity) {
    return this.compileEntityAttributeDescriptorCommon(entityAttribute, entity);
  }
  
  protected String _getType(final EntityAttribute entityAttribute) {
    String _typeClass = this.getTypeClass(entityAttribute);
    String _format = String.format("EntityAttribute \'%s\' not supported", _typeClass);
    throw new RuntimeException(_format);
  }
  
  protected String _getRawType(final EntityAttribute entityAttribute) {
    return this.getType(entityAttribute);
  }
  
  protected String _getTypeClass(final EntityAttribute entityAttribute) {
    String _type = this.getType(entityAttribute);
    return (_type + ".class");
  }
  
  protected String _getRawTypeClass(final EntityAttribute entityAttribute) {
    String _rawType = this.getRawType(entityAttribute);
    return (_rawType + ".class");
  }
  
  protected String _getType(final CustomEntityAttribute entityAttribute) {
    String _xifexpression = null;
    String _type = entityAttribute.getType();
    boolean _notEquals = (!Objects.equal(_type, null));
    if (_notEquals) {
      Cardinality _cardinality = entityAttribute.getCardinality();
      String _type_1 = entityAttribute.getType();
      _xifexpression = this.getTypeWithCardinality(_cardinality, _type_1);
    }
    return _xifexpression;
  }
  
  protected String _getType(final CustomType customType) {
    Cardinality _cardinality = customType.getCardinality();
    String _type = customType.getType();
    return this.getTypeWithCardinality(_cardinality, _type);
  }
  
  protected String _getType(final MangoTypes mangoTypes) {
    if (mangoTypes != null) {
      switch (mangoTypes) {
        case IBASEVO:
          return IBaseVO.class.getName();
        case IBASECLIENTVO:
          return IBaseClientEntity.class.getName();
        case SELECTQUERY:
          return SelectQuery.class.getName();
        case IHIERARCHICALVO:
          return IHierarchicalVO.class.getName();
        default:
          break;
      }
    }
    return null;
  }
  
  protected String _getType(final MangoType mangoType) {
    Cardinality _cardinality = mangoType.getCardinality();
    MangoTypes _type = mangoType.getType();
    String _type_1 = this.getType(_type);
    return this.getTypeWithCardinality(_cardinality, _type_1);
  }
  
  protected String _getType(final GenericType genericType) {
    Cardinality _cardinality = genericType.getCardinality();
    GenericTypeDefinition _genericTypeDefinition = genericType.getGenericTypeDefinition();
    String _name = _genericTypeDefinition.getName();
    return this.getTypeWithCardinality(_cardinality, _name);
  }
  
  protected String _getType(final GenericTypeDefinition genericTypeDefinition) {
    return genericTypeDefinition.getName();
  }
  
  protected String _getType(final GenericEntityAttribute genericEntityAttribute) {
    GenericTypeDefinition _type = genericEntityAttribute.getType();
    return this.getType(_type);
  }
  
  protected String _getType(final MapEntityAttribute mapEntityAttribute) {
    SimpleTypes _keyType = mapEntityAttribute.getKeyType();
    String _type = this.getType(_keyType);
    String _plus = ("java.util.Map<" + _type);
    String _plus_1 = (_plus + ", ");
    SimpleTypes _valueType = mapEntityAttribute.getValueType();
    String _type_1 = this.getType(_valueType);
    String _plus_2 = (_plus_1 + _type_1);
    return (_plus_2 + ">");
  }
  
  protected String _getType(final EnumerationDataType dataType) {
    Enumeration _enumeration = dataType.getEnumeration();
    return this.getType(_enumeration);
  }
  
  protected String _getType(final Enumeration enumeration) {
    ClientNameUtils clientNameUtils = new ClientNameUtils();
    return clientNameUtils.enumerationFullQualifiedName(enumeration);
  }
  
  protected String _getType(final BinaryDataType dataType) {
    return "byte[]";
  }
  
  protected String _getType(final BooleanDataType dataType) {
    return Boolean.class.getName();
  }
  
  protected String _getType(final StringDataType dataType) {
    return String.class.getName();
  }
  
  protected String _getType(final IntegerDataType dataType) {
    return Integer.class.getName();
  }
  
  protected String _getType(final LongDataType dataType) {
    return Long.class.getName();
  }
  
  protected String _getType(final EntityDataType dataType) {
    Entity _entity = dataType.getEntity();
    return this._nameUtils.entityFullQualifiedName(_entity);
  }
  
  protected String _getType(final StringEntityAttribute entityAttribute) {
    Cardinality _cardinality = entityAttribute.getCardinality();
    String _rawType = this.getRawType(entityAttribute);
    return this.getTypeWithCardinality(_cardinality, _rawType);
  }
  
  protected String _getTypeClass(final StringEntityAttribute entityAttribute) {
    Cardinality _cardinality = entityAttribute.getCardinality();
    String _rawType = this.getRawType(entityAttribute);
    return this.getTypeClassWithCardinality(_cardinality, _rawType);
  }
  
  protected String _getRawType(final StringEntityAttribute entityAttribute) {
    return String.class.getName();
  }
  
  protected CharSequence _compileEntityAttributeDescriptor(final StringEntityAttribute entityAttribute, final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public static ");
    String _name = StringAttributeDescriptor.class.getName();
    _builder.append(_name, "");
    _builder.append(" ");
    String _name_1 = entityAttribute.getName();
    String _attributeConstantName = this._nameUtils.attributeConstantName(_name_1);
    _builder.append(_attributeConstantName, "");
    _builder.append(" = new ");
    String _name_2 = StringAttributeDescriptor.class.getName();
    _builder.append(_name_2, "");
    _builder.append("(");
    String _entityConstantName = this._nameUtils.entityConstantName(entity);
    _builder.append(_entityConstantName, "");
    _builder.append(", \"");
    String _name_3 = entityAttribute.getName();
    String _attributeName = this._nameUtils.attributeName(_name_3);
    _builder.append(_attributeName, "");
    _builder.append("\", ");
    String _typeClass = this.getTypeClass(entityAttribute);
    _builder.append(_typeClass, "");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected String _getType(final BooleanEntityAttribute entityAttribute) {
    return Boolean.class.getName();
  }
  
  protected String _getType(final IntegerEntityAttribute entityAttribute) {
    return Integer.class.getName();
  }
  
  protected String _getType(final BinaryEntityAttribute entityAttribute) {
    return "byte[]";
  }
  
  protected String _getType(final LongEntityAttribute entityAttribute) {
    return Long.class.getName();
  }
  
  protected String _getType(final EntityEntityAttribute entityAttribute) {
    Cardinality _cardinality = entityAttribute.getCardinality();
    EntityAttributeType _type = entityAttribute.getType();
    String _type_1 = this.getType(_type);
    return this.getTypeWithCardinality(_cardinality, _type_1);
  }
  
  protected String _getTypeClass(final EntityEntityAttribute entityAttribute) {
    Cardinality _cardinality = entityAttribute.getCardinality();
    EntityAttributeType _type = entityAttribute.getType();
    String _type_1 = this.getType(_type);
    return this.getTypeClassWithCardinality(_cardinality, _type_1);
  }
  
  protected String _getRawType(final EntityEntityAttribute entityAttribute) {
    EntityAttributeType _type = entityAttribute.getType();
    return this.getType(_type);
  }
  
  protected String _getRawTypeClass(final EntityEntityAttribute entityAttribute) {
    EntityAttributeType _type = entityAttribute.getType();
    String _rawType = this.getRawType(_type);
    return (_rawType + ".class");
  }
  
  protected CharSequence _compileEntityAttributeDescriptor(final EntityEntityAttribute entityAttribute, final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Cardinality _cardinality = entityAttribute.getCardinality();
      boolean _equals = Objects.equal(_cardinality, Cardinality.ONETOMANY);
      if (_equals) {
        CharSequence _compileEntityAttributeDescriptorCommon = this.compileEntityAttributeDescriptorCommon(entityAttribute, null);
        _builder.append(_compileEntityAttributeDescriptorCommon, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("public static ");
        String _name = EntityAttributeDescriptor.class.getName();
        _builder.append(_name, "");
        _builder.append("<");
        EntityAttributeType _type = entityAttribute.getType();
        String _rawType = this.getRawType(_type);
        _builder.append(_rawType, "");
        _builder.append("> ");
        String _name_1 = entityAttribute.getName();
        String _attributeConstantName = this._nameUtils.attributeConstantName(_name_1);
        _builder.append(_attributeConstantName, "");
        _builder.append(" = new ");
        String _name_2 = EntityAttributeDescriptor.class.getName();
        _builder.append(_name_2, "");
        _builder.append("<");
        EntityAttributeType _type_1 = entityAttribute.getType();
        String _rawType_1 = this.getRawType(_type_1);
        _builder.append(_rawType_1, "");
        _builder.append(">(");
        Entity _parentEntity = this._attributeUtils.getParentEntity(entityAttribute);
        String _entityConstantName = this._nameUtils.entityConstantName(_parentEntity);
        _builder.append(_entityConstantName, "");
        _builder.append(", \"");
        String _name_3 = entityAttribute.getName();
        String _attributeName = this._nameUtils.attributeName(_name_3);
        _builder.append(_attributeName, "");
        _builder.append("\", ");
        String _typeClass = this.getTypeClass(entityAttribute);
        _builder.append(_typeClass, "");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected String _getType(final EnumerationEntityAttribute entityAttribute) {
    EnumerationAttributeType _type = entityAttribute.getType();
    return this.getType(_type);
  }
  
  protected String _getRawType(final EnumerationEntityAttribute entityAttribute) {
    EnumerationAttributeType _type = entityAttribute.getType();
    return this.getType(_type);
  }
  
  protected String _getInitializer(final EntityAttribute entityAttribute) {
    return null;
  }
  
  protected String _getInitializer(final BooleanEntityAttribute entityAttribute) {
    return "false";
  }
  
  protected String _getInitializer(final StringEntityAttribute entityAttribute) {
    String _switchResult = null;
    Cardinality _cardinality = entityAttribute.getCardinality();
    if (_cardinality != null) {
      switch (_cardinality) {
        case ONETOMANY:
          String _name = ChangeTrackingArrayList.class.getName();
          String _plus = ("new " + _name);
          String _plus_1 = (_plus + "<");
          String _name_1 = String.class.getName();
          String _plus_2 = (_plus_1 + _name_1);
          _switchResult = (_plus_2 + ">()");
          break;
        default:
          _switchResult = null;
          break;
      }
    } else {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  protected String _getInitializer(final EntityEntityAttribute entityAttribute) {
    String _switchResult = null;
    Cardinality _cardinality = entityAttribute.getCardinality();
    if (_cardinality != null) {
      switch (_cardinality) {
        case ONETOMANY:
          String _name = ChangeTrackingArrayList.class.getName();
          String _plus = ("new " + _name);
          String _plus_1 = (_plus + "<");
          String _rawType = this.getRawType(entityAttribute);
          String _plus_2 = (_plus_1 + _rawType);
          _switchResult = (_plus_2 + ">()");
          break;
        default:
          _switchResult = null;
          break;
      }
    } else {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  /**
   * }
   * def parseSimpleTypeFromString(EnumerationType EnumerationType, String parameterName) FOR EnumerationType-» '''
   * «fullQualifiedEntityName(this.type)-».valueOf(«parameterName»)
   * '''
   */
  public String parseSimpleTypeFromString(final SimpleTypes simpleTypes, final String parameterName) {
    if (simpleTypes != null) {
      switch (simpleTypes) {
        case LONG:
          return (("java.lang.Long.parseLong(" + parameterName) + ")");
        case BIGDECIMAL:
          return (("java.lang.BigDecimal.parse(" + parameterName) + ")");
        case BOOLEAN:
          return (("java.lang.Boolean.parseBoolean(" + parameterName) + ")");
        case INTEGER:
          return (("java.lang.Integer.parseInt(" + parameterName) + ")");
        case STRING:
          return parameterName;
        default:
          String _format = String.format("simple type \'%s\' not implemented", simpleTypes);
          throw new RuntimeException(_format);
      }
    } else {
      String _format = String.format("simple type \'%s\' not implemented", simpleTypes);
      throw new RuntimeException(_format);
    }
  }
  
  public String genericTypeDefinition(final GenericTypeDefinition genericTypeDefinition) {
    boolean _notEquals = (!Objects.equal(genericTypeDefinition, null));
    if (_notEquals) {
      String _name = genericTypeDefinition.getName();
      String _plus = ("<" + _name);
      String _plus_1 = (_plus + " extends ");
      GenericTypeTypes _genericType = genericTypeDefinition.getGenericType();
      String _type = this.getType(_genericType);
      String _plus_2 = (_plus_1 + _type);
      return (_plus_2 + ">");
    }
    return null;
  }
  
  public String getType(final Object dataType) {
    if (dataType instanceof BinaryDataType) {
      return _getType((BinaryDataType)dataType);
    } else if (dataType instanceof BinaryEntityAttribute) {
      return _getType((BinaryEntityAttribute)dataType);
    } else if (dataType instanceof BooleanDataType) {
      return _getType((BooleanDataType)dataType);
    } else if (dataType instanceof BooleanEntityAttribute) {
      return _getType((BooleanEntityAttribute)dataType);
    } else if (dataType instanceof EnumerationDataType) {
      return _getType((EnumerationDataType)dataType);
    } else if (dataType instanceof EnumerationEntityAttribute) {
      return _getType((EnumerationEntityAttribute)dataType);
    } else if (dataType instanceof IntegerDataType) {
      return _getType((IntegerDataType)dataType);
    } else if (dataType instanceof IntegerEntityAttribute) {
      return _getType((IntegerEntityAttribute)dataType);
    } else if (dataType instanceof LongEntityAttribute) {
      return _getType((LongEntityAttribute)dataType);
    } else if (dataType instanceof MapEntityAttribute) {
      return _getType((MapEntityAttribute)dataType);
    } else if (dataType instanceof StringDataType) {
      return _getType((StringDataType)dataType);
    } else if (dataType instanceof StringEntityAttribute) {
      return _getType((StringEntityAttribute)dataType);
    } else if (dataType instanceof EntityDataType) {
      return _getType((EntityDataType)dataType);
    } else if (dataType instanceof EntityEntityAttribute) {
      return _getType((EntityEntityAttribute)dataType);
    } else if (dataType instanceof CustomEntityAttribute) {
      return _getType((CustomEntityAttribute)dataType);
    } else if (dataType instanceof CustomType) {
      return _getType((CustomType)dataType);
    } else if (dataType instanceof Datatype) {
      return _getType((Datatype)dataType);
    } else if (dataType instanceof Entity) {
      return _getType((Entity)dataType);
    } else if (dataType instanceof EntityAttribute) {
      return _getType((EntityAttribute)dataType);
    } else if (dataType instanceof EntityType) {
      return _getType((EntityType)dataType);
    } else if (dataType instanceof Enumeration) {
      return _getType((Enumeration)dataType);
    } else if (dataType instanceof GenericEntityAttribute) {
      return _getType((GenericEntityAttribute)dataType);
    } else if (dataType instanceof GenericType) {
      return _getType((GenericType)dataType);
    } else if (dataType instanceof MangoEntityAttribute) {
      return _getType((MangoEntityAttribute)dataType);
    } else if (dataType instanceof MangoType) {
      return _getType((MangoType)dataType);
    } else if (dataType instanceof SimpleTypeType) {
      return _getType((SimpleTypeType)dataType);
    } else if (dataType instanceof ValueObjectType) {
      return _getType((ValueObjectType)dataType);
    } else if (dataType instanceof GenericTypeDefinition) {
      return _getType((GenericTypeDefinition)dataType);
    } else if (dataType instanceof LongDataType) {
      return _getType((LongDataType)dataType);
    } else if (dataType instanceof MangoTypes) {
      return _getType((MangoTypes)dataType);
    } else if (dataType instanceof SimpleTypes) {
      return _getType((SimpleTypes)dataType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dataType).toString());
    }
  }
  
  public String getTypeClass(final EObject entityAttribute) {
    if (entityAttribute instanceof StringEntityAttribute) {
      return _getTypeClass((StringEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof EntityEntityAttribute) {
      return _getTypeClass((EntityEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof Datatype) {
      return _getTypeClass((Datatype)entityAttribute);
    } else if (entityAttribute instanceof Entity) {
      return _getTypeClass((Entity)entityAttribute);
    } else if (entityAttribute instanceof EntityAttribute) {
      return _getTypeClass((EntityAttribute)entityAttribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute).toString());
    }
  }
  
  public String getRawType(final EObject entityAttribute) {
    if (entityAttribute instanceof EnumerationEntityAttribute) {
      return _getRawType((EnumerationEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof StringEntityAttribute) {
      return _getRawType((StringEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof EntityEntityAttribute) {
      return _getRawType((EntityEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof Datatype) {
      return _getRawType((Datatype)entityAttribute);
    } else if (entityAttribute instanceof Entity) {
      return _getRawType((Entity)entityAttribute);
    } else if (entityAttribute instanceof EntityAttribute) {
      return _getRawType((EntityAttribute)entityAttribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute).toString());
    }
  }
  
  public String getRawTypeClass(final EObject entityAttribute) {
    if (entityAttribute instanceof EntityEntityAttribute) {
      return _getRawTypeClass((EntityEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof Datatype) {
      return _getRawTypeClass((Datatype)entityAttribute);
    } else if (entityAttribute instanceof EntityAttribute) {
      return _getRawTypeClass((EntityAttribute)entityAttribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute).toString());
    }
  }
  
  public CharSequence compileEntityAttributeDescriptor(final EntityAttribute entityAttribute, final Entity entity) {
    if (entityAttribute instanceof StringEntityAttribute) {
      return _compileEntityAttributeDescriptor((StringEntityAttribute)entityAttribute, entity);
    } else if (entityAttribute instanceof EntityEntityAttribute) {
      return _compileEntityAttributeDescriptor((EntityEntityAttribute)entityAttribute, entity);
    } else if (entityAttribute != null) {
      return _compileEntityAttributeDescriptor(entityAttribute, entity);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute, entity).toString());
    }
  }
  
  public String getInitializer(final EntityAttribute entityAttribute) {
    if (entityAttribute instanceof BooleanEntityAttribute) {
      return _getInitializer((BooleanEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof StringEntityAttribute) {
      return _getInitializer((StringEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof EntityEntityAttribute) {
      return _getInitializer((EntityEntityAttribute)entityAttribute);
    } else if (entityAttribute != null) {
      return _getInitializer(entityAttribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute).toString());
    }
  }
}
