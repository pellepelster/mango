package io.pelle.mango.server.mail;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Value("mail.sender.from")
	private String mailFrom;

	private static Logger LOG = Logger.getLogger(MailService.class.getName());

	public void sendMail(final String mailTo, final String velocityTemplateLocation, final Map<String, Object> velocityTemplateModel) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(mailTo);
				message.setFrom(mailFrom);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateLocation, StandardCharsets.UTF_8.name(), velocityTemplateModel);
				message.setText(text, true);
			}
		};

		try {
			this.mailSender.send(preparator);
		} catch (MailException e) {
			LOG.error(e);
		}
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
}
