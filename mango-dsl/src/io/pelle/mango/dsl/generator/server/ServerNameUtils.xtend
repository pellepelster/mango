package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.PackageDeclaration
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod

class ServerNameUtils extends NameUtils {

	//-------------------------------------------------------------------------
	// entity
	//-------------------------------------------------------------------------
	def entityName(Entity entity) {
		return entity.name.toFirstUpper;
	}

	def entityFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + entityName(entity);
	}

	def entityFullQualifiedFileName(Entity entity) {
		return entityFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
	}
	
	override dispatch String getPackageName(PackageDeclaration packageDeclaration) {
		
		if (packageDeclaration.eContainer instanceof Model)
		{
			packageDeclaration.packageName.packageName + "." + GeneratorConstants.SERVER_PACKAGE_POSTFIX
		}
		else
		{
			combinePackageName(getPackageName(packageDeclaration.eContainer), packageDeclaration.packageName.packageName)
		}
		
	}

	def restRemoteServicesApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "RestRemoteServices-gen.xml"
	}
	
	def gwtRemoteServicesApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "GWTRemoteServices-gen.xml"
	}

	def serviceSpringNameApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "SpringServices-gen.xml"
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

	// rest controller request vo
	def restControllerRequestVOName(Service service, ServiceMethod method) {
		return service.restControllerName + method.name.toFirstUpper + "RequestVO"
	}

	def restControllerRequestVOFullQualifiedName(Service service, ServiceMethod method) {
		return service.getPackageName + "." + restControllerRequestVOName(service, method);
	}

	def restControllerRequestVOFullQualifiedFileName(Service service, ServiceMethod method) {
		return restControllerRequestVOFullQualifiedName(service, method).replaceAll("\\.", "/")  + ".java";
	}

}
