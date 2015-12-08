package io.pelle.mango.server;

import java.util.Properties;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.MangoDBApplicationContext;
import io.pelle.mango.server.mail.MailService;

@Configuration
public class MangoMailApplicationContext extends MangoDBApplicationContext {

	@Autowired
	private IPropertyService propertyService;

	@Bean
	public MailService mailService() {
		return new MailService();
	}

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_HOST));

		if (propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_PORT) != null) {
			javaMailSender.setPort(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_PORT));
		}

		if (propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_USERNAME) != null) {
			javaMailSender.setUsername(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_USERNAME));
		}

		if (propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_PASSWORD) != null) {
			javaMailSender.setPassword(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_PASSWORD));
		}

		return javaMailSender;
	}

	@Bean
	public VelocityEngineFactoryBean velocityEngine() {

		VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();

		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.setVelocityProperties(properties);
		return velocityEngine;
	}

}
