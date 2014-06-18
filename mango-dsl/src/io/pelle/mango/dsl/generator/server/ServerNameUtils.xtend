package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.PackageDeclaration
import io.pelle.mango.dsl.mango.Service

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

}
