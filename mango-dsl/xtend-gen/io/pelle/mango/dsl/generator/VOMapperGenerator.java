package io.pelle.mango.dsl.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.server.base.BaseEntityVOMapper;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class VOMapperGenerator {
  @Inject
  @Extension
  private NameUtils _nameUtils;
  
  private ClientNameUtils clientNameUtils = new ClientNameUtils();
  
  public CharSequence compileVOMapper(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _modelPackageName = this._nameUtils.modelPackageName(model);
    _builder.append(_modelPackageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("import java.util.HashMap;");
    _builder.newLine();
    _builder.append("import java.util.Map;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("@org.springframework.stereotype.Component");
    _builder.newLine();
    _builder.append("public class ");
    String _voMapperName = this._nameUtils.voMapperName(model);
    _builder.append(_voMapperName, "");
    _builder.append(" extends ");
    String _name = BaseEntityVOMapper.class.getName();
    _builder.append(_name, "");
    _builder.append(" implements ");
    String _name_1 = IEntityVOMapper.class.getName();
    _builder.append(_name_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@SuppressWarnings(\"serial\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    {
      TreeIterator<EObject> _eAllContents = model.eAllContents();
      Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_eAllContents);
      Iterable<Entity> _filter = Iterables.<Entity>filter(_iterable, Entity.class);
      for(final Entity entity : _filter) {
        _builder.append("\t\t\t");
        _builder.append("put(");
        String _voFullQualifiedName = this.clientNameUtils.voFullQualifiedName(entity);
        _builder.append(_voFullQualifiedName, "\t\t\t");
        _builder.append(".class, ");
        String _entityFullQualifiedName = this._nameUtils.entityFullQualifiedName(entity);
        _builder.append(_entityFullQualifiedName, "\t\t\t");
        _builder.append(".class);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("put(");
        String _entityFullQualifiedName_1 = this._nameUtils.entityFullQualifiedName(entity);
        _builder.append(_entityFullQualifiedName_1, "\t\t\t");
        _builder.append(".class, ");
        String _voFullQualifiedName_1 = this.clientNameUtils.voFullQualifiedName(entity);
        _builder.append(_voFullQualifiedName_1, "\t\t\t");
        _builder.append(".class);");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected Map<Class<?>, Class<?>> getClassMappings() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return entityVOMapper;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Class<?> getMappedClass(Class<?> clazz) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return entityVOMapper.get(clazz);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
