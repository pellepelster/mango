/**
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.client.ModuleGenerator;
import io.pelle.mango.dsl.generator.client.VOGenerator;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryGenerator;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNavigationGenerator;
import io.pelle.mango.dsl.generator.client.web.GWTClient;
import io.pelle.mango.dsl.generator.client.web.GWTServices;
import io.pelle.mango.dsl.generator.client.web.Services;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.Enumeration;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.ModuleDefinition;
import io.pelle.mango.dsl.mango.Service;
import io.pelle.mango.dsl.mango.ValueObject;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class ClientGenerator implements IGenerator {
  @Inject
  @Extension
  private GWTServices _gWTServices;
  
  @Inject
  @Extension
  private GWTClient _gWTClient;
  
  @Inject
  @Extension
  private DictionaryGenerator _dictionaryGenerator;
  
  @Inject
  @Extension
  private VOGenerator _vOGenerator;
  
  @Inject
  @Extension
  private Services _services;
  
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  @Inject
  @Extension
  private ModuleGenerator _moduleGenerator;
  
  @Inject
  @Extension
  private DictionaryNavigationGenerator _dictionaryNavigationGenerator;
  
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    TreeIterator<EObject> _allContents = resource.getAllContents();
    Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_allContents);
    Iterable<Model> _filter = Iterables.<Model>filter(_iterable, Model.class);
    for (final Model model : _filter) {
      {
        String _gwtRemoteServiceLocatorFullQualifiedFileName = this._clientNameUtils.gwtRemoteServiceLocatorFullQualifiedFileName(model);
        CharSequence _gwtRemoteServiceLocator = this._gWTServices.gwtRemoteServiceLocator(model);
        fsa.generateFile(_gwtRemoteServiceLocatorFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtRemoteServiceLocator);
        String _gwtRemoteServiceLocatorInterfaceFullQualifiedFileName = this._clientNameUtils.gwtRemoteServiceLocatorInterfaceFullQualifiedFileName(model);
        CharSequence _gwtRemoteServiceLocatorInterface = this._gWTServices.gwtRemoteServiceLocatorInterface(model);
        fsa.generateFile(_gwtRemoteServiceLocatorInterfaceFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtRemoteServiceLocatorInterface);
        String _gwtClientModuleFullQualifiedFileName = this._clientNameUtils.gwtClientModuleFullQualifiedFileName(model);
        CharSequence _gwtClientModule = this._gWTClient.gwtClientModule(model);
        fsa.generateFile(_gwtClientModuleFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtClientModule);
        String _gwtClientconfigurationFullQualifiedNameFileName = this._clientNameUtils.gwtClientconfigurationFullQualifiedNameFileName(model);
        CharSequence _gwtClientConfiguration = this._gWTClient.gwtClientConfiguration(model);
        fsa.generateFile(_gwtClientconfigurationFullQualifiedNameFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtClientConfiguration);
        this._dictionaryGenerator.dictionaryGenerator(model, fsa);
        this._dictionaryNavigationGenerator.dictionaryNavigationGenerator(model, fsa);
      }
    }
    TreeIterator<EObject> _allContents_1 = resource.getAllContents();
    Iterable<EObject> _iterable_1 = IteratorExtensions.<EObject>toIterable(_allContents_1);
    Iterable<ModuleDefinition> _filter_1 = Iterables.<ModuleDefinition>filter(_iterable_1, ModuleDefinition.class);
    for (final ModuleDefinition moduleDefinition : _filter_1) {
      String _baseModuleDefinitionFullQualifiedFileName = this._clientNameUtils.baseModuleDefinitionFullQualifiedFileName(moduleDefinition);
      CharSequence _compileBaseModuleDefinition = this._moduleGenerator.compileBaseModuleDefinition(moduleDefinition);
      fsa.generateFile(_baseModuleDefinitionFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _compileBaseModuleDefinition);
    }
    TreeIterator<EObject> _allContents_2 = resource.getAllContents();
    Iterable<EObject> _iterable_2 = IteratorExtensions.<EObject>toIterable(_allContents_2);
    Iterable<Entity> _filter_2 = Iterables.<Entity>filter(_iterable_2, Entity.class);
    for (final Entity entity : _filter_2) {
      String _voFullQualifiedFileName = this._clientNameUtils.voFullQualifiedFileName(entity);
      CharSequence _compileVO = this._vOGenerator.compileVO(entity);
      fsa.generateFile(_voFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _compileVO);
    }
    TreeIterator<EObject> _allContents_3 = resource.getAllContents();
    Iterable<EObject> _iterable_3 = IteratorExtensions.<EObject>toIterable(_allContents_3);
    Iterable<ValueObject> _filter_3 = Iterables.<ValueObject>filter(_iterable_3, ValueObject.class);
    for (final ValueObject valueObject : _filter_3) {
      String _voFullQualifiedFileName_1 = this._clientNameUtils.voFullQualifiedFileName(valueObject);
      CharSequence _compileValueObject = this._vOGenerator.compileValueObject(valueObject);
      fsa.generateFile(_voFullQualifiedFileName_1, GeneratorConstants.VO_GEN_OUTPUT, _compileValueObject);
    }
    TreeIterator<EObject> _allContents_4 = resource.getAllContents();
    Iterable<EObject> _iterable_4 = IteratorExtensions.<EObject>toIterable(_allContents_4);
    Iterable<Enumeration> _filter_4 = Iterables.<Enumeration>filter(_iterable_4, Enumeration.class);
    for (final Enumeration enumeration : _filter_4) {
      String _enumerationFullQualifiedFileName = this._clientNameUtils.enumerationFullQualifiedFileName(enumeration);
      CharSequence _compileEnumeration = this._vOGenerator.compileEnumeration(enumeration);
      fsa.generateFile(_enumerationFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _compileEnumeration);
    }
    TreeIterator<EObject> _allContents_5 = resource.getAllContents();
    Iterable<EObject> _iterable_5 = IteratorExtensions.<EObject>toIterable(_allContents_5);
    Iterable<Service> _filter_5 = Iterables.<Service>filter(_iterable_5, Service.class);
    for (final Service service : _filter_5) {
      {
        String _gwtAsyncServiceInterfaceFullQualifiedFileName = this._clientNameUtils.gwtAsyncServiceInterfaceFullQualifiedFileName(service);
        CharSequence _gwtAsyncServiceInterface = this._gWTServices.gwtAsyncServiceInterface(service);
        fsa.generateFile(_gwtAsyncServiceInterfaceFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtAsyncServiceInterface);
        String _gwtServiceInterfaceFullQualifiedFileName = this._clientNameUtils.gwtServiceInterfaceFullQualifiedFileName(service);
        CharSequence _gwtServiceInterface = this._gWTServices.gwtServiceInterface(service);
        fsa.generateFile(_gwtServiceInterfaceFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _gwtServiceInterface);
        String _serviceInterfaceFullQualifiedFileName = this._clientNameUtils.serviceInterfaceFullQualifiedFileName(service);
        CharSequence _serviceInterface = this._services.serviceInterface(service);
        fsa.generateFile(_serviceInterfaceFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _serviceInterface);
      }
    }
  }
}
