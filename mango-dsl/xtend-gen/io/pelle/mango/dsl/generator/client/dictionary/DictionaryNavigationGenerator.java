package io.pelle.mango.dsl.generator.client.dictionary;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionarySearch;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.Module;
import io.pelle.mango.dsl.mango.NavigationNode;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class DictionaryNavigationGenerator {
  @Inject
  @Extension
  private DictionaryNameUtils _dictionaryNameUtils;
  
  public void dictionaryNavigationGenerator(final Model model, final IFileSystemAccess fsa) {
    TreeIterator<EObject> _eAllContents = model.eAllContents();
    Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_eAllContents);
    Iterable<NavigationNode> _filter = Iterables.<NavigationNode>filter(_iterable, NavigationNode.class);
    boolean _isEmpty = IterableExtensions.isEmpty(_filter);
    boolean _not = (!_isEmpty);
    if (_not) {
      TreeIterator<EObject> _eAllContents_1 = model.eAllContents();
      Iterable<EObject> _iterable_1 = IteratorExtensions.<EObject>toIterable(_eAllContents_1);
      Iterable<NavigationNode> _filter_1 = Iterables.<NavigationNode>filter(_iterable_1, NavigationNode.class);
      for (final NavigationNode navigationNode : _filter_1) {
        String _navigationNodeClassFullQualifiedFileName = this._dictionaryNameUtils.navigationNodeClassFullQualifiedFileName(navigationNode);
        CharSequence _dictionaryNavigationNodeClass = this.dictionaryNavigationNodeClass(navigationNode);
        fsa.generateFile(_navigationNodeClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryNavigationNodeClass);
      }
      String _navigationNodeClassFullQualifiedFileName_1 = this._dictionaryNameUtils.navigationNodeClassFullQualifiedFileName(model);
      CharSequence _dictionaryNavigationRootNodeClass = this.dictionaryNavigationRootNodeClass(model);
      fsa.generateFile(_navigationNodeClassFullQualifiedFileName_1, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryNavigationRootNodeClass);
    }
  }
  
  public CharSequence dictionaryNavigationRootNodeClass(final Model model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _modelPackageName = this._dictionaryNameUtils.modelPackageName(model);
    _builder.append(_modelPackageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _navigationNodeClassName = this._dictionaryNameUtils.navigationNodeClassName(model);
    _builder.append(_navigationNodeClassName, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static class RootNavigationNode extends ");
    String _name = NavigationTreeElement.class.getName();
    _builder.append(_name, "\t");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      TreeIterator<EObject> _eAllContents = model.eAllContents();
      Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_eAllContents);
      Iterable<NavigationNode> _filter = Iterables.<NavigationNode>filter(_iterable, NavigationNode.class);
      final Function1<NavigationNode, Boolean> _function = new Function1<NavigationNode, Boolean>() {
        public Boolean apply(final NavigationNode it) {
          EObject _eContainer = it.eContainer();
          return Boolean.valueOf((!(_eContainer instanceof NavigationNode)));
        }
      };
      Iterable<NavigationNode> _filter_1 = IterableExtensions.<NavigationNode>filter(_filter, _function);
      for(final NavigationNode navigationNode : _filter_1) {
        _builder.append("\t");
        _builder.append("public ");
        String _navigationyNodeClassFullQualifiedName = this._dictionaryNameUtils.navigationyNodeClassFullQualifiedName(navigationNode);
        _builder.append(_navigationyNodeClassFullQualifiedName, "\t");
        _builder.append(" ");
        String _navigationNodeConstantName = this._dictionaryNameUtils.navigationNodeConstantName(navigationNode);
        _builder.append(_navigationNodeConstantName, "\t");
        _builder.append(" = new ");
        String _navigationyNodeClassFullQualifiedName_1 = this._dictionaryNameUtils.navigationyNodeClassFullQualifiedName(navigationNode);
        _builder.append(_navigationyNodeClassFullQualifiedName_1, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public RootNavigationNode() {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("super(\"ROOT\");");
    _builder.newLine();
    _builder.newLine();
    {
      TreeIterator<EObject> _eAllContents_1 = model.eAllContents();
      Iterable<EObject> _iterable_1 = IteratorExtensions.<EObject>toIterable(_eAllContents_1);
      Iterable<NavigationNode> _filter_2 = Iterables.<NavigationNode>filter(_iterable_1, NavigationNode.class);
      final Function1<NavigationNode, Boolean> _function_1 = new Function1<NavigationNode, Boolean>() {
        public Boolean apply(final NavigationNode it) {
          EObject _eContainer = it.eContainer();
          return Boolean.valueOf((!(_eContainer instanceof NavigationNode)));
        }
      };
      Iterable<NavigationNode> _filter_3 = IterableExtensions.<NavigationNode>filter(_filter_2, _function_1);
      for(final NavigationNode navigationNode_1 : _filter_3) {
        _builder.append("\t\t\t");
        _builder.append("getChildren().add(");
        String _navigationNodeConstantName_1 = this._dictionaryNameUtils.navigationNodeConstantName(navigationNode_1);
        _builder.append(_navigationNodeConstantName_1, "\t\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("setLabel(RootNavigationNode.class.getName());");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static RootNavigationNode ROOT = new RootNavigationNode();");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence dictionaryNavigationNodeClass(final NavigationNode navigationNode) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(navigationNode);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _navigationNodeClassName = this._dictionaryNameUtils.navigationNodeClassName(navigationNode);
    _builder.append(_navigationNodeClassName, "");
    _builder.append(" extends ");
    String _name = NavigationTreeElement.class.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    {
      EList<EObject> _eContents = navigationNode.eContents();
      Iterable<NavigationNode> _filter = Iterables.<NavigationNode>filter(_eContents, NavigationNode.class);
      for(final NavigationNode childNavigationNode : _filter) {
        _builder.append("\t");
        _builder.append("public ");
        String _navigationyNodeClassFullQualifiedName = this._dictionaryNameUtils.navigationyNodeClassFullQualifiedName(childNavigationNode);
        _builder.append(_navigationyNodeClassFullQualifiedName, "\t");
        _builder.append(" ");
        String _navigationNodeConstantName = this._dictionaryNameUtils.navigationNodeConstantName(childNavigationNode);
        _builder.append(_navigationNodeConstantName, "\t");
        _builder.append(" = new ");
        String _navigationyNodeClassFullQualifiedName_1 = this._dictionaryNameUtils.navigationyNodeClassFullQualifiedName(childNavigationNode);
        _builder.append(_navigationyNodeClassFullQualifiedName_1, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _navigationNodeClassName_1 = this._dictionaryNameUtils.navigationNodeClassName(navigationNode);
    _builder.append(_navigationNodeClassName_1, "\t");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _name_1 = navigationNode.getName();
    _builder.append(_name_1, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<EObject> _eContents_1 = navigationNode.eContents();
      Iterable<NavigationNode> _filter_1 = Iterables.<NavigationNode>filter(_eContents_1, NavigationNode.class);
      for(final NavigationNode childNavigationNode_1 : _filter_1) {
        _builder.append("\t\t");
        _builder.append("getChildren().add(");
        String _navigationNodeConstantName_1 = this._dictionaryNameUtils.navigationNodeConstantName(childNavigationNode_1);
        _builder.append(_navigationNodeConstantName_1, "\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("setLabel(\"");
    String _name_2 = navigationNode.getName();
    _builder.append(_name_2, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    {
      Module _module = navigationNode.getModule();
      boolean _notEquals = (!Objects.equal(_module, null));
      if (_notEquals) {
        _builder.append("\t\t");
        _builder.append("setModuleLocator(");
        _builder.append(IModuleUI.UI_MODULE_ID_PARAMETER_NAME, "\t\t");
        _builder.append(" + \"=");
        Module _module_1 = navigationNode.getModule();
        String _name_3 = _module_1.getName();
        _builder.append(_name_3, "\t\t");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      } else {
        DictionaryEditor _dictionaryEditor = navigationNode.getDictionaryEditor();
        boolean _notEquals_1 = (!Objects.equal(_dictionaryEditor, null));
        if (_notEquals_1) {
          _builder.append("\t\t");
          _builder.append("setModuleLocator(\"");
          _builder.append(IModuleUI.UI_MODULE_ID_PARAMETER_NAME, "\t\t");
          _builder.append("=DictionaryEditor&\" + io.pelle.mango.client.modules.BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID + \"=");
          DictionaryEditor _dictionaryEditor_1 = navigationNode.getDictionaryEditor();
          Dictionary _parentDictionary = ModelUtil.getParentDictionary(_dictionaryEditor_1);
          String _name_4 = _parentDictionary.getName();
          _builder.append(_name_4, "\t\t");
          _builder.append("\");");
          _builder.newLineIfNotEmpty();
        } else {
          DictionarySearch _dictionarySearch = navigationNode.getDictionarySearch();
          boolean _notEquals_2 = (!Objects.equal(_dictionarySearch, null));
          if (_notEquals_2) {
            _builder.append("\t\t");
            _builder.append("setModuleLocator(\"");
            _builder.append(IModuleUI.UI_MODULE_ID_PARAMETER_NAME, "\t\t");
            _builder.append("=DictionarySearch&\" + io.pelle.mango.client.modules.BaseDictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID + \"=");
            DictionarySearch _dictionarySearch_1 = navigationNode.getDictionarySearch();
            Dictionary _parentDictionary_1 = ModelUtil.getParentDictionary(_dictionarySearch_1);
            String _name_5 = _parentDictionary_1.getName();
            _builder.append(_name_5, "\t\t");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          } else {
            String _name_6 = navigationNode.getName();
            boolean _notEquals_3 = (!Objects.equal(_name_6, null));
            if (_notEquals_3) {
              _builder.append("\t\t");
              _builder.append("setModuleLocator(\"");
              _builder.append(IModuleUI.UI_MODULE_ID_PARAMETER_NAME, "\t\t");
              _builder.append("=ModuleNavigationOverview&\" + io.pelle.mango.client.modules.BaseModuleNavigationModule.NAVIGATIONTREEELEMENTNAME_PARAMETER_ID + \"=");
              String _name_7 = navigationNode.getName();
              _builder.append(_name_7, "\t\t");
              _builder.append("\");");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
