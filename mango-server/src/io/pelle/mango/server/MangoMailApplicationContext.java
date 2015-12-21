package io.pelle.mango.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.mail.MailService;

@Configuration
public class MangoMailApplicationContext {

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


}
