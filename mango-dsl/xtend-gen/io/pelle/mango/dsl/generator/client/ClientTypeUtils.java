package io.pelle.mango.dsl.generator.client;

import com.google.inject.Inject;
import io.pelle.mango.dsl.generator.TypeUtils;
import io.pelle.mango.dsl.generator.client.ClientNameUtils;
import io.pelle.mango.dsl.mango.BinaryDataType;
import io.pelle.mango.dsl.mango.BinaryEntityAttribute;
import io.pelle.mango.dsl.mango.BooleanDataType;
import io.pelle.mango.dsl.mango.BooleanEntityAttribute;
import io.pelle.mango.dsl.mango.CustomEntityAttribute;
import io.pelle.mango.dsl.mango.CustomType;
import io.pelle.mango.dsl.mango.Datatype;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityDataType;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;
import io.pelle.mango.dsl.mango.EntityType;
import io.pelle.mango.dsl.mango.Enumeration;
import io.pelle.mango.dsl.mango.EnumerationDataType;
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute;
import io.pelle.mango.dsl.mango.GenericEntityAttribute;
import io.pelle.mango.dsl.mango.GenericType;
import io.pelle.mango.dsl.mango.GenericTypeDefinition;
import io.pelle.mango.dsl.mango.IntegerDataType;
import io.pelle.mango.dsl.mango.IntegerEntityAttribute;
import io.pelle.mango.dsl.mango.LongDataType;
import io.pelle.mango.dsl.mango.LongEntityAttribute;
import io.pelle.mango.dsl.mango.MangoEntityAttribute;
import io.pelle.mango.dsl.mango.MangoType;
import io.pelle.mango.dsl.mango.MangoTypes;
import io.pelle.mango.dsl.mango.MapEntityAttribute;
import io.pelle.mango.dsl.mango.SimpleTypeType;
import io.pelle.mango.dsl.mango.SimpleTypes;
import io.pelle.mango.dsl.mango.StringDataType;
import io.pelle.mango.dsl.mango.StringEntityAttribute;
import io.pelle.mango.dsl.mango.ValueObjectType;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ClientTypeUtils extends TypeUtils {
  @Inject
  @Extension
  private ClientNameUtils _clientNameUtils;
  
  protected String _getType(final EntityDataType dataType) {
    Entity _entity = dataType.getEntity();
    return this.getType(_entity);
  }
  
  protected String _getType(final Entity entity) {
    return this._clientNameUtils.voFullQualifiedName(entity);
  }
  
  protected String _getTypeClass(final Entity entity) {
    String _type = this.getType(entity);
    return (_type + ".class");
  }
  
  public String getType(final Object dataType) {
    if (dataType instanceof BinaryDataType) {
      return _getType((BinaryDataType)dataType);
    } else if (dataType instanceof BinaryEntityAttribute) {
      return _getType((BinaryEntityAttribute)dataType);
    } else if (dataType instanceof BooleanDataType) {
      return _getType((BooleanDataType)dataType);
    } else if (dataType instanceof BooleanEntityAttribute) {
      return _getType((BooleanEntityAttribute)dataType);
    } else if (dataType instanceof EnumerationDataType) {
      return _getType((EnumerationDataType)dataType);
    } else if (dataType instanceof EnumerationEntityAttribute) {
      return _getType((EnumerationEntityAttribute)dataType);
    } else if (dataType instanceof IntegerDataType) {
      return _getType((IntegerDataType)dataType);
    } else if (dataType instanceof IntegerEntityAttribute) {
      return _getType((IntegerEntityAttribute)dataType);
    } else if (dataType instanceof LongEntityAttribute) {
      return _getType((LongEntityAttribute)dataType);
    } else if (dataType instanceof MapEntityAttribute) {
      return _getType((MapEntityAttribute)dataType);
    } else if (dataType instanceof StringDataType) {
      return _getType((StringDataType)dataType);
    } else if (dataType instanceof StringEntityAttribute) {
      return _getType((StringEntityAttribute)dataType);
    } else if (dataType instanceof EntityDataType) {
      return _getType((EntityDataType)dataType);
    } else if (dataType instanceof EntityEntityAttribute) {
      return _getType((EntityEntityAttribute)dataType);
    } else if (dataType instanceof CustomEntityAttribute) {
      return _getType((CustomEntityAttribute)dataType);
    } else if (dataType instanceof CustomType) {
      return _getType((CustomType)dataType);
    } else if (dataType instanceof Datatype) {
      return _getType((Datatype)dataType);
    } else if (dataType instanceof Entity) {
      return _getType((Entity)dataType);
    } else if (dataType instanceof EntityAttribute) {
      return _getType((EntityAttribute)dataType);
    } else if (dataType instanceof EntityType) {
      return _getType((EntityType)dataType);
    } else if (dataType instanceof Enumeration) {
      return _getType((Enumeration)dataType);
    } else if (dataType instanceof GenericEntityAttribute) {
      return _getType((GenericEntityAttribute)dataType);
    } else if (dataType instanceof GenericType) {
      return _getType((GenericType)dataType);
    } else if (dataType instanceof MangoEntityAttribute) {
      return _getType((MangoEntityAttribute)dataType);
    } else if (dataType instanceof MangoType) {
      return _getType((MangoType)dataType);
    } else if (dataType instanceof SimpleTypeType) {
      return _getType((SimpleTypeType)dataType);
    } else if (dataType instanceof ValueObjectType) {
      return _getType((ValueObjectType)dataType);
    } else if (dataType instanceof GenericTypeDefinition) {
      return _getType((GenericTypeDefinition)dataType);
    } else if (dataType instanceof LongDataType) {
      return _getType((LongDataType)dataType);
    } else if (dataType instanceof MangoTypes) {
      return _getType((MangoTypes)dataType);
    } else if (dataType instanceof SimpleTypes) {
      return _getType((SimpleTypes)dataType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dataType).toString());
    }
  }
  
  public String getTypeClass(final EObject entity) {
    if (entity instanceof StringEntityAttribute) {
      return _getTypeClass((StringEntityAttribute)entity);
    } else if (entity instanceof EntityEntityAttribute) {
      return _getTypeClass((EntityEntityAttribute)entity);
    } else if (entity instanceof Datatype) {
      return _getTypeClass((Datatype)entity);
    } else if (entity instanceof Entity) {
      return _getTypeClass((Entity)entity);
    } else if (entity instanceof EntityAttribute) {
      return _getTypeClass((EntityAttribute)entity);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entity).toString());
    }
  }
}
