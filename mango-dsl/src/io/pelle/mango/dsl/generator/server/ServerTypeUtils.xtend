package io.pelle.mango.dsl.generator.server

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import java.util.List

class ServerTypeUtils extends TypeUtils {

	@Inject
	extension ServerNameUtils serverNameUtils

	@Inject
	extension ServerAttributeUtils attributeUtils

	@Inject
	extension AttributeGeneratorFactory attributeGeneratorFactory

	def hierarchicalEntityAttributes() {

		var List<AttributeGeneratorFactory.AttributeGenerator> hierarchicalEntityAttributes = newArrayList()

		hierarchicalEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentClassName", typeof(String), this, attributeUtils))
		hierarchicalEntityAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentId", typeof(Long), this, attributeUtils))

		return hierarchicalEntityAttributes
	}

	override def String entityVOFullQualifiedName(Entity entity) {
		serverNameUtils.entityFullQualifiedName(entity)
	}

	override getAttributeUtils() {
		attributeUtils
	}

	override getAttributeGeneratorFactory() {
		attributeGeneratorFactory
	}

}
