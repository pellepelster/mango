package io.pelle.mango.server.mail;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.ConfigurationParameters;

public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IPropertyService propertyService;

	@Autowired
	private VelocityEngine velocityEngine;

	private static Logger LOG = Logger.getLogger(MailService.class.getName());

	public void sendMail(final String mailTo, final String subject, final String velocityTemplateLocation, final Map<String, Object> velocityTemplateModel) {
		sendMail(mailTo, subject, velocityTemplateLocation, velocityTemplateModel, Collections.<String, String> emptyMap());
	}

	public void sendMail(final String mailTo, final String subject, final String velocityTemplateLocation) {
		sendMail(mailTo, subject, velocityTemplateLocation, Collections.<String, Object> emptyMap(), Collections.<String, String> emptyMap());
	}

	public void sendMail(final String mailTo, final String subject, final String velocityTemplateLocation, final Map<String, Object> velocityTemplateModel, final Map<String, String> headers) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				message.setTo(mailTo);
				message.setFrom(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_FROM));

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateLocation, StandardCharsets.UTF_8.name(), velocityTemplateModel);

				message.setText(text, true);
				message.setSubject(subject);

				for (Map.Entry<String, String> header : headers.entrySet()) {
					mimeMessage.setHeader(header.getKey(), header.getValue());
				}
			}
		};

		try {
			this.mailSender.send(preparator);
		} catch (MailException e) {
			LOG.error(e);
		}
	}

}
