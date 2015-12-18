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

	//-------------------------------------------------------------------------
	// entity DAO
	//-------------------------------------------------------------------------
	def entityDAOName(Entity entity) {
		return "Base" + entity.name.toFirstUpper + "EntityDAO";
	}

	def entityDAOSpringId(Entity entity) {
		return entity.entityDAOName.toFirstLower;
	}

	def entityDAOFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + entityDAOName(entity);
	}

	//-------------------------------------------------------------------------
	// entity DAO interface
	//-------------------------------------------------------------------------
	def entityDAOInterfaceName(Entity entity) {
		return "I" + entity.name.toFirstUpper + "EntityDAO";
	}

	def entityDAOInterfaceFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + entityDAOInterfaceName(entity);
	}

	//-------------------------------------------------------------------------
	// VO DAO
	//-------------------------------------------------------------------------
	def voDAOName(Entity entity) {
		return "Base" + entity.name.toFirstUpper + "VODAO";
	}

	def voDAOSpringId(Entity entity) {
		return entity.voDAOName.toFirstLower;
	}

	def voDAOFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + voDAOName(entity);
	}

	//-------------------------------------------------------------------------
	// vo DAO interface
	//-------------------------------------------------------------------------
	def voDAOInterfaceName(Entity entity) {
		return "I" + entity.name.toFirstUpper + "VODAO";
	}

	def voDAOInterfaceFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + voDAOInterfaceName(entity);
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

	def restMapping(Service service, ServiceMethod serviceMethod) {
		
		if (service.remoteMethods.filter[e|e.name.equals(serviceMethod.name)].size > 1) {
			return serviceMethod.name.toLowerCase + service.remoteMethods.indexOf(serviceMethod)
		} else {
			return serviceMethod.name.toLowerCase
		}
	}

	// rest controller
	def restControllerBeanName(Service service) {
		return service.restControllerName.toFirstLower
	}
	
	def restControllerName(Service service) {
		return service.name.toFirstUpper + "RestController"
	}

	def restControllerFullQualifiedName(Service service) {
		return getPackageName(service) + "." + restControllerName(service);
	}

	// rest controller request vo
	def restControllerRequestVOName(Service service, ServiceMethod method) {
		return service.restControllerName + uniqueMethodName(service, method).toFirstUpper + "Request"
	}

	def uniqueMethodName(Service service, ServiceMethod method) {
		if (service.remoteMethods.filter[e|e.name.equals(method.name)].size > 1) {
			return method.name + service.remoteMethods.indexOf(method)
		} else {
			return method.name
		}
	}

	def restControllerRequestVOFullQualifiedName(Service service, ServiceMethod method) {
		return service.getPackageName + "." + restControllerRequestVOName(service, method);
	}

}
