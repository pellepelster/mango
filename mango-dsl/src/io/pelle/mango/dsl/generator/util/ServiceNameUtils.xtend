package io.pelle.mango.dsl.generator.util

import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod

class ServiceNameUtils {
	
	//-------------------------------------------------------------------------
	// service 
	//-------------------------------------------------------------------------
	def serviceName(Service service) {
		return service.name.toFirstUpper;
	}

	def serviceAttributeName(Service service) {
		return service.name.toFirstLower;
	}
	
	def serviceSpringName(Service service) {
		return service.serviceName
	}

	def methodName(ServiceMethod serviceMethod) {
		return serviceMethod.name.toFirstLower;
	}
	

	//-------------------------------------------------------------------------
	// client configuration  
	//-------------------------------------------------------------------------
	def gwtClientconfigurationName(Model model) {
		return model.modelName.toFirstUpper + "ClientConfiguration";
	}

	def gwtClientconfigurationFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtClientconfigurationName(model);
	}

	def gwtClientconfigurationFullQualifiedNameFileName(Model model) {
		return gwtClientconfigurationFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// service interface  
	//-------------------------------------------------------------------------
	def serviceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper;
	}

	def serviceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + serviceInterfaceName(service);
	}

	def serviceInterfaceFullQualifiedFileName(Service service) {
		return serviceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// GWT service interface  
	//-------------------------------------------------------------------------
	def gwtServiceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper + "GWT";
	}

	def gwtServiceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtServiceInterfaceName(service);
	}

	def gwtServiceInterfaceFullQualifiedFileName(Service service) {
		return gwtServiceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// GWT Async interface  
	//-------------------------------------------------------------------------
	def gwtAsyncServiceInterfaceName(Service service) {
		return "I" + service.name.toFirstUpper + "GWTAsync";
	}

	def gwtAsyncServiceInterfaceFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtAsyncServiceInterfaceName(service);
	}

	def gwtAsyncServiceInterfaceFullQualifiedFileName(Service service) {
		return gwtAsyncServiceInterfaceFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// GWT Async interface  
	//-------------------------------------------------------------------------
	def gwtAsyncAdapterName(Service service) {
		return service.name.toFirstUpper + "GWTAsyncAdapter";
	}

	def gwtAsyncAdapterBeanName(Service service) {
		return service.gwtAsyncAdapterName.toFirstLower
	}

	def gwtAsyncAdapterFullQualifiedName(Service service) {
		return getPackageName(service) + "." + gwtAsyncAdapterName(service);
	}

	def gwtAsyncAdapterFullQualifiedFileName(Service service) {
		return gwtAsyncAdapterFullQualifiedName(service).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// GWT remote service locator 
	//-------------------------------------------------------------------------
	def gwtRemoteServiceLocatorName(Model model) {
		return model.modelName.toFirstUpper + "GwtRemoteServiceLocator";
	}

	def gwtRemoteServiceLocatorFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtRemoteServiceLocatorName(model);
	}

	def gwtRemoteServiceLocatorFullQualifiedFileName(Model model) {
		return gwtRemoteServiceLocatorFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// GWT async adapter remote service locator 
	//-------------------------------------------------------------------------
	def gwtAsyncAdapterRemoteServiceLocatorName(Model model) {
		return model.modelName.toFirstUpper + "GwtAsyncAdapterRemoteServiceLocator";
	}

	def gwtAsyncAdapterRemoteServiceLocatorFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtAsyncAdapterRemoteServiceLocatorName(model);
	}

	def gwtAsyncAdapterRemoteServiceLocatorFullQualifiedFileName(Model model) {
		return gwtAsyncAdapterRemoteServiceLocatorFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	 
	//-------------------------------------------------------------------------
	// GWT remote service locator interface 
	//-------------------------------------------------------------------------
	def gwtRemoteServiceLocatorInterfaceName(Model model) {
		return "I" + model.modelName.toFirstUpper + "GwtRemoteServiceLocator";
	}

	def gwtRemoteServiceLocatorInterfaceFullQualifiedName(Model model) {
		return model.modelPackageName + "." + gwtRemoteServiceLocatorInterfaceName(model);
	}

	def gwtRemoteServiceLocatorInterfaceFullQualifiedFileName(Model model) {
		return gwtRemoteServiceLocatorInterfaceFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}

	//-------------------------------------------------------------------------
	// remote service locator 
	//-------------------------------------------------------------------------
	def remoteServiceLocatorName(Model model) {
		return model.modelName.toFirstUpper + "RemoteServiceLocator";
	}

	def remoteServiceLocatorFullQualifiedName(Model model) {
		return model.modelPackageName + "." + remoteServiceLocatorName(model);
	}

	def remoteServiceLocatorFullQualifiedFileName(Model model) {
		return remoteServiceLocatorFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	 
	//-------------------------------------------------------------------------
	// GWT remote service locator interface 
	//-------------------------------------------------------------------------
	def remoteServiceLocatorInterfaceName(Model model) {
		return "I" + model.modelName.toFirstUpper + "RemoteServiceLocator";
	}

	def remoteServiceLocatorInterfaceFullQualifiedName(Model model) {
		return model.modelPackageName + "." + remoteServiceLocatorInterfaceName(model);
	}

	def remoteServiceLocatorInterfaceFullQualifiedFileName(Model model) {
		return remoteServiceLocatorInterfaceFullQualifiedName(model).replaceAll("\\.", "/")  + ".java";
	}
	
}