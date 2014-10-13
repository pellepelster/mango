package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityDataType
import io.pelle.mango.dsl.mango.ValueObject
import io.pelle.mango.dsl.mango.ValueObjectType

class ClientTypeUtils extends TypeUtils {

	@Inject
	extension ClientNameUtils clientNameUtils

	override String entityVOFullQualifiedName(Entity entity)
	{
		clientNameUtils.voFullQualifiedName(entity)
	}
	
	override dispatch String getType(Entity entity) {
		entity.voFullQualifiedName
	}

	override dispatch String getTypeClass(Entity entity) {
		getType(entity) + ".class"
	}
	
	def dispatch String getType(ValueObjectType valueObjectType)
	{
		return valueObjectType.type.voFullQualifiedName
	}
	
	def dispatch String getType(ValueObject valueObject)
	{
		return valueObject.voFullQualifiedName
	}
	

}
