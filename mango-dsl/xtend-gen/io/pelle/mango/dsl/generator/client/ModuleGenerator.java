package io.pelle.mango.dsl.generator.client;

import com.google.inject.Inject;
import io.pelle.mango.dsl.mango.ModuleDefinition;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ModuleGenerator {
  @Inject
  @Extension
  private /* ClientNameUtils */Object _clientNameUtils;
  
  @Inject
  @Extension
  private /* ClientTypeUtils */Object _clientTypeUtils;
  
  public CharSequence compileBaseModuleDefinition(final ModuleDefinition moduleDefinition) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getPackageName is undefined for the type ModuleGenerator"
      + "\nThe method baseModuleDefinitionName is undefined for the type ModuleGenerator"
      + "\nThe method baseModuleDefinitionName is undefined for the type ModuleGenerator"
      + "\nThe method getType is undefined for the type ModuleGenerator"
      + "\nThe method getType is undefined for the type ModuleGenerator"
      + "\nThe method parseSimpleTypeFromString is undefined for the type ModuleGenerator"
      + "\nThe method getType is undefined for the type ModuleGenerator");
  }
  
  public CharSequence moduleDefinitionParameters(final ModuleDefinition moduleDefinition) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getType is undefined for the type ModuleGenerator");
  }
}
