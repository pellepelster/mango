package io.pelle.mango.dsl.generator.util;

import com.google.common.base.Objects;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.util.GeneratorUtil;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionarySearch;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.ModuleDefinition;
import io.pelle.mango.dsl.mango.PackageDeclaration;
import io.pelle.mango.dsl.mango.Service;
import io.pelle.mango.dsl.mango.ValueObject;
import java.util.Arrays;
import javax.management.Attribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class NameUtils {
  public String combinePackageName(final String packageName1, final String packageName2) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(packageName1, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isEmpty = packageName1.isEmpty();
      boolean _not = (!_isEmpty);
      _and = (_notEquals && _not);
    }
    if (_and) {
      String _plus = (packageName1 + ".");
      return (_plus + packageName2);
    } else {
      return packageName2;
    }
  }
  
  protected String _getPackageName(final String packageName) {
    String _lowerCase = packageName.toLowerCase();
    return _lowerCase.replaceAll("\\^", "");
  }
  
  protected String _getPackageName(final Dictionary dictionary) {
    EObject _eContainer = dictionary.eContainer();
    String _packageName = this.getPackageName(_eContainer);
    String _name = dictionary.getName();
    String _packageName_1 = this.getPackageName(_name);
    return this.combinePackageName(_packageName, _packageName_1);
  }
  
  protected String _getPackageName(final DictionaryEditor dictionaryEditor) {
    EObject _eContainer = dictionaryEditor.eContainer();
    String _packageName = this.getPackageName(_eContainer);
    String _name = dictionaryEditor.getName();
    String _packageName_1 = this.getPackageName(_name);
    return this.combinePackageName(_packageName, _packageName_1);
  }
  
  protected String _getPackageName(final DictionarySearch dictionarySearch) {
    EObject _eContainer = dictionarySearch.eContainer();
    String _packageName = this.getPackageName(_eContainer);
    String _name = dictionarySearch.getName();
    String _packageName_1 = this.getPackageName(_name);
    return this.combinePackageName(_packageName, _packageName_1);
  }
  
  protected String _getPackageName(final PackageDeclaration packageDeclaration) {
    EObject _eContainer = packageDeclaration.eContainer();
    String _packageName = this.getPackageName(_eContainer);
    String _name = packageDeclaration.getName();
    String _packageName_1 = this.getPackageName(_name);
    return this.combinePackageName(_packageName, _packageName_1);
  }
  
  protected String _getPackageName(final EObject eObject) {
    EObject _eContainer = eObject.eContainer();
    boolean _equals = Objects.equal(_eContainer, null);
    if (_equals) {
      return "";
    } else {
      EObject _eContainer_1 = eObject.eContainer();
      return this.getPackageName(_eContainer_1);
    }
  }
  
  public String attributeName(final Attribute attribute) {
    String _name = attribute.getName();
    return this.attributeName(_name);
  }
  
  public String attributeName(final String attribute) {
    return StringExtensions.toFirstLower(attribute);
  }
  
  public String attributeConstantName(final String attribute) {
    return attribute.toUpperCase();
  }
  
  public String entityConstantName(final Entity entity) {
    String _name = entity.getName();
    return _name.toUpperCase();
  }
  
  public String entityTableName(final Entity entity) {
    String _name = entity.getName();
    return _name.toLowerCase();
  }
  
  public String entityTableIdColumnName(final Entity entity) {
    String _name = entity.getName();
    String _lowerCase = _name.toLowerCase();
    return (_lowerCase + "_id");
  }
  
  public String entityTableColumnName(final EntityAttribute entityAttribute) {
    Entity _parentEntity = GeneratorUtil.getParentEntity(entityAttribute);
    String _name = _parentEntity.getName();
    String _lowerCase = _name.toLowerCase();
    String _plus = (_lowerCase + "_");
    String _name_1 = entityAttribute.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    return (_plus + _lowerCase_1);
  }
  
  public String entityTableIdSequenceName(final Entity entity) {
    String _name = entity.getName();
    String _lowerCase = _name.toLowerCase();
    return (_lowerCase + "_id_seq");
  }
  
  public String gwtClientGeneratedModuleDefinitionFileName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "Generated.gwt.xml");
  }
  
  public String gwtClientModuleFullQualifiedFileName(final Model model) {
    String _xblockexpression = null;
    {
      NameUtils _nameUtils = new NameUtils();
      NameUtils nameUtils = _nameUtils;
      String _modelPackageName = nameUtils.modelPackageName(model);
      String _replaceAll = _modelPackageName.replaceAll("\\.", "/");
      String _plus = (_replaceAll + "/");
      String _gwtClientGeneratedModuleDefinitionFileName = this.gwtClientGeneratedModuleDefinitionFileName(model);
      String _plus_1 = (_plus + _gwtClientGeneratedModuleDefinitionFileName);
      _xblockexpression = (_plus_1);
    }
    return _xblockexpression;
  }
  
  public String entityName(final Entity entity) {
    String _name = entity.getName();
    return StringExtensions.toFirstUpper(_name);
  }
  
  public String entityFullQualifiedName(final Entity entity) {
    String _packageName = this.getPackageName(entity);
    String _plus = (_packageName + ".");
    String _entityName = this.entityName(entity);
    return (_plus + _entityName);
  }
  
  public String entityFullQualifiedFileName(final Entity entity) {
    String _entityFullQualifiedName = this.entityFullQualifiedName(entity);
    String _replaceAll = _entityFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String voName(final ValueObject valueObject) {
    String _name = valueObject.getName();
    return StringExtensions.toFirstUpper(_name);
  }
  
  public String voFullQualifiedName(final ValueObject valueObject) {
    String _packageName = this.getPackageName(valueObject);
    String _plus = (_packageName + ".");
    String _voName = this.voName(valueObject);
    return (_plus + _voName);
  }
  
  public String voFullQualifiedFileName(final ValueObject valueObject) {
    String _voFullQualifiedName = this.voFullQualifiedName(valueObject);
    String _replaceAll = _voFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String serviceName(final Service service) {
    String _name = service.getName();
    return StringExtensions.toFirstUpper(_name);
  }
  
  public String serviceSpringName(final Service service) {
    return this.serviceName(service);
  }
  
  public String gwtClientconfigurationName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "ClientConfiguration");
  }
  
  public String gwtClientconfigurationFullQualifiedName(final Model model) {
    String _modelPackageName = this.modelPackageName(model);
    String _plus = (_modelPackageName + ".");
    String _gwtClientconfigurationName = this.gwtClientconfigurationName(model);
    return (_plus + _gwtClientconfigurationName);
  }
  
  public String gwtClientconfigurationFullQualifiedNameFileName(final Model model) {
    String _gwtClientconfigurationFullQualifiedName = this.gwtClientconfigurationFullQualifiedName(model);
    String _replaceAll = _gwtClientconfigurationFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String serviceInterfaceName(final Service service) {
    String _name = service.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return ("I" + _firstUpper);
  }
  
  public String serviceInterfaceFullQualifiedName(final Service service) {
    String _packageName = this.getPackageName(service);
    String _plus = (_packageName + ".");
    String _serviceInterfaceName = this.serviceInterfaceName(service);
    return (_plus + _serviceInterfaceName);
  }
  
  public String serviceInterfaceFullQualifiedFileName(final Service service) {
    String _serviceInterfaceFullQualifiedName = this.serviceInterfaceFullQualifiedName(service);
    String _replaceAll = _serviceInterfaceFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String gwtServiceInterfaceName(final Service service) {
    String _name = service.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _plus = ("I" + _firstUpper);
    return (_plus + "GWT");
  }
  
  public String gwtServiceInterfaceFullQualifiedName(final Service service) {
    String _packageName = this.getPackageName(service);
    String _plus = (_packageName + ".");
    String _gwtServiceInterfaceName = this.gwtServiceInterfaceName(service);
    return (_plus + _gwtServiceInterfaceName);
  }
  
  public String gwtServiceInterfaceFullQualifiedFileName(final Service service) {
    String _gwtServiceInterfaceFullQualifiedName = this.gwtServiceInterfaceFullQualifiedName(service);
    String _replaceAll = _gwtServiceInterfaceFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String gwtAsyncServiceInterfaceName(final Service service) {
    String _name = service.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _plus = ("I" + _firstUpper);
    return (_plus + "GWTAsync");
  }
  
  public String gwtAsyncServiceInterfaceFullQualifiedName(final Service service) {
    String _packageName = this.getPackageName(service);
    String _plus = (_packageName + ".");
    String _gwtAsyncServiceInterfaceName = this.gwtAsyncServiceInterfaceName(service);
    return (_plus + _gwtAsyncServiceInterfaceName);
  }
  
  public String gwtAsyncServiceInterfaceFullQualifiedFileName(final Service service) {
    String _gwtAsyncServiceInterfaceFullQualifiedName = this.gwtAsyncServiceInterfaceFullQualifiedName(service);
    String _replaceAll = _gwtAsyncServiceInterfaceFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String gwtAsyncAdapterName(final Service service) {
    String _name = service.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "GWTAsyncAdapter");
  }
  
  public String gwtAsyncAdapterFullQualifiedName(final Service service) {
    String _packageName = this.getPackageName(service);
    String _plus = (_packageName + ".");
    String _gwtAsyncAdapterName = this.gwtAsyncAdapterName(service);
    return (_plus + _gwtAsyncAdapterName);
  }
  
  public String gwtAsyncAdapterFullQualifiedFileName(final Service service) {
    String _gwtAsyncAdapterFullQualifiedName = this.gwtAsyncAdapterFullQualifiedName(service);
    String _replaceAll = _gwtAsyncAdapterFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String gwtRemoteServiceLocatorName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "GwtRemoteServiceLocator");
  }
  
  public String gwtRemoteServiceLocatorFullQualifiedName(final Model model) {
    String _modelPackageName = this.modelPackageName(model);
    String _plus = (_modelPackageName + ".");
    String _gwtRemoteServiceLocatorName = this.gwtRemoteServiceLocatorName(model);
    return (_plus + _gwtRemoteServiceLocatorName);
  }
  
  public String gwtRemoteServiceLocatorFullQualifiedFileName(final Model model) {
    String _gwtRemoteServiceLocatorFullQualifiedName = this.gwtRemoteServiceLocatorFullQualifiedName(model);
    String _replaceAll = _gwtRemoteServiceLocatorFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String gwtRemoteServiceLocatorInterfaceName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _plus = ("I" + _firstUpper);
    return (_plus + "GwtRemoteServiceLocator");
  }
  
  public String gwtRemoteServiceLocatorInterfaceFullQualifiedName(final Model model) {
    String _modelPackageName = this.modelPackageName(model);
    String _plus = (_modelPackageName + ".");
    String _gwtRemoteServiceLocatorInterfaceName = this.gwtRemoteServiceLocatorInterfaceName(model);
    return (_plus + _gwtRemoteServiceLocatorInterfaceName);
  }
  
  public String gwtRemoteServiceLocatorInterfaceFullQualifiedFileName(final Model model) {
    String _gwtRemoteServiceLocatorInterfaceFullQualifiedName = this.gwtRemoteServiceLocatorInterfaceFullQualifiedName(model);
    String _replaceAll = _gwtRemoteServiceLocatorInterfaceFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String baseModuleDefinitionName(final ModuleDefinition moduleDefinition) {
    String _name = moduleDefinition.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _plus = ("Base" + _firstUpper);
    return (_plus + "Module");
  }
  
  public String baseModuleDefinitionFullQualifiedName(final ModuleDefinition moduleDefinition) {
    String _packageName = this.getPackageName(moduleDefinition);
    String _plus = (_packageName + ".");
    String _baseModuleDefinitionName = this.baseModuleDefinitionName(moduleDefinition);
    return (_plus + _baseModuleDefinitionName);
  }
  
  public String baseModuleDefinitionFullQualifiedFileName(final ModuleDefinition moduleDefinition) {
    String _baseModuleDefinitionFullQualifiedName = this.baseModuleDefinitionFullQualifiedName(moduleDefinition);
    String _replaceAll = _baseModuleDefinitionFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String modelPackageName(final Model model) {
    PackageDeclaration _singleRootPackage = ModelUtil.getSingleRootPackage(model);
    String _name = _singleRootPackage.getName();
    return this.getPackageName(_name);
  }
  
  public String voMapperName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "VOMapper");
  }
  
  public String voMapperFullQualifiedName(final Model model) {
    String _modelPackageName = this.modelPackageName(model);
    String _plus = (_modelPackageName + ".");
    String _voMapperName = this.voMapperName(model);
    return (_plus + _voMapperName);
  }
  
  public String voMapperFullQualifiedFileName(final Model model) {
    String _voMapperFullQualifiedName = this.voMapperFullQualifiedName(model);
    String _replaceAll = _voMapperFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String springDBApplicationContextFullQualifiedFileName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "DB-gen.xml");
  }
  
  public String baseApplicationContextFullQualifiedFileName(final Model model) {
    String _name = model.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "BaseApplicationContext-gen.xml");
  }
  
  public String springPersistenceXMLFullQualifiedFileName(final Model model) {
    return "META-INF/persistence.xml";
  }
  
  public String persistenceUnitName(final Model model) {
    String _name = model.getName();
    return _name.toLowerCase();
  }
  
  public String jndiName(final Model model) {
    return this.persistenceUnitName(model);
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
