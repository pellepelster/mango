/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.Service

class GWTSpringServices {

	@Inject
	extension ServerNameUtils
	
	def gwtRemoteServicesApplicationContext(Model model) '''
	<?xml version="1.0" encoding="UTF-8"?>
	
	<beans xmlns="http://www.springframework.org/schema/beans"
	        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	        xmlns:context="http://www.springframework.org/schema/context"
	        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
	
		<bean id="«model.modelName.toFirstLower»GWTUrlMapping" class="org.gwtwidgets.server.spring.GWTHandler">
			<property name="order" value="10"/>
			<property name="serviceExporterFactory">
				<ref bean="rpcServiceExporterFactory" />
			</property>
			<property name="mappings">
				<map>
					«FOR service: model.eAllContents.toIterable.filter(Service)»
					<entry key="/rpc/«service.serviceSpringName»" value-ref="«service.serviceSpringName»" />
					«ENDFOR»
				</map>
			</property>
		</bean>

	</beans>
	'''

	def restRemoteServicesApplicationContext(Model model) '''
	<?xml version="1.0" encoding="UTF-8"?>
	
	<beans xmlns="http://www.springframework.org/schema/beans"
	        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	        xmlns:context="http://www.springframework.org/schema/context"
	        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
	
			«FOR service: model.eAllContents.toIterable.filter(Service)»
			<bean id="«service.restControllerBeanName»" class="«service.restControllerFullQualifiedName»" />
			«ENDFOR»

	</beans>
	'''
	
}