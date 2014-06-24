package io.pelle.mango.dsl.generator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import io.pelle.mango.client.base.module.BaseModule;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.client.ClientTypeUtils;
import io.pelle.mango.dsl.mango.ModuleDefinition;
import io.pelle.mango.dsl.mango.ModuleDefinitionParameter;
import io.pelle.mango.dsl.mango.SimpleTypes;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ModuleGenerator {
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  @Inject
  @Extension
  private ClientTypeUtils _clientTypeUtils;
  
  public CharSequence compileBaseModuleDefinition(final ModuleDefinition moduleDefinition) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._clientNameUtils.getPackageName(moduleDefinition);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public abstract class ");
    String _baseModuleDefinitionName = this._clientNameUtils.baseModuleDefinitionName(moduleDefinition);
    _builder.append(_baseModuleDefinitionName, "");
    _builder.append(" extends ");
    String _name = BaseModule.class.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final String MODULE_ID = \"");
    String _name_1 = moduleDefinition.getName();
    _builder.append(_name_1, "	");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _baseModuleDefinitionName_1 = this._clientNameUtils.baseModuleDefinitionName(moduleDefinition);
    _builder.append(_baseModuleDefinitionName_1, "	");
    _builder.append("(String moduleUrl, ");
    String _name_2 = AsyncCallback.class.getName();
    _builder.append(_name_2, "	");
    _builder.append("<");
    String _name_3 = IModule.class.getName();
    _builder.append(_name_3, "	");
    _builder.append("> moduleCallback, java.util.Map<String, Object> parameters) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(moduleUrl, moduleCallback, parameters);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      EList<ModuleDefinitionParameter> _moduleDefinitionParameters = moduleDefinition.getModuleDefinitionParameters();
      for(final ModuleDefinitionParameter moduleDefinitionParameter : _moduleDefinitionParameters) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public static final String ");
        String _name_4 = moduleDefinitionParameter.getName();
        String _upperCase = _name_4.toUpperCase();
        _builder.append(_upperCase, "	");
        _builder.append("_PARAMETER_ID = \"");
        String _name_5 = moduleDefinitionParameter.getName();
        _builder.append(_name_5, "	");
        _builder.append("\";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public ");
        SimpleTypes _type = moduleDefinitionParameter.getType();
        _builder.append(_type, "	");
        _builder.append(" get");
        String _name_6 = moduleDefinitionParameter.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_6);
        _builder.append(_firstUpper, "	");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("if (getParameters().containsKey(\"");
        String _name_7 = moduleDefinitionParameter.getName();
        _builder.append(_name_7, "		");
        _builder.append("\"))");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("Object parameterValue = parameters.get(\"");
        String _name_8 = moduleDefinitionParameter.getName();
        _builder.append(_name_8, "			");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("if (parameterValue instanceof ");
        SimpleTypes _type_1 = moduleDefinitionParameter.getType();
        String _type_2 = this._clientTypeUtils.getType(_type_1);
        _builder.append(_type_2, "			");
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("return (");
        SimpleTypes _type_3 = moduleDefinitionParameter.getType();
        String _type_4 = this._clientTypeUtils.getType(_type_3);
        _builder.append(_type_4, "				");
        _builder.append(") parameterValue;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("if (parameterValue instanceof java.lang.String)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("return ");
        SimpleTypes _type_5 = moduleDefinitionParameter.getType();
        String _parseSimpleTypeFromString = this._clientTypeUtils.parseSimpleTypeFromString(_type_5, "parameterValue.toString()");
        _builder.append(_parseSimpleTypeFromString, "				");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("throw new RuntimeException(\"parameter value type mismatch, expected \'");
        SimpleTypes _type_6 = moduleDefinitionParameter.getType();
        String _type_7 = this._clientTypeUtils.getType(_type_6);
        _builder.append(_type_7, "			");
        _builder.append("\' but got \'\" + parameterValue.getClass().getName() + \"\'\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("return null;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public boolean has");
        String _name_9 = moduleDefinitionParameter.getName();
        String _firstUpper_1 = StringExtensions.toFirstUpper(_name_9);
        _builder.append(_firstUpper_1, "	");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return getParameters().containsKey(\"");
        String _name_10 = moduleDefinitionParameter.getName();
        _builder.append(_name_10, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static java.util.Map<String, Object> getParameterMap(");
    CharSequence _moduleDefinitionParameters_1 = this.moduleDefinitionParameters(moduleDefinition);
    _builder.append(_moduleDefinitionParameters_1, "	");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("java.util.Map<String, Object> parameterMap = new java.util.HashMap<String, Object>();");
    _builder.newLine();
    _builder.newLine();
    {
      EList<ModuleDefinitionParameter> _moduleDefinitionParameters_2 = moduleDefinition.getModuleDefinitionParameters();
      for(final ModuleDefinitionParameter moduleDefinitionParameter_1 : _moduleDefinitionParameters_2) {
        _builder.append("\t\t");
        _builder.append("parameterMap.put(");
        String _name_11 = moduleDefinitionParameter_1.getName();
        String _upperCase_1 = _name_11.toUpperCase();
        _builder.append(_upperCase_1, "		");
        _builder.append("_PARAMETER_ID, ");
        String _name_12 = moduleDefinitionParameter_1.getName();
        String _firstLower = StringExtensions.toFirstLower(_name_12);
        _builder.append(_firstLower, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return parameterMap;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean hasParameter(String parameterName) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return getParameters().containsKey(parameterName);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence moduleDefinitionParameters(final ModuleDefinition moduleDefinition) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ModuleDefinitionParameter> _moduleDefinitionParameters = moduleDefinition.getModuleDefinitionParameters();
      boolean _hasElements = false;
      for(final ModuleDefinitionParameter moduleDefinitionParameter : _moduleDefinitionParameters) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        SimpleTypes _type = moduleDefinitionParameter.getType();
        String _type_1 = this._clientTypeUtils.getType(_type);
        _builder.append(_type_1, "");
        _builder.append(" ");
        String _name = moduleDefinitionParameter.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
