package io.pelle.mango.dsl.generator.client;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.client.base.db.vos.NaturalKey;
import io.pelle.mango.client.base.vo.BaseVO;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.LongAttributeDescriptor;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.AttributeUtils;
import io.pelle.mango.dsl.generator.BaseEntityGenerator;
import io.pelle.mango.dsl.generator.EntityUtils;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.client.ClientTypeUtils;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.Enumeration;
import io.pelle.mango.dsl.mango.ValueObject;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class VOGenerator extends BaseEntityGenerator {
  @Inject
  @Extension
  private AttributeUtils _attributeUtils;
  
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  @Inject
  @Extension
  private ClientTypeUtils _clientTypeUtils;
  
  @Inject
  @Extension
  private EntityUtils _entityUtils;
  
  public CharSequence compileVO(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._clientNameUtils.getPackageName(entity);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@SuppressWarnings(\"serial\")");
    _builder.newLine();
    _builder.append("public class ");
    String _voName = this._clientNameUtils.voName(entity);
    _builder.append(_voName, "");
    _builder.append(" extends ");
    {
      Entity _extends = entity.getExtends();
      boolean _notEquals = (!Objects.equal(_extends, null));
      if (_notEquals) {
        Entity _extends_1 = entity.getExtends();
        String _voFullQualifiedName = this._clientNameUtils.voFullQualifiedName(_extends_1);
        _builder.append(_voFullQualifiedName, "");
      } else {
        String _name = BaseVO.class.getName();
        _builder.append(_name, "");
      }
    }
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final ");
    String _name_1 = IEntityDescriptor.class.getName();
    _builder.append(_name_1, "\t");
    _builder.append("<");
    String _voFullQualifiedName_1 = this._clientNameUtils.voFullQualifiedName(entity);
    _builder.append(_voFullQualifiedName_1, "\t");
    _builder.append("> ");
    String _entityConstantName = this._clientNameUtils.entityConstantName(entity);
    _builder.append(_entityConstantName, "\t");
    _builder.append(" = new ");
    String _name_2 = EntityDescriptor.class.getName();
    _builder.append(_name_2, "\t");
    _builder.append("<");
    String _type = this._clientTypeUtils.getType(entity);
    _builder.append(_type, "\t");
    _builder.append(">(");
    String _typeClass = this._clientTypeUtils.getTypeClass(entity);
    _builder.append(_typeClass, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _compileGetAttributeDescriptors = this.compileGetAttributeDescriptors(entity);
    _builder.append(_compileGetAttributeDescriptors, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static ");
    String _name_3 = LongAttributeDescriptor.class.getName();
    _builder.append(_name_3, "\t");
    _builder.append(" ");
    String _attributeConstantName = this._clientNameUtils.attributeConstantName(IVOEntity.ID_FIELD_NAME);
    _builder.append(_attributeConstantName, "\t");
    _builder.append(" = new ");
    String _name_4 = LongAttributeDescriptor.class.getName();
    _builder.append(_name_4, "\t");
    _builder.append("(");
    String _entityConstantName_1 = this._clientNameUtils.entityConstantName(entity);
    _builder.append(_entityConstantName_1, "\t");
    _builder.append(", \"");
    _builder.append(IVOEntity.ID_FIELD_NAME, "\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      Entity _extends_2 = entity.getExtends();
      boolean _notEquals_1 = (!Objects.equal(_extends_2, null));
      if (_notEquals_1) {
        {
          Entity _extends_3 = entity.getExtends();
          EList<EntityAttribute> _attributes = _extends_3.getAttributes();
          for(final EntityAttribute attribute : _attributes) {
            _builder.append("\t");
            Entity _parentEntity = this._attributeUtils.getParentEntity(attribute);
            CharSequence _compileEntityAttributeDescriptor = this._clientTypeUtils.compileEntityAttributeDescriptor(attribute, _parentEntity);
            _builder.append(_compileEntityAttributeDescriptor, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private long id;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _terSetter = this._attributeUtils.getterSetter("long", IVOEntity.ID_FIELD_NAME);
    _builder.append(_terSetter, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<EntityAttribute> _attributes_1 = entity.getAttributes();
      for(final EntityAttribute attribute_1 : _attributes_1) {
        _builder.append("\t");
        CharSequence _compileVOAttribute = this.compileVOAttribute(attribute_1);
        _builder.append(_compileVOAttribute, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _genericVOGetter = this.genericVOGetter(entity);
    _builder.append(_genericVOGetter, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _genericVOSetter = this.genericVOSetter(entity);
    _builder.append(_genericVOSetter, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<EntityAttribute> _naturalKeyAttributes = entity.getNaturalKeyAttributes();
      boolean _isEmpty = _naturalKeyAttributes.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("\t");
        _builder.append("@java.lang.Override");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public String getNaturalKey() ");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("java.lang.StringBuffer sb = new java.lang.StringBuffer();");
        _builder.newLine();
        {
          EList<EntityAttribute> _naturalKeyAttributes_1 = entity.getNaturalKeyAttributes();
          boolean _hasElements = false;
          for(final EntityAttribute naturalKeyAttribute : _naturalKeyAttributes_1) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("sb.append(\", \");", "\t\t");
            }
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("sb.append(this.get");
            String _name_5 = naturalKeyAttribute.getName();
            String _firstUpper = StringExtensions.toFirstUpper(_name_5);
            _builder.append(_firstUpper, "\t\t");
            _builder.append("());");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return sb.toString();");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileValueObject(final ValueObject valueObject) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._clientNameUtils.getPackageName(valueObject);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _voName = this._clientNameUtils.voName(valueObject);
    _builder.append(_voName, "");
    _builder.append(" ");
    {
      ValueObject _extends = valueObject.getExtends();
      boolean _notEquals = (!Objects.equal(_extends, null));
      if (_notEquals) {
        _builder.append("extends ");
        ValueObject _extends_1 = valueObject.getExtends();
        String _voFullQualifiedName = this._clientNameUtils.voFullQualifiedName(_extends_1);
        _builder.append(_voFullQualifiedName, "");
      }
    }
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _voName_1 = this._clientNameUtils.voName(valueObject);
    _builder.append(_voName_1, "\t");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      EList<EntityAttribute> _attributes = valueObject.getAttributes();
      for(final EntityAttribute attribute : _attributes) {
        _builder.append("\t");
        CharSequence _compileValueObjectAttribute = this.compileValueObjectAttribute(attribute);
        _builder.append(_compileValueObjectAttribute, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileEnumeration(final Enumeration enumeration) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._clientNameUtils.getPackageName(enumeration);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public enum ");
    String _enumerationName = this._clientNameUtils.enumerationName(enumeration);
    _builder.append(_enumerationName, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<String> _enumerationValues = enumeration.getEnumerationValues();
      boolean _hasElements = false;
      for(final String enumerationValue : _enumerationValues) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t");
        }
        _builder.append("\t");
        String _upperCase = enumerationValue.toUpperCase();
        _builder.append(_upperCase, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public Object compileValueObjectConstructor(final List<EntityAttribute> attributes) {
    return null;
  }
  
  public CharSequence compileValueObjectAttribute(final EntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    String _type = this._clientTypeUtils.getType(entityAttribute);
    String _name = entityAttribute.getName();
    String _initializer = this._clientTypeUtils.getInitializer(entityAttribute);
    CharSequence _attribute = this._attributeUtils.attribute(_type, _name, _initializer);
    _builder.append(_attribute, "");
    _builder.newLineIfNotEmpty();
    String _type_1 = this._clientTypeUtils.getType(entityAttribute);
    String _name_1 = entityAttribute.getName();
    String _attributeName = this._clientNameUtils.attributeName(_name_1);
    CharSequence _ter = this._attributeUtils.getter(_type_1, _attributeName);
    _builder.append(_ter, "");
    _builder.newLineIfNotEmpty();
    String _type_2 = this._clientTypeUtils.getType(entityAttribute);
    String _name_2 = entityAttribute.getName();
    String _attributeName_1 = this._clientNameUtils.attributeName(_name_2);
    CharSequence _setter = this._attributeUtils.setter(_type_2, _attributeName_1);
    _builder.append(_setter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence compileVOAttribute(final EntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _changeTrackingAttributeGetterSetter = this.changeTrackingAttributeGetterSetter(entityAttribute);
    _builder.append(_changeTrackingAttributeGetterSetter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence changeTrackingAttributeGetterSetter(final EntityAttribute attribute) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Entity _parentEntity = this._attributeUtils.getParentEntity(attribute);
      EList<EntityAttribute> _naturalKeyAttributes = _parentEntity.getNaturalKeyAttributes();
      boolean _contains = _naturalKeyAttributes.contains(attribute);
      if (_contains) {
        _builder.append("@");
        String _name = NaturalKey.class.getName();
        _builder.append(_name, "");
        _builder.append("( order = ");
        Entity _parentEntity_1 = ModelUtil.getParentEntity(attribute);
        EList<EntityAttribute> _naturalKeyAttributes_1 = _parentEntity_1.getNaturalKeyAttributes();
        int _indexOf = _naturalKeyAttributes_1.indexOf(attribute);
        _builder.append(_indexOf, "");
        _builder.append(")");
      }
    }
    _builder.newLineIfNotEmpty();
    String _type = this._clientTypeUtils.getType(attribute);
    String _name_1 = attribute.getName();
    String _initializer = this._clientTypeUtils.getInitializer(attribute);
    CharSequence _attribute = this._attributeUtils.attribute(_type, _name_1, _initializer);
    _builder.append(_attribute, "");
    _builder.newLineIfNotEmpty();
    {
      Entity _parentEntity_2 = this._attributeUtils.getParentEntity(attribute);
      boolean _isExtendedByOtherEntity = this._entityUtils.isExtendedByOtherEntity(_parentEntity_2);
      boolean _not = (!_isExtendedByOtherEntity);
      if (_not) {
        Entity _parentEntity_3 = this._attributeUtils.getParentEntity(attribute);
        CharSequence _compileEntityAttributeDescriptor = this._clientTypeUtils.compileEntityAttributeDescriptor(attribute, _parentEntity_3);
        _builder.append(_compileEntityAttributeDescriptor, "");
        _builder.newLineIfNotEmpty();
      }
    }
    String _type_1 = this._clientTypeUtils.getType(attribute);
    String _name_2 = attribute.getName();
    String _attributeName = this._clientNameUtils.attributeName(_name_2);
    CharSequence _ter = this._attributeUtils.getter(_type_1, _attributeName);
    _builder.append(_ter, "");
    _builder.newLineIfNotEmpty();
    String _type_2 = this._clientTypeUtils.getType(attribute);
    String _name_3 = attribute.getName();
    String _attributeName_1 = this._clientNameUtils.attributeName(_name_3);
    CharSequence _changeTrackingSetter = this._attributeUtils.changeTrackingSetter(_type_2, _attributeName_1);
    _builder.append(_changeTrackingSetter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence genericVOGetter(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public Object get(java.lang.String name) {");
    _builder.newLine();
    _builder.newLine();
    {
      EList<EntityAttribute> _attributes = entity.getAttributes();
      for(final EntityAttribute attribute : _attributes) {
        _builder.append("\t");
        _builder.append("if (\"");
        String _name = attribute.getName();
        _builder.append(_name, "\t");
        _builder.append("\".equals(name))");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return this.");
        String _name_1 = attribute.getName();
        _builder.append(_name_1, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return super.get(name);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence genericVOSetter(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public void set(java.lang.String name, java.lang.Object value) {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("getChangeTracker().addChange(name, value);");
    _builder.newLine();
    _builder.newLine();
    {
      EList<EntityAttribute> _attributes = entity.getAttributes();
      for(final EntityAttribute attribute : _attributes) {
        _builder.append("\t");
        _builder.append("if (\"");
        String _name = attribute.getName();
        _builder.append(_name, "\t");
        _builder.append("\".equals(name))");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("set");
        String _name_1 = attribute.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_1);
        _builder.append(_firstUpper, "\t\t");
        _builder.append("((");
        String _type = this._clientTypeUtils.getType(attribute);
        _builder.append(_type, "\t\t");
        _builder.append(") value);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("super.set(name, value);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
