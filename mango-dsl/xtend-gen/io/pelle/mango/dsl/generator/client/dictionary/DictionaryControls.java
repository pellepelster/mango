package io.pelle.mango.dsl.generator.client.dictionary;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.FileControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.HierarchicalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils;
import io.pelle.mango.dsl.generator.util.AttributeUtils;
import io.pelle.mango.dsl.generator.util.TypeUtils;
import io.pelle.mango.dsl.mango.BaseDictionaryControl;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryBigDecimalControl;
import io.pelle.mango.dsl.mango.DictionaryBooleanControl;
import io.pelle.mango.dsl.mango.DictionaryControl;
import io.pelle.mango.dsl.mango.DictionaryControlGroup;
import io.pelle.mango.dsl.mango.DictionaryDateControl;
import io.pelle.mango.dsl.mango.DictionaryEnumerationControl;
import io.pelle.mango.dsl.mango.DictionaryFileControl;
import io.pelle.mango.dsl.mango.DictionaryHierarchicalControl;
import io.pelle.mango.dsl.mango.DictionaryIntegerControl;
import io.pelle.mango.dsl.mango.DictionaryReferenceControl;
import io.pelle.mango.dsl.mango.DictionaryTextControl;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.Labels;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DictionaryControls {
  @Inject
  @Extension
  private DictionaryNameUtils _dictionaryNameUtils;
  
  @Inject
  @Extension
  private TypeUtils _typeUtils;
  
  @Inject
  @Extension
  private AttributeUtils _attributeUtils;
  
  public CharSequence dictionaryControlClass(final DictionaryControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = BaseControlModel.class.getName();
    _builder.append(_name, "");
    _builder.append("<");
    String _name_1 = IBaseControl.class.getName();
    _builder.append(_name_1, "");
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlConstantSetters(final DictionaryControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final DictionaryControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    CharSequence _dictionaryControlType = this.dictionaryControlType(dictionaryControl);
    _builder.append(_dictionaryControlType, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    CharSequence _dictionaryControlType_1 = this.dictionaryControlType(dictionaryControl);
    _builder.append(_dictionaryControlType_1, "");
    _builder.append("(\"");
    String _controlName = ModelUtil.getControlName(dictionaryControl);
    _builder.append(_controlName, "");
    _builder.append("\", this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * def dictionaryControlTypeSetters(DictionaryControl dictionaryControl, Type type) {
   * }
   * 
   * «DEFINE dictionaryControlTypeSetters(DictionaryControl dictionaryControl) FOR DatatypeType»
   * «IF this.type.baseDatatype.labels != null»
   * «EXPAND dictionaryControlLabelSetters(this.type.baseDatatype.labels) FOR dictionaryControl»
   * «ENDIF»
   * «ENDDEFINE»
   */
  public CharSequence dictionaryControlCommonSetters(final DictionaryControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      BaseDictionaryControl _baseControl = dictionaryControl.getBaseControl();
      boolean _notEquals = (!Objects.equal(_baseControl, null));
      if (_notEquals) {
        _builder.newLine();
        _builder.append("\t");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
        _builder.append(_dictionaryConstantName, "	");
        _builder.append(".setMandatory(");
        BaseDictionaryControl _baseControl_1 = dictionaryControl.getBaseControl();
        boolean _isMandatory = _baseControl_1.isMandatory();
        String _string = Boolean.valueOf(_isMandatory).toString();
        String _lowerCase = _string.toLowerCase();
        _builder.append(_lowerCase, "	");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        {
          BaseDictionaryControl _baseControl_2 = dictionaryControl.getBaseControl();
          EntityAttribute _entityattribute = _baseControl_2.getEntityattribute();
          boolean _notEquals_1 = (!Objects.equal(_entityattribute, null));
          if (_notEquals_1) {
            _builder.append("\t");
            String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
            _builder.append(_dictionaryConstantName_1, "	");
            _builder.append(".setAttributePath(\"");
            BaseDictionaryControl _baseControl_3 = dictionaryControl.getBaseControl();
            EntityAttribute _entityattribute_1 = _baseControl_3.getEntityattribute();
            String _name = _entityattribute_1.getName();
            _builder.append(_name, "	");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.newLine();
            {
              BaseDictionaryControl _baseControl_4 = dictionaryControl.getBaseControl();
              EntityAttribute _entityattribute_2 = _baseControl_4.getEntityattribute();
              boolean _isNaturalKeyAttribute = this._attributeUtils.isNaturalKeyAttribute(_entityattribute_2);
              if (_isNaturalKeyAttribute) {
                _builder.append("\t");
                _builder.append("// natural key attribute");
                _builder.newLine();
                _builder.append("\t");
                String _dictionaryConstantName_2 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
                _builder.append(_dictionaryConstantName_2, "	");
                _builder.append(".setMandatory(true);");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        _builder.newLine();
        {
          BaseDictionaryControl _baseControl_5 = dictionaryControl.getBaseControl();
          Labels _labels = _baseControl_5.getLabels();
          boolean _notEquals_2 = (!Objects.equal(_labels, null));
          if (_notEquals_2) {
            _builder.append("\t");
            BaseDictionaryControl _baseControl_6 = dictionaryControl.getBaseControl();
            Labels _labels_1 = _baseControl_6.getLabels();
            CharSequence _dictionaryControlLabelSetters = this.dictionaryControlLabelSetters(dictionaryControl, _labels_1);
            _builder.append(_dictionaryControlLabelSetters, "	");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("//IF dictionaryControl.baseControl.entityattribute != null");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("//\tdictionaryControl.baseControl.entityattribute.dictionaryControlTypeSetters");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("//ENDIF");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence dictionaryControlLabelSetters(final DictionaryControl dictionaryControl, final Labels labels) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _notEquals = (!Objects.equal(labels, null));
      if (_notEquals) {
        {
          String _label = labels.getLabel();
          boolean _notEquals_1 = (!Objects.equal(_label, null));
          if (_notEquals_1) {
            _builder.append("\t");
            String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
            _builder.append(_dictionaryConstantName, "	");
            _builder.append(".setLabel(\"");
            String _label_1 = labels.getLabel();
            _builder.append(_label_1, "	");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        {
          String _columnLabel = labels.getColumnLabel();
          boolean _notEquals_2 = (!Objects.equal(_columnLabel, null));
          if (_notEquals_2) {
            _builder.append("\t");
            String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
            _builder.append(_dictionaryConstantName_1, "	");
            _builder.append(".setColumnLabel(\"");
            String _columnLabel_1 = labels.getColumnLabel();
            _builder.append(_columnLabel_1, "	");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        {
          String _editorLabel = labels.getEditorLabel();
          boolean _notEquals_3 = (!Objects.equal(_editorLabel, null));
          if (_notEquals_3) {
            _builder.append("\t");
            String _dictionaryConstantName_2 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
            _builder.append(_dictionaryConstantName_2, "	");
            _builder.append(".setEditorLabel(\"");
            String _editorLabel_1 = labels.getEditorLabel();
            _builder.append(_editorLabel_1, "	");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        {
          String _filterLabel = labels.getFilterLabel();
          boolean _notEquals_4 = (!Objects.equal(_filterLabel, null));
          if (_notEquals_4) {
            _builder.append("\t");
            String _dictionaryConstantName_3 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
            _builder.append(_dictionaryConstantName_3, "	");
            _builder.append(".setFilterLabel(\"");
            String _filterLabel_1 = labels.getFilterLabel();
            _builder.append(_filterLabel_1, "	");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryTextControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = TextControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlConstantSetters(final DictionaryTextControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      DictionaryTextControl _ref = dictionaryControl.getRef();
      boolean _notEquals = (!Objects.equal(_ref, null));
      if (_notEquals) {
        DictionaryTextControl _ref_1 = dictionaryControl.getRef();
        Object _dictionaryControlConstantSetters = this.dictionaryControlConstantSetters(_ref_1);
        _builder.append(_dictionaryControlConstantSetters, "");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _dictionaryControlCommonSetters = this.dictionaryControlCommonSetters(dictionaryControl);
    _builder.append(_dictionaryControlCommonSetters, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryIntegerControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = IntegerControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryBigDecimalControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = BigDecimalControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryBooleanControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = BooleanControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryDateControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = DateControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryEnumerationControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = EnumerationControlModel.class.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryReferenceControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ReferenceControlModel.class, "");
    _builder.append("<");
    EntityAttribute _entityAttribute = ModelUtil.getEntityAttribute(dictionaryControl);
    String _type = this._typeUtils.getType(_entityAttribute);
    _builder.append(_type, "");
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final DictionaryReferenceControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryControl);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryControl);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence dictionaryControlClass(final DictionaryReferenceControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryControl);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryControl);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    CharSequence _dictionaryControlType = this.dictionaryControlType(dictionaryControl);
    _builder.append(_dictionaryControlType, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _labelcontrols = dictionaryControl.getLabelcontrols();
      for(final DictionaryControl dictionaryLabelControl : _labelcontrols) {
        _builder.append("\t");
        CharSequence _dictionaryConstant = this.dictionaryConstant(dictionaryLabelControl);
        _builder.append(_dictionaryConstant, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryControl);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("(de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _controlName = ModelUtil.getControlName(dictionaryControl);
    _builder.append(_controlName, "		");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _labelcontrols_1 = dictionaryControl.getLabelcontrols();
      for(final DictionaryControl dictionaryLabelControl_1 : _labelcontrols_1) {
        _builder.append("\t\t");
        Object _dictionaryControlConstantSetters = this.dictionaryControlConstantSetters(dictionaryControl);
        _builder.append(_dictionaryControlConstantSetters, "		");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<DictionaryControl> _labelcontrols_2 = dictionaryControl.getLabelcontrols();
      for(final DictionaryControl dictionaryLabelControl_2 : _labelcontrols_2) {
        _builder.append("\t\t");
        _builder.append("this.getLabelControls().add(");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryLabelControl_2);
        _builder.append(_dictionaryConstantName, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlConstantSetters(final DictionaryReferenceControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      DictionaryReferenceControl _ref = dictionaryControl.getRef();
      boolean _notEquals = (!Objects.equal(_ref, null));
      if (_notEquals) {
        _builder.append("\t");
        DictionaryReferenceControl _ref_1 = dictionaryControl.getRef();
        Object _dictionaryControlConstantSetters = this.dictionaryControlConstantSetters(_ref_1);
        _builder.append(_dictionaryControlConstantSetters, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _dictionaryControlCommonSetters = this.dictionaryControlCommonSetters(dictionaryControl);
    _builder.append(_dictionaryControlCommonSetters, "	");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      Dictionary _dictionary = dictionaryControl.getDictionary();
      boolean _notEquals_1 = (!Objects.equal(_dictionary, null));
      if (_notEquals_1) {
        _builder.append("\t");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
        _builder.append(_dictionaryConstantName, "	");
        _builder.append(".setDictionaryName(\"");
        Dictionary _dictionary_1 = dictionaryControl.getDictionary();
        String _name = _dictionary_1.getName();
        _builder.append(_name, "	");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryHierarchicalControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = HierarchicalControlModel.class.getName();
    _builder.append(_name, "");
    _builder.append("<");
    String _name_1 = IBaseControl.class.getName();
    _builder.append(_name_1, "");
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlType(final DictionaryFileControl dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = FileControlModel.class.getName();
    _builder.append(_name, "");
    _builder.append("<");
    String _name_1 = IBaseControl.class.getName();
    _builder.append(_name_1, "");
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryControlConstantSetters(final DictionaryControlGroup dictionaryControl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      DictionaryControlGroup _ref = dictionaryControl.getRef();
      boolean _notEquals = (!Objects.equal(_ref, null));
      if (_notEquals) {
        DictionaryControlGroup _ref_1 = dictionaryControl.getRef();
        Object _dictionaryControlConstantSetters = this.dictionaryControlConstantSetters(_ref_1);
        _builder.append(_dictionaryControlConstantSetters, "");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    CharSequence _dictionaryControlCommonSetters = this.dictionaryControlCommonSetters(dictionaryControl);
    _builder.append(_dictionaryControlCommonSetters, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence dictionaryControlType(final EObject dictionaryControl) {
    if (dictionaryControl instanceof DictionaryBigDecimalControl) {
      return _dictionaryControlType((DictionaryBigDecimalControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryBooleanControl) {
      return _dictionaryControlType((DictionaryBooleanControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryDateControl) {
      return _dictionaryControlType((DictionaryDateControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryEnumerationControl) {
      return _dictionaryControlType((DictionaryEnumerationControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryFileControl) {
      return _dictionaryControlType((DictionaryFileControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryIntegerControl) {
      return _dictionaryControlType((DictionaryIntegerControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryReferenceControl) {
      return _dictionaryControlType((DictionaryReferenceControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryTextControl) {
      return _dictionaryControlType((DictionaryTextControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryControl) {
      return _dictionaryControlType((DictionaryControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryHierarchicalControl) {
      return _dictionaryControlType((DictionaryHierarchicalControl)dictionaryControl);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dictionaryControl).toString());
    }
  }
  
  public Object dictionaryControlConstantSetters(final DictionaryControl dictionaryControl) {
    if (dictionaryControl instanceof DictionaryControlGroup) {
      return _dictionaryControlConstantSetters((DictionaryControlGroup)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryReferenceControl) {
      return _dictionaryControlConstantSetters((DictionaryReferenceControl)dictionaryControl);
    } else if (dictionaryControl instanceof DictionaryTextControl) {
      return _dictionaryControlConstantSetters((DictionaryTextControl)dictionaryControl);
    } else if (dictionaryControl != null) {
      return _dictionaryControlConstantSetters(dictionaryControl);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dictionaryControl).toString());
    }
  }
  
  public CharSequence dictionaryConstant(final DictionaryControl dictionaryControl) {
    if (dictionaryControl instanceof DictionaryReferenceControl) {
      return _dictionaryConstant((DictionaryReferenceControl)dictionaryControl);
    } else if (dictionaryControl != null) {
      return _dictionaryConstant(dictionaryControl);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dictionaryControl).toString());
    }
  }
}
