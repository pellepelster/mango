package io.pelle.mango.dsl.generator.server

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.xml.XmlNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model

class SpringGenerator {
	
	@Inject 
	extension ServerNameUtils

	@Inject 
	extension XmlNameUtils

	def compileSpringDBApplicationContext(Model model) '''
		<?xml version="1.0" encoding="UTF-8"?>
		
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xmlns:jee="http://www.springframework.org/schema/jee"
			xmlns:context="http://www.springframework.org/schema/context"
			xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		           http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
		           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
		
			<bean id="persistenceUnitManager" class="org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager">
				<property name="persistenceXmlLocations">
		    		<list>
		      			<value>classpath*:META-INF/persistence.xml</value>
		    		</list>
		  		</property>
				<property name="persistenceUnitPostProcessors">
					<bean class="io.pelle.mango.db.util.MergingPersistenceUnitPostProcessor">
						<property name="targetPersistenceUnitName" value="«persistenceUnitName(model)»" />
					</bean>
				</property>
				<property name="defaultDataSource" ref="dataSource"/>
			</bean>
		
		  	<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/«jndiName(model)»"/>
		
			<bean class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" id="entityManagerFactory">
		
				<property name="dataSource" ref="dataSource" />
				<property name="jpaVendorAdapter" ref="jpaAdapter" />
				<property name="persistenceUnitName" value="«persistenceUnitName(model)»" />
				<property name="persistenceUnitManager" ref="persistenceUnitManager" />
		
				<property name="jpaProperties">
					<props>
						<prop key="hibernate.hbm2ddl.auto">create</prop>
						<prop key="hibernate.query.substitutions">true 1, false 0</prop>
						<prop key="hibernate.connection.autocommit">true</prop>
						<prop key="hibernate.FlushMode">AUTO</prop>
						<prop key="hibernate.show_sql">true</prop>
						<prop key="hibernate.format_sql">true</prop>
					</props>
				</property>
			</bean>
		</beans>
	'''

	def compileBaseApplicationContext(Model model) '''
		<?xml version="1.0" encoding="UTF-8"?>
		
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
			xmlns:context="http://www.springframework.org/schema/context"
			xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

			<context:annotation-config/>
			
			<bean class="«model.xmlVOMapperFullQualifiedName»" />

			<bean class="«model.voMapperFullQualifiedName»" />
		
		</beans>
	'''


	def compilePersistenceXml(Model model) '''
		<persistence version="1.0"
			xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
			http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd">
		
				<persistence-unit name="«persistenceUnitName(model)»">
				«FOR entity:model.eAllContents.toIterable.filter(Entity)»
				<class>«entityFullQualifiedName(entity)»</class>
				«ENDFOR»
				<exclude-unlisted-classes>false</exclude-unlisted-classes>
		
			</persistence-unit>
		</persistence>
	'''

}
