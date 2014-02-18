/**
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.web;

import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.client.web.BaseServices;
import io.pelle.mango.dsl.mango.Service;
import io.pelle.mango.dsl.mango.ServiceMethod;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
@SuppressWarnings("all")
public class Services extends BaseServices {
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  public CharSequence serviceInterface(final Service service) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._clientNameUtils.getPackageName(service);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public interface ");
    String _serviceInterfaceName = this._clientNameUtils.serviceInterfaceName(service);
    _builder.append(_serviceInterfaceName, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<ServiceMethod> _remoteMethods = service.getRemoteMethods();
      for(final ServiceMethod remoteMethod : _remoteMethods) {
        _builder.append("\t");
        CharSequence _serviceMethod = this.serviceMethod(remoteMethod);
        _builder.append(_serviceMethod, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
