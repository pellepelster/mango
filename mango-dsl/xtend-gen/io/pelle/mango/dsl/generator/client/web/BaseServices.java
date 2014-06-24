package io.pelle.mango.dsl.generator.client.web;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.client.ClientTypeUtils;
import io.pelle.mango.dsl.mango.GenericTypeDefinition;
import io.pelle.mango.dsl.mango.MethodParameter;
import io.pelle.mango.dsl.mango.MethodReturnType;
import io.pelle.mango.dsl.mango.ServiceMethod;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class BaseServices {
  @Inject
  @Extension
  private ClientTypeUtils _clientTypeUtils;
  
  public CharSequence methodParameters(final List<MethodParameter> methodParameters) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final MethodParameter methodParameter : methodParameters) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "");
        }
        String _methodParameter = this.methodParameter(methodParameter);
        _builder.append(_methodParameter, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public String methodParameter(final MethodParameter methodParameter) {
    String _type = this._clientTypeUtils.getType(methodParameter);
    String _plus = (_type + " ");
    String _name = methodParameter.getName();
    String _firstLower = StringExtensions.toFirstLower(_name);
    String _plus_1 = (_plus + _firstLower);
    return _plus_1;
  }
  
  public CharSequence serviceMethod(final ServiceMethod serviceMethod) {
    StringConcatenation _builder = new StringConcatenation();
    {
      GenericTypeDefinition _genericTypeDefinition = serviceMethod.getGenericTypeDefinition();
      boolean _notEquals = (!Objects.equal(_genericTypeDefinition, null));
      if (_notEquals) {
        GenericTypeDefinition _genericTypeDefinition_1 = serviceMethod.getGenericTypeDefinition();
        String _genericTypeDefinition_2 = this._clientTypeUtils.genericTypeDefinition(_genericTypeDefinition_1);
        _builder.append(_genericTypeDefinition_2, "");
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _serviceMethodReturnType = this.serviceMethodReturnType(serviceMethod);
    _builder.append(_serviceMethodReturnType, "");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    String _name = serviceMethod.getName();
    String _firstLower = StringExtensions.toFirstLower(_name);
    _builder.append(_firstLower, " ");
    _builder.append("(");
    EList<MethodParameter> _methodParameters = serviceMethod.getMethodParameters();
    CharSequence _methodParameters_1 = this.methodParameters(_methodParameters);
    _builder.append(_methodParameters_1, " ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence serviceMethodReturnType(final ServiceMethod serviceMethod) {
    StringConcatenation _builder = new StringConcatenation();
    {
      MethodReturnType _returnType = serviceMethod.getReturnType();
      boolean _equals = Objects.equal(_returnType, null);
      if (_equals) {
        _builder.append("void");
        _builder.newLine();
      } else {
        MethodReturnType _returnType_1 = serviceMethod.getReturnType();
        String _type = this._clientTypeUtils.getType(_returnType_1);
        _builder.append(_type, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
