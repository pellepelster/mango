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

	def entityDAOFullQualifiedFileName(Entity entity) {
		return entityDAOFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
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

	def entityDAOInterfaceFullQualifiedFileName(Entity entity) {
		return entityDAOInterfaceFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
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

	def voDAOFullQualifiedFileName(Entity entity) {
		return voDAOFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
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

	def voDAOInterfaceFullQualifiedFileName(Entity entity) {
		return voDAOInterfaceFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
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

	def serviceSpringServicesApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "SpringServices-gen.xml"
	}

	def serviceSpringServicesInvokerApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "SpringInvokerServices-gen.xml"
	}

	def serviceSpringServicesInvokerClientApplicationContextFullQualifiedFileName(Model model) {
		return model.modelName.toFirstUpper + "SpringInvokerClientServices-gen.xml"
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

	def restControllerFullQualifiedFileName(Service service) {
		return restControllerFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
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

	def restControllerRequestVOFullQualifiedFileName(Service service, ServiceMethod method) {
		return restControllerRequestVOFullQualifiedName(service, method).replaceAll("\\.", "/")  + ".java";
	}

}
