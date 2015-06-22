package io.pelle.mango.gradle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Type;
import org.hibernate.tool.hbm2ddl.Target;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;


public class HibernateGenerator  {

	public interface LogCallback {
		void log(String message);
	}
	
	private Configuration configuration;

	private File schemaDir = new File("schemas");

	private LogCallback logCallback;
	
	public HibernateGenerator(LogCallback logCallback) {

		this.logCallback = logCallback;
		this.configuration = new Configuration();
		this.configuration.setProperty("hibernate.hbm2ddl.auto", "create");

		for (Class<?> jpaClass : getJPAClasses()) {
			configuration.addAnnotatedClass(jpaClass);
		}
	}

	private void generate(Class<? extends Dialect> dialectClass) {

		configuration.setProperty("hibernate.dialect", dialectClass.getName());

		SchemaExport export = new SchemaExport(configuration);
		export.setDelimiter(";");

		File dialectDir = new File(schemaDir.getPath(), dialectClass.getSimpleName());
		dialectDir.mkdirs();

		export.setOutputFile(dialectDir.getPath() + "/create.sql");
		export.execute(Target.SCRIPT, Type.CREATE);

		export.setOutputFile(dialectDir.getPath() + "/drop.sql");
		export.execute(Target.SCRIPT, Type.DROP);

		export.setOutputFile(dialectDir.getPath() + "/drop_and_create.sql");
		export.execute(Target.SCRIPT, Type.BOTH);

	}

	public void generate() {

		List<Class<? extends Dialect>> dialectsClasses = getDialectClasses();

		for (Class<? extends Dialect> dialectClass : dialectsClasses) {
			generate(dialectClass);
		}

	}

	private List<Class<?>> getJPAClasses() {

		String packageName = "*";
		
		logCallback.log(String.format("searching package '%s' for jpa entities...", packageName));

		List<Class<?>> entities = new ArrayList<Class<?>>();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));

		for (BeanDefinition beandefinition : scanner.findCandidateComponents("*")) {

			try {
				entities.add(Class.forName(beandefinition.getBeanClassName()));
				logCallback.log(String.format("found entity '%s'", beandefinition.getBeanClassName()));
			} catch (ClassNotFoundException e) {
				logCallback.log(e.getMessage());
			}
		}

		logCallback.log(String.format("...done, found %d entities", entities.size()));

		return entities;
	}

	@SuppressWarnings("unchecked")
	private List<Class<? extends Dialect>> getDialectClasses() {

		String packageName = "org.hibernate.dialect";
		
		logCallback.log(String.format("searching package '%s' for hibernate dialects...", packageName));

		List<Class<? extends Dialect>> dialects = new ArrayList<Class<? extends Dialect>>();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new BaseClassTypeFilter(org.hibernate.dialect.Dialect.class));

		for (BeanDefinition beandefinition : scanner.findCandidateComponents(packageName)) {
			try {
				dialects.add((Class<? extends Dialect>) Class.forName(beandefinition.getBeanClassName()));
				logCallback.log(String.format("found jpa dialect '%s'", beandefinition.getBeanClassName()));
			} catch (ClassNotFoundException e) {
				logCallback.log(e.getMessage());
			}
		}

		logCallback.log(String.format("...done, found %d dialects", dialects.size()));

		return dialects;
	}

}
