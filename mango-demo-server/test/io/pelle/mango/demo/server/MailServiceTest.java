package io.pelle.mango.demo.server;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.mail.MailService;

public class MailServiceTest extends BaseDemoTest {

	@Autowired
	private MailService mailService;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

	@Test
	public void testStandardMail() throws IOException, MessagingException {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Steve");
		model.put("link", "http://www.example.org");
		model.put("username", "steve23");

		mailService.sendMail("steve@yahoo.com", "subject1", "/templates/mailtemplate.ftl", model);
		greenMail.waitForIncomingEmail(1);

		URL url = resourceLoader.getResource("classpath:mailtemplate.ftl.expected").getURL();
		String expectedMailContent = Resources.toString(url, Charsets.UTF_8);

		MimeMessage message = greenMail.getReceivedMessages()[0];
		String mailContent = message.getContent().toString();

		Assert.assertTrue(expectedMailContent.replaceAll("\\s+", "").equalsIgnoreCase(mailContent.replaceAll("\\s+", "")));
		Assert.assertEquals("subject1", message.getSubject());
	}

	@Test
	public void testMailWithExtraHeader() throws IOException, MessagingException {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Steve");
		model.put("link", "http://www.example.org");
		model.put("username", "steve23");

		Map<String, String> headers = new HashMap<>();
		headers.put("X-HEADER-1", "value1");

		mailService.sendMail("steve@yahoo.com", "subject2", "/templates/mailtemplate.ftl", model, headers);

		greenMail.waitForIncomingEmail(1);
		MimeMessage message = greenMail.getReceivedMessages()[0];

		Assert.assertTrue(Iterables.tryFind(Collections.<Header> list(message.getAllHeaders()), new Predicate<Header>() {
			@Override
			public boolean apply(Header input) {
				return input.getName().equals("X-HEADER-1") && input.getValue().equals("value1");
			}
		}).isPresent());

	}

}
