package io.pelle.mango.db;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.FlushMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.db.util.MergingPersistenceUnitPostProcessor;
import io.pelle.mango.server.base.MangoConstants;

@Configuration
@ComponentScan({ "io.pelle.mango.db" })
@EnableTransactionManagement
@PropertySource(value = "classpath:/mango-gen.properties")
public class MangoDBApplicationContext {

	@Value("${hibernate.sql.show:hibernate.sql.show.default}")
	private String hibernateShowSQL;

	@Value("${hibernate.sql.format:hibernate.sql.format.default}")
	private String hibernateFormatSQL;

	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertyPlaceholderConfigurer();
	}

	@Autowired
	@Bean
	public DataSource dataSource(Environment environment) throws Exception {

		String applicationName = environment.getProperty(MangoConstants.APPLICATION_NAME_PROPERTY_KEY);

		if (JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable()) {
			JndiLocatorDelegate jndiLocatorDelegate = JndiLocatorDelegate.createDefaultResourceRefLocator();
			return (DataSource) jndiLocatorDelegate.lookup("java:comp/env/jdbc/" + applicationName);
		} else {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName(applicationName).build();
		}
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory emf, DataSource dataSource) {

		JpaTransactionManager result = new JpaTransactionManager();

		result.setDataSource(dataSource);
		result.setEntityManagerFactory(emf);

		return result;
	}

	@Bean
	public EntityVOMapper entityVOMapper() {
		return EntityVOMapper.getInstance();
	}

	@Bean
	public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		return sessionFactory;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaAdapter() {

		HibernateJpaVendorAdapter result = new HibernateJpaVendorAdapter();

		return result;
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, PersistenceUnitManager persistenceUnitManager,
			Environment environment) {

		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		String applicationName = environment.getProperty(MangoConstants.APPLICATION_NAME_PROPERTY_KEY);

		result.setDataSource(dataSource);
		result.setJpaVendorAdapter(jpaVendorAdapter);
		result.setPersistenceUnitName(applicationName);
		result.setPersistenceUnitManager(persistenceUnitManager);
		result.setPersistenceUnitManager(persistenceUnitManager);

		result.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
		result.getJpaPropertyMap().put("hibernate.query.substitutions", "true 1, false 0");
		result.getJpaPropertyMap().put("hibernate.connection.autocommit", Boolean.TRUE);
		result.getJpaPropertyMap().put("hibernate.FlushMode", FlushMode.AUTO);
		result.getJpaPropertyMap().put("hibernate.show_sql", hibernateShowSQL);
		result.getJpaPropertyMap().put("hibernate.format_sql", hibernateFormatSQL);

		return result;
	}

	@Bean
	@Autowired
	public MergingPersistenceUnitPostProcessor persistenceUnitPostProcessors(Environment environment) {

		String applicationName = environment.getProperty(MangoConstants.APPLICATION_NAME_PROPERTY_KEY);
		MergingPersistenceUnitPostProcessor result = new MergingPersistenceUnitPostProcessor();
		result.setTargetPersistenceUnitName(applicationName);

		return result;
	}

	@Bean
	@Autowired
	public DefaultPersistenceUnitManager persistenceUnitManager(DataSource dataSource, PersistenceUnitPostProcessor persistenceUnitPostProcessor) {

		DefaultPersistenceUnitManager result = new DefaultPersistenceUnitManager();

		result.setPersistenceXmlLocation("classpath*:META-INF/persistence.xml");

		result.setPersistenceUnitPostProcessors(persistenceUnitPostProcessor);
		result.setDefaultDataSource(dataSource);

		return result;
	}

}
