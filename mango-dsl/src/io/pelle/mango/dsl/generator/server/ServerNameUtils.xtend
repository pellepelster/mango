package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.MethodParameter
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.PackageDeclaration
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.SimpleDatatypeEntityAttribute
import java.util.List
import io.pelle.mango.dsl.mango.ServiceMethod

class ServerNameUtils extends NameUtils {

	override dispatch String getPackageName(PackageDeclaration packageDeclaration) {
		if (packageDeclaration.eContainer instanceof Model)
		{
			packageDeclaration.name.packageName + "." + GeneratorConstants.SERVER_PACKAGE_POSTFIX
		}
		else
		{
			combinePackageName(getPackageName(packageDeclaration.eContainer), packageDeclaration.name.packageName)
		}
	}
	
	def gwtRemoteServicesApplicationContextFullQualifiedFileName(Model model) {
		return model.name.toFirstUpper + "GWTRemoteServices-gen.xml"
	}

	def serviceSpringNameApplicationContextFullQualifiedFileName(Model model) {
		return model.name.toFirstUpper + "SpringServices-gen.xml"
	}
	
	def serviceImplFullQualifiedName(Service service) {
		return getPackageName(service) + "." + service.serviceImplName;
	}
	
	def serviceImplName(Service service) {
		return service.name.toFirstUpper + "Impl";
	}

	def variableName(Service service) {
		return service.name.toFirstLower
	}

	def setterName(Service service) {
		return "set" + service.variableName.toFirstUpper
	}

	def restMapping(Service service) {
		return service.name.toLowerCase
	}

	def restMapping(ServiceMethod serviceMethod) {
		return serviceMethod.name.toLowerCase
	}

	// rest controller
	def restControllerName(Service service) {
		return service.name.toFirstUpper + "RestController"
	}

	def restControllerFullQualifiedName(Service service) {
		return getPackageName(service) + "." + restControllerName(service);
	}

	def restControllerFullQualifiedFileName(Service service) {
		return restControllerFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}

}
