package io.pelle.mango.dsl.generator.server;

import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionarySearch;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.PackageDeclaration;
import io.pelle.mango.dsl.mango.Service;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ServerNameUtils extends NameUtils {
  protected String _getPackageName(final PackageDeclaration packageDeclaration) {
    String _xifexpression = null;
    EObject _eContainer = packageDeclaration.eContainer();
    if ((_eContainer instanceof Model)) {
      String _name = packageDeclaration.getName();
      String _packageName = this.getPackageName(_name);
      String _plus = (_packageName + ".");
      String _plus_1 = (_plus + GeneratorConstants.SERVER_PACKAGE_POSTFIX);
      _xifexpression = _plus_1;
    } else {
      EObject _eContainer_1 = packageDeclaration.eContainer();
      String _packageName_1 = this.getPackageName(_eContainer_1);
      String _name_1 = packageDeclaration.getName();
      String _packageName_2 = this.getPackageName(_name_1);
      String _combinePackageName = this.combinePackageName(_packageName_1, _packageName_2);
      _xifexpression = _combinePackageName;
    }
    return _xifexpression;
  }
  
  public String gwtRemoteServicesApplicationContextFullQualifiedFileName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "GWTRemoteServices-gen.xml");
  }
  
  public String serviceSpringNameApplicationContextFullQualifiedFileName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "SpringServices-gen.xml");
  }
  
  public String serviceImplFullQualifiedName(final Service service) {
    String _packageName = this.getPackageName(service);
    String _plus = (_packageName + ".");
    String _serviceImplName = this.serviceImplName(service);
    return (_plus + _serviceImplName);
  }
  
  public String serviceImplName(final Service service) {
    String _name = service.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "Impl");
  }
  
  public String getPackageName(final Object dictionary) {
    if (dictionary instanceof Dictionary) {
      return _getPackageName((Dictionary)dictionary);
    } else if (dictionary instanceof PackageDeclaration) {
      return _getPackageName((PackageDeclaration)dictionary);
    } else if (dictionary instanceof DictionaryEditor) {
      return _getPackageName((DictionaryEditor)dictionary);
    } else if (dictionary instanceof DictionarySearch) {
      return _getPackageName((DictionarySearch)dictionary);
    } else if (dictionary instanceof String) {
      return _getPackageName((String)dictionary);
    } else if (dictionary instanceof EObject) {
      return _getPackageName((EObject)dictionary);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dictionary).toString());
    }
  }
}
