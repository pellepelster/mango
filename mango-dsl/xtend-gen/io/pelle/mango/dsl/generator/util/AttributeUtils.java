package io.pelle.mango.dsl.generator.util;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class AttributeUtils {
  @Inject
  @Extension
  private NameUtils _nameUtils;
  
  public boolean isNaturalKeyAttribute(final EntityAttribute entityAttribute) {
    Entity _parentEntity = this.getParentEntity(entityAttribute);
    EList<EntityAttribute> _naturalKeyAttributes = _parentEntity.getNaturalKeyAttributes();
    return _naturalKeyAttributes.contains(entityAttribute);
  }
  
  public Entity getParentEntity(final EntityAttribute entityAttribute) {
    EObject _eContainer = entityAttribute.eContainer();
    return ((Entity) _eContainer);
  }
  
  public CharSequence attributeGetterSetter(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _attribute = this.attribute(attributeType, attributeName);
    _builder.append(_attribute, "");
    _builder.newLineIfNotEmpty();
    CharSequence _ter = this.getter(attributeType, attributeName);
    _builder.append(_ter, "");
    _builder.newLineIfNotEmpty();
    CharSequence _setter = this.setter(attributeType, attributeName);
    _builder.append(_setter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getterSetter(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _ter = this.getter(attributeType, attributeName);
    _builder.append(_ter, "");
    _builder.newLineIfNotEmpty();
    CharSequence _setter = this.setter(attributeType, attributeName);
    _builder.append(_setter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence attribute(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _attribute = this.attribute(attributeType, attributeName, null);
    _builder.append(_attribute, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence attribute(final String attributeType, final String attributeName, final String attributeInitializer) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private ");
    _builder.append(attributeType, "");
    _builder.append(" ");
    String _attributeName = this._nameUtils.attributeName(attributeName);
    _builder.append(_attributeName, "");
    {
      boolean _notEquals = (!Objects.equal(attributeInitializer, null));
      if (_notEquals) {
        _builder.append(" = ");
        _builder.append(attributeInitializer, "");
      }
    }
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getter(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    _builder.append(attributeType, "");
    _builder.append(" get");
    String _firstUpper = StringExtensions.toFirstUpper(attributeName);
    _builder.append(_firstUpper, "");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return this.");
    _builder.append(attributeName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence setter(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public void set");
    String _firstUpper = StringExtensions.toFirstUpper(attributeName);
    _builder.append(_firstUpper, "");
    _builder.append("(");
    _builder.append(attributeType, "");
    _builder.append(" ");
    _builder.append(attributeName, "");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("this.");
    _builder.append(attributeName, "\t");
    _builder.append(" = ");
    _builder.append(attributeName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence changeTrackingSetter(final String attributeType, final String attributeName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public void set");
    String _firstUpper = StringExtensions.toFirstUpper(attributeName);
    _builder.append(_firstUpper, "");
    _builder.append("(");
    _builder.append(attributeType, "");
    _builder.append(" ");
    _builder.append(attributeName, "");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("getChangeTracker().addChange(\"");
    _builder.append(attributeName, "\t");
    _builder.append("\", ");
    _builder.append(attributeName, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("this.");
    _builder.append(attributeName, "\t");
    _builder.append(" = ");
    _builder.append(attributeName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
