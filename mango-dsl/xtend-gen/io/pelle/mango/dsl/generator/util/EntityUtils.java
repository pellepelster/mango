package io.pelle.mango.dsl.generator.util;

import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.mango.Entity;

@SuppressWarnings("all")
public class EntityUtils {
  public boolean isExtendedByOtherEntity(final Entity entity) {
    return ModelUtil.isExtendedByOtherEntity(entity);
  }
}
