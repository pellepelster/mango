package io.pelle.mango.dsl.generator.client

import javax.inject.Inject
import io.pelle.mango.dsl.generator.util.BaseAttributeUtils

class ClientAttributeUtils extends BaseAttributeUtils {

	@Inject
	extension ClientTypeUtils typeUtils

	override getTypeUtils() {
		typeUtils
	}

}
