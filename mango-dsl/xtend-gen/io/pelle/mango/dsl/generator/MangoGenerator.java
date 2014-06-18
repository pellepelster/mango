package io.pelle.mango.dsl.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.VOMapperGenerator;
import io.pelle.mango.dsl.generator.client.ClientGenerator;
import io.pelle.mango.dsl.generator.server.EntityGenerator;
import io.pelle.mango.dsl.generator.server.ServerGenerator;
import io.pelle.mango.dsl.generator.server.SpringGenerator;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.Model;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class MangoGenerator implements IGenerator {
  @Inject
  @Extension
  private EntityGenerator _entityGenerator;
  
  @Inject
  @Extension
  private VOMapperGenerator _vOMapperGenerator;
  
  @Inject
  @Extension
  private NameUtils _nameUtils;
  
  @Inject
  @Extension
  private ClientGenerator clientGenerator;
  
  @Inject
  @Extension
  private ServerGenerator serverGenerator;
  
  @Inject
  @Extension
  private SpringGenerator _springGenerator;
  
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    this.clientGenerator.doGenerate(resource, fsa);
    this.serverGenerator.doGenerate(resource, fsa);
    TreeIterator<EObject> _allContents = resource.getAllContents();
    Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_allContents);
    Iterable<Entity> _filter = Iterables.<Entity>filter(_iterable, Entity.class);
    for (final Entity entity : _filter) {
      String _entityFullQualifiedFileName = this._nameUtils.entityFullQualifiedFileName(entity);
      CharSequence _compileEntity = this._entityGenerator.compileEntity(entity);
      fsa.generateFile(_entityFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _compileEntity);
    }
    TreeIterator<EObject> _allContents_1 = resource.getAllContents();
    Iterable<EObject> _iterable_1 = IteratorExtensions.<EObject>toIterable(_allContents_1);
    Iterable<Model> _filter_1 = Iterables.<Model>filter(_iterable_1, Model.class);
    for (final Model model : _filter_1) {
      {
        String _springDBApplicationContextFullQualifiedFileName = this._nameUtils.springDBApplicationContextFullQualifiedFileName(model);
        CharSequence _compileSpringDBApplicationContext = this._springGenerator.compileSpringDBApplicationContext(model);
        fsa.generateFile(_springDBApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _compileSpringDBApplicationContext);
        String _springPersistenceXMLFullQualifiedFileName = this._nameUtils.springPersistenceXMLFullQualifiedFileName(model);
        CharSequence _compilePersistenceXml = this._springGenerator.compilePersistenceXml(model);
        fsa.generateFile(_springPersistenceXMLFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _compilePersistenceXml);
        String _baseApplicationContextFullQualifiedFileName = this._nameUtils.baseApplicationContextFullQualifiedFileName(model);
        CharSequence _compileBaseApplicationContext = this._springGenerator.compileBaseApplicationContext(model);
        fsa.generateFile(_baseApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _compileBaseApplicationContext);
        String _voMapperFullQualifiedFileName = this._nameUtils.voMapperFullQualifiedFileName(model);
        CharSequence _compileVOMapper = this._vOMapperGenerator.compileVOMapper(model);
        fsa.generateFile(_voMapperFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _compileVOMapper);
      }
    }
  }
}
