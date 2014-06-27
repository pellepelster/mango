package io.pelle.mango.dsl.generator.server;

import com.google.inject.Inject;
import io.pelle.mango.dsl.mango.Model;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class SpringGenerator {
  @Inject
  @Extension
  private /* NameUtils */Object _nameUtils;
  
  public CharSequence compileSpringDBApplicationContext(final Model model) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method persistenceUnitName is undefined for the type SpringGenerator"
      + "\nThe method jndiName is undefined for the type SpringGenerator"
      + "\nThe method persistenceUnitName is undefined for the type SpringGenerator");
  }
  
  public CharSequence compileBaseApplicationContext(final Model model) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method modelPackageName is undefined for the type SpringGenerator");
  }
  
  public CharSequence compilePersistenceXml(final Model model) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method persistenceUnitName is undefined for the type SpringGenerator"
      + "\nThe method entityFullQualifiedName is undefined for the type SpringGenerator");
  }
}
