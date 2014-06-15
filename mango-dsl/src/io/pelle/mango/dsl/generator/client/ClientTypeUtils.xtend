package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityDataType

class ClientTypeUtils extends TypeUtils {

	@Inject
	extension ClientNameUtils

	override dispatch String getType(EntityDataType dataType) {
		return dataType.entity.type
	}

	override dispatch String getType(Entity entity) {
		entity.voFullQualifiedName
	}

	override dispatch String getTypeClass(Entity entity) {
		getType(entity) + ".class"
	}

}
