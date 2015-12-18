package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.Model

class ServiceNameUtils {

	@Inject
	extension ServerNameUtils serverNameUtils

	// -------------------------------------------------------------------------
	// rest remote services
	// -------------------------------------------------------------------------
	def restRemoteServicesApplicationContextFullQualifiedName(Model model) {
		return model.restRemoteServicesApplicationContextPackageName() + "." + model.restRemoteServicesApplicationContextName;
	}

	def restRemoteServicesApplicationContextPackageName(Model model) {
		return modelPackageName(model)
	}

	def restRemoteServicesApplicationContextName(Model model) {
		return model.modelName.toFirstUpper + "RestRemoteServicesGen"
	}
	
	// -------------------------------------------------------------------------
	// gwt remote services
	// -------------------------------------------------------------------------
	def gwtRemoteServicesApplicationContextFullQualifiedName(Model model) {
		return model.gwtRemoteServicesApplicationContextPackageName() + "." + model.gwtRemoteServicesApplicationContextName;
	}

	def gwtRemoteServicesApplicationContextPackageName(Model model) {
		return modelPackageName(model)
	}

	def gwtRemoteServicesApplicationContextName(Model model) {
		return model.modelName.toFirstUpper + "GWTRemoteServicesGen"
	}
	
	// -------------------------------------------------------------------------
	// spring services
	// -------------------------------------------------------------------------
	def springServicesApplicationContextFullQualifiedName(Model model) {
		return model.springServicesApplicationContextPackageName() + "." + model.springServicesApplicationContextName;
	}

	def springServicesApplicationContextPackageName(Model model) {
		return modelPackageName(model)
	}

	def springServicesApplicationContextName(Model model) {
		return model.modelName.toFirstUpper + "SpringServicesGen"
	}

	// -------------------------------------------------------------------------
	// spring invoker services
	// -------------------------------------------------------------------------
	def springInvokerServicesApplicationContextFullQualifiedName(Model model) {
		return model.springInvokerServicesApplicationContextPackageName() + "." + model.springInvokerServicesApplicationContextName;
	}

	def springInvokerServicesApplicationContextPackageName(Model model) {
		return modelPackageName(model)
	}

	def springInvokerServicesApplicationContextName(Model model) {
		return model.modelName.toFirstUpper + "SpringInvokerServicesGen"
	}

	// -------------------------------------------------------------------------
	// spring invoker client services
	// -------------------------------------------------------------------------
	def springInvokerClientServicesApplicationContextFullQualifiedName(Model model) {
		return model.springInvokerClientServicesApplicationContextPackageName() + "." + model.springInvokerClientServicesApplicationContextName;
	}

	def springInvokerClientServicesApplicationContextPackageName(Model model) {
		return modelPackageName(model)
	}

	def springInvokerClientServicesApplicationContextName(Model model) {
		return model.modelName.toFirstUpper + "SpringInvokerClientServicesGen"
	}

}
