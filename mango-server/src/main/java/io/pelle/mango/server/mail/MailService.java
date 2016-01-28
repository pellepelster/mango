package io.pelle.mango.server.mail;

import java.util.Collections;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.server.ConfigurationParameters;

public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IPropertyService propertyService;

	private static Logger LOG = Logger.getLogger(MailService.class.getName());

	public void sendMail(final String mailTo, final String subject, final String templateLocation, final Map<String, Object> templateModel) {
		sendMail(mailTo, subject, templateLocation, templateModel, Collections.<String, String> emptyMap());
	}

	public void sendMail(final String mailTo, final String subject, final String templateLocation) {
		sendMail(mailTo, subject, templateLocation, Collections.<String, Object> emptyMap(), Collections.<String, String> emptyMap());
	}

	public void sendMail(final String mailTo, final String subject, final String templateName, final Map<String, Object> templateModel, final Map<String, String> headers) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				message.setTo(mailTo);
				message.setFrom(propertyService.getProperty(ConfigurationParameters.MAIL_SENDER_FROM));

				Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
				cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "/");
				Template template = cfg.getTemplate(templateName, "utf-8");

				String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);

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
