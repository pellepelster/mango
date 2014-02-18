package io.pelle.mango.dsl.generator;

import com.google.inject.Inject;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class BaseEntityGenerator {
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  public CharSequence compileGetAttributeDescriptors(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("public static ");
    String _name = IAttributeDescriptor.class.getName();
    _builder.append(_name, "");
    _builder.append("<?>[] getAttributeDescriptors() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return new ");
    String _name_1 = IAttributeDescriptor.class.getName();
    _builder.append(_name_1, "\t");
    _builder.append("[]{");
    _builder.newLineIfNotEmpty();
    {
      EList<EntityAttribute> _attributes = entity.getAttributes();
      boolean _hasElements = false;
      for(final EntityAttribute attribute : _attributes) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(", ", "\t\t");
        }
        _builder.append("\t\t");
        String _name_2 = attribute.getName();
        String _attributeConstantName = this._clientNameUtils.attributeConstantName(_name_2);
        _builder.append(_attributeConstantName, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("};");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
