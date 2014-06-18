package io.pelle.mango.dsl.generator.util

import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.mango.Entity

class EntityUtils {

	def isExtendedByOtherEntity(Entity entity) {
		return ModelUtil.isExtendedByOtherEntity(entity)
	}

}
