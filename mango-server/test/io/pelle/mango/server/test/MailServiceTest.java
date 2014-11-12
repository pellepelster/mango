package io.pelle.mango.server.test;

import io.pelle.mango.server.mail.MailService;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceTest {

	@Autowired
	private MailService mailService;

	@Test
	@Ignore
	public void testSendMail() {

		Map<String, Object> model = new HashMap<String, Object>();

	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

}
