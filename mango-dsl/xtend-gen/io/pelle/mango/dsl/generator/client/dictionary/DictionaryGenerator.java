package io.pelle.mango.dsl.generator.client.dictionary;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.CompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.FilterModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.ResultModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.SearchModel;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryContainerGenerator;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryControls;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryContainerContent;
import io.pelle.mango.dsl.mango.DictionaryControl;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionaryFilter;
import io.pelle.mango.dsl.mango.DictionaryResult;
import io.pelle.mango.dsl.mango.DictionarySearch;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.Model;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class DictionaryGenerator {
  @Inject
  @Extension
  private DictionaryNameUtils _dictionaryNameUtils;
  
  @Inject
  @Extension
  private DictionaryControls _dictionaryControls;
  
  @Inject
  @Extension
  private DictionaryContainerGenerator _dictionaryContainerGenerator;
  
  protected CharSequence _dictionaryConstant(final DictionaryEditor dictionaryEditor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryEditor);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryEditor);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryEditor);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final Dictionary dictionary) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public static final ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionary);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionary);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionary);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public void dictionaryGenerator(final Model model, final IFileSystemAccess fsa) {
    TreeIterator<EObject> _eAllContents = model.eAllContents();
    Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_eAllContents);
    Iterable<Dictionary> _filter = Iterables.<Dictionary>filter(_iterable, Dictionary.class);
    for (final Dictionary dictionary : _filter) {
      this.dictionaryGenerator(dictionary, fsa);
    }
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(model);
    CharSequence _dictionaryClass = this.dictionaryClass(model);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
  }
  
  public CharSequence dictionaryClass(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("package ");
    String _modelPackageName = this._dictionaryNameUtils.modelPackageName(model);
    _builder.append(_modelPackageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(model);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(model);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      TreeIterator<EObject> _eAllContents = model.eAllContents();
      Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_eAllContents);
      Iterable<Dictionary> _filter = Iterables.<Dictionary>filter(_iterable, Dictionary.class);
      for(final Dictionary dictionary : _filter) {
        _builder.append("\t");
        CharSequence _dictionaryConstant = this.dictionaryConstant(dictionary);
        _builder.append(_dictionaryConstant, "	");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public void dictionaryGenerator(final Dictionary dictionary, final IFileSystemAccess fsa) {
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionary);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionary);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
    EList<DictionaryControl> _labelcontrols = dictionary.getLabelcontrols();
    for (final DictionaryControl dictionaryLabelControl : _labelcontrols) {
      String _dictionaryClassFullQualifiedFileName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryLabelControl);
      CharSequence _dictionaryControlClass = this._dictionaryControls.dictionaryControlClass(dictionaryLabelControl);
      fsa.generateFile(_dictionaryClassFullQualifiedFileName_1, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryControlClass);
    }
    DictionaryEditor _dictionaryeditor = dictionary.getDictionaryeditor();
    boolean _notEquals = (!Objects.equal(_dictionaryeditor, null));
    if (_notEquals) {
      DictionaryEditor _dictionaryeditor_1 = dictionary.getDictionaryeditor();
      this.dictionaryGenerator(_dictionaryeditor_1, fsa);
    }
    DictionarySearch _dictionarysearch = dictionary.getDictionarysearch();
    boolean _notEquals_1 = (!Objects.equal(_dictionarysearch, null));
    if (_notEquals_1) {
      DictionarySearch _dictionarysearch_1 = dictionary.getDictionarysearch();
      this.dictionaryGenerator(_dictionarysearch_1, fsa);
    }
  }
  
  public void dictionaryGenerator(final DictionaryEditor dictionaryEditor, final IFileSystemAccess fsa) {
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryEditor);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionaryEditor);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
    EList<DictionaryContainerContent> _containercontents = dictionaryEditor.getContainercontents();
    this._dictionaryContainerGenerator.dictionaryGenerator(_containercontents, fsa);
  }
  
  public CharSequence dictionaryClass(final DictionaryEditor dictionaryEditor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryEditor);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryEditor);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    String _name_1 = EditorModel.class.getName();
    _builder.append(_name_1, "");
    _builder.append("<");
    Dictionary _parentDictionary = ModelUtil.getParentDictionary(dictionaryEditor);
    Entity _entity = _parentDictionary.getEntity();
    String _voFullQualifiedName = this._dictionaryNameUtils.voFullQualifiedName(_entity);
    _builder.append(_voFullQualifiedName, "");
    _builder.append("> {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private class RootComposite extends ");
    String _name_2 = CompositeModel.class.getName();
    _builder.append(_name_2, "	");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    EList<DictionaryContainerContent> _containercontents = dictionaryEditor.getContainercontents();
    CharSequence _dictionaryClass = this._dictionaryContainerGenerator.dictionaryClass(_containercontents);
    _builder.append(_dictionaryClass, "		");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public RootComposite(");
    String _name_3 = BaseModel.class.getName();
    _builder.append(_name_3, "		");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("super(\"");
    _builder.append(ICompositeModel.ROOT_COMPOSITE_NAME, "			");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t\t");
    EList<DictionaryContainerContent> _containercontents_1 = dictionaryEditor.getContainercontents();
    CharSequence _dictionaryContainerContentsConstructor = this._dictionaryContainerGenerator.dictionaryContainerContentsConstructor(_containercontents_1);
    _builder.append(_dictionaryContainerContentsConstructor, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private RootComposite rootComposite = new RootComposite(this);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryEditor);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("(");
    String _name_4 = BaseModel.class.getName();
    _builder.append(_name_4, "	");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_5 = dictionaryEditor.getName();
    _builder.append(_name_5, "		");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      String _label = dictionaryEditor.getLabel();
      boolean _notEquals = (!Objects.equal(_label, null));
      if (_notEquals) {
        _builder.append("\t\t");
        _builder.append("setLabel(\"");
        String _label_1 = dictionaryEditor.getLabel();
        _builder.append(_label_1, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("setCompositeModel(rootComposite);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    EList<DictionaryContainerContent> _containercontents_2 = dictionaryEditor.getContainercontents();
    CharSequence _dictionaryContainerContentsConstants = this._dictionaryContainerGenerator.dictionaryContainerContentsConstants(_containercontents_2);
    _builder.append(_dictionaryContainerContentsConstants, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final DictionaryResult dictionaryResult) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryResult);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryResult);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryResult);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final DictionaryFilter dictionaryFilter) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryFilter);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryFilter);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryFilter);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence dictionaryClass(final Dictionary dictionary) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionary);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionary);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    String _name_1 = DictionaryModel.class.getName();
    _builder.append(_name_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// label controls");
    _builder.newLine();
    {
      EList<DictionaryControl> _labelcontrols = dictionary.getLabelcontrols();
      for(final DictionaryControl dictionaryControl : _labelcontrols) {
        _builder.append("\t");
        CharSequence _dictionaryConstant = this._dictionaryControls.dictionaryConstant(dictionaryControl);
        _builder.append(_dictionaryConstant, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      DictionaryEditor _dictionaryeditor = dictionary.getDictionaryeditor();
      boolean _notEquals = (!Objects.equal(_dictionaryeditor, null));
      if (_notEquals) {
        _builder.append("\t");
        _builder.append("// dictionary editor");
        _builder.newLine();
        _builder.append("\t");
        DictionaryEditor _dictionaryeditor_1 = dictionary.getDictionaryeditor();
        CharSequence _dictionaryConstant_1 = this.dictionaryConstant(_dictionaryeditor_1);
        _builder.append(_dictionaryConstant_1, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      DictionarySearch _dictionarysearch = dictionary.getDictionarysearch();
      boolean _notEquals_1 = (!Objects.equal(_dictionarysearch, null));
      if (_notEquals_1) {
        _builder.append("\t");
        _builder.append("// dictionary search");
        _builder.newLine();
        _builder.append("\t");
        DictionarySearch _dictionarysearch_1 = dictionary.getDictionarysearch();
        CharSequence _dictionaryConstant_2 = this.dictionaryConstant(_dictionarysearch_1);
        _builder.append(_dictionaryConstant_2, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionary);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_2 = dictionary.getName();
    _builder.append(_name_2, "		");
    _builder.append("\", null);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("setVoName(");
    Entity _entity = dictionary.getEntity();
    String _voFullQualifiedName = this._dictionaryNameUtils.voFullQualifiedName(_entity);
    _builder.append(_voFullQualifiedName, "		");
    _builder.append(".class);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// label controls");
    _builder.newLine();
    {
      EList<DictionaryControl> _labelcontrols_1 = dictionary.getLabelcontrols();
      for(final DictionaryControl labelcontrol : _labelcontrols_1) {
        _builder.append("\t\t");
        Object _dictionaryControlConstantSetters = this._dictionaryControls.dictionaryControlConstantSetters(labelcontrol);
        _builder.append(_dictionaryControlConstantSetters, "		");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("this.getLabelControls().add(");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(labelcontrol);
        _builder.append(_dictionaryConstantName, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      DictionaryEditor _dictionaryeditor_2 = dictionary.getDictionaryeditor();
      boolean _notEquals_2 = (!Objects.equal(_dictionaryeditor_2, null));
      if (_notEquals_2) {
        _builder.append("\t\t");
        _builder.append("// dictionary editor \'");
        DictionaryEditor _dictionaryeditor_3 = dictionary.getDictionaryeditor();
        String _name_3 = _dictionaryeditor_3.getName();
        _builder.append(_name_3, "		");
        _builder.append("\'");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("setEditorModel(");
        DictionaryEditor _dictionaryeditor_4 = dictionary.getDictionaryeditor();
        String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(_dictionaryeditor_4);
        _builder.append(_dictionaryConstantName_1, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      DictionarySearch _dictionarysearch_2 = dictionary.getDictionarysearch();
      boolean _notEquals_3 = (!Objects.equal(_dictionarysearch_2, null));
      if (_notEquals_3) {
        _builder.append("\t\t");
        _builder.append("// dictionary search \'");
        DictionarySearch _dictionarysearch_3 = dictionary.getDictionarysearch();
        String _name_4 = _dictionarysearch_3.getName();
        _builder.append(_name_4, "		");
        _builder.append("\'");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("setSearchModel(");
        DictionarySearch _dictionarysearch_4 = dictionary.getDictionarysearch();
        String _dictionaryConstantName_2 = this._dictionaryNameUtils.dictionaryConstantName(_dictionarysearch_4);
        _builder.append(_dictionaryConstantName_2, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      String _label = dictionary.getLabel();
      boolean _notEquals_4 = (!Objects.equal(_label, null));
      if (_notEquals_4) {
        _builder.append("\t\t");
        _builder.append("setLabel(\"");
        String _label_1 = dictionary.getLabel();
        _builder.append(_label_1, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      String _pluralLabel = dictionary.getPluralLabel();
      boolean _notEquals_5 = (!Objects.equal(_pluralLabel, null));
      if (_notEquals_5) {
        _builder.append("\t\t");
        _builder.append("setPluralLabel(\"");
        String _pluralLabel_1 = dictionary.getPluralLabel();
        _builder.append(_pluralLabel_1, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _dictionaryConstant(final DictionarySearch dictionarySearch) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionarySearch);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionarySearch);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionarySearch);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public void dictionaryFilterGenerator(final DictionaryFilter dictionaryFilter, final IFileSystemAccess fsa) {
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryFilter);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionaryFilter);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
    EList<DictionaryContainerContent> _containercontents = dictionaryFilter.getContainercontents();
    this._dictionaryContainerGenerator.dictionaryGenerator(_containercontents, fsa);
  }
  
  public void dictionaryResultGenerator(final DictionaryResult dictionaryResult, final IFileSystemAccess fsa) {
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryResult);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionaryResult);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
  }
  
  public void dictionaryGenerator(final DictionarySearch dictionarySearch, final IFileSystemAccess fsa) {
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionarySearch);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionarySearch);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
    DictionaryResult _dictionaryresult = dictionarySearch.getDictionaryresult();
    this.dictionaryResultGenerator(_dictionaryresult, fsa);
    EList<DictionaryFilter> _dictionaryfilters = dictionarySearch.getDictionaryfilters();
    for (final DictionaryFilter dictionaryfilter : _dictionaryfilters) {
      this.dictionaryFilterGenerator(dictionaryfilter, fsa);
    }
  }
  
  public CharSequence dictionaryClass(final DictionarySearch dictionarySearch) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionarySearch);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionarySearch);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    String _name_1 = SearchModel.class.getName();
    _builder.append(_name_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    DictionaryResult _dictionaryresult = dictionarySearch.getDictionaryresult();
    CharSequence _dictionaryConstant = this.dictionaryConstant(_dictionaryresult);
    _builder.append(_dictionaryConstant, "	");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryFilter> _dictionaryfilters = dictionarySearch.getDictionaryfilters();
      for(final DictionaryFilter dictionaryFilter : _dictionaryfilters) {
        _builder.append("\t");
        CharSequence _dictionaryConstant_1 = this.dictionaryConstant(dictionaryFilter);
        _builder.append(_dictionaryConstant_1, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionarySearch);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("(");
    String _name_2 = BaseModel.class.getName();
    _builder.append(_name_2, "	");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_3 = dictionarySearch.getName();
    _builder.append(_name_3, "		");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    {
      String _label = dictionarySearch.getLabel();
      boolean _notEquals = (!Objects.equal(_label, null));
      if (_notEquals) {
        _builder.append("\t\t");
        _builder.append("setLabel(\"");
        String _label_1 = dictionarySearch.getLabel();
        _builder.append(_label_1, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// result");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("setResultModel(");
    DictionaryResult _dictionaryresult_1 = dictionarySearch.getDictionaryresult();
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(_dictionaryresult_1);
    _builder.append(_dictionaryConstantName, "		");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// filters");
    _builder.newLine();
    {
      EList<DictionaryFilter> _dictionaryfilters_1 = dictionarySearch.getDictionaryfilters();
      for(final DictionaryFilter dictionaryfilter : _dictionaryfilters_1) {
        _builder.append("\t\t");
        _builder.append("getFilterModels().add(");
        String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryfilter);
        _builder.append(_dictionaryConstantName_1, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence dictionaryClass(final DictionaryResult dictionaryResult) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryResult);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryResult);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    String _name_1 = ResultModel.class.getName();
    _builder.append(_name_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _resultcolumns = dictionaryResult.getResultcolumns();
      for(final DictionaryControl dictionaryControl : _resultcolumns) {
        _builder.append("\t");
        CharSequence _dictionaryConstant = this._dictionaryControls.dictionaryConstant(dictionaryControl);
        _builder.append(_dictionaryConstant, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryResult);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("(");
    String _name_2 = BaseModel.class.getName();
    _builder.append(_name_2, "	");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_3 = dictionaryResult.getName();
    _builder.append(_name_3, "		");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _resultcolumns_1 = dictionaryResult.getResultcolumns();
      for(final DictionaryControl dictionaryControl_1 : _resultcolumns_1) {
        _builder.append("\t\t");
        _builder.append("this.getControls().add(");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl_1);
        _builder.append(_dictionaryConstantName, "		");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<DictionaryControl> _resultcolumns_2 = dictionaryResult.getResultcolumns();
      for(final DictionaryControl dictionaryControl_2 : _resultcolumns_2) {
        _builder.append("\t\t");
        Object _dictionaryControlConstantSetters = this._dictionaryControls.dictionaryControlConstantSetters(dictionaryControl_2);
        _builder.append(_dictionaryControlConstantSetters, "		");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence dictionaryClass(final DictionaryFilter dictionaryFilter) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryFilter);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryFilter);
    _builder.append(_dictionaryClassName, "");
    _builder.append(" extends ");
    String _name_1 = FilterModel.class.getName();
    _builder.append(_name_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private class RootComposite extends ");
    String _name_2 = CompositeModel.class.getName();
    _builder.append(_name_2, "	");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    EList<DictionaryContainerContent> _containercontents = dictionaryFilter.getContainercontents();
    CharSequence _dictionaryClass = this._dictionaryContainerGenerator.dictionaryClass(_containercontents);
    _builder.append(_dictionaryClass, "		");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public RootComposite(");
    String _name_3 = BaseModel.class.getName();
    _builder.append(_name_3, "		");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("super(\"");
    _builder.append(ICompositeModel.ROOT_COMPOSITE_NAME, "			");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    EList<DictionaryContainerContent> _containercontents_1 = dictionaryFilter.getContainercontents();
    CharSequence _dictionaryContainerContentsConstructor = this._dictionaryContainerGenerator.dictionaryContainerContentsConstructor(_containercontents_1);
    _builder.append(_dictionaryContainerContentsConstructor, "			");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private RootComposite rootComposite = new RootComposite(this);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryFilter);
    _builder.append(_dictionaryClassName_1, "	");
    _builder.append("(");
    String _name_4 = BaseModel.class.getName();
    _builder.append(_name_4, "	");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_5 = dictionaryFilter.getName();
    _builder.append(_name_5, "		");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("setCompositeModel(rootComposite);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    EList<DictionaryContainerContent> _containercontents_2 = dictionaryFilter.getContainercontents();
    CharSequence _dictionaryContainerContentsConstants = this._dictionaryContainerGenerator.dictionaryContainerContentsConstants(_containercontents_2);
    _builder.append(_dictionaryContainerContentsConstants, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence dictionaryConstant(final EObject dictionary) {
    if (dictionary instanceof Dictionary) {
      return _dictionaryConstant((Dictionary)dictionary);
    } else if (dictionary instanceof DictionaryEditor) {
      return _dictionaryConstant((DictionaryEditor)dictionary);
    } else if (dictionary instanceof DictionaryFilter) {
      return _dictionaryConstant((DictionaryFilter)dictionary);
    } else if (dictionary instanceof DictionaryResult) {
      return _dictionaryConstant((DictionaryResult)dictionary);
    } else if (dictionary instanceof DictionarySearch) {
      return _dictionaryConstant((DictionarySearch)dictionary);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dictionary).toString());
    }
  }
}
