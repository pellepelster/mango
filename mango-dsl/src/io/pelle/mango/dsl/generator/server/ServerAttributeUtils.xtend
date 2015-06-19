package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.server.ServerTypeUtils
import javax.inject.Inject
import io.pelle.mango.dsl.generator.util.BaseAttributeUtils

class ServerAttributeUtils extends BaseAttributeUtils {

	@Inject
	extension ServerTypeUtils typeUtils

	override getTypeUtils() {
		typeUtils
	}

}
