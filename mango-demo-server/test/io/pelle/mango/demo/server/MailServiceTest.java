package io.pelle.mango.demo.server;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.mail.MailService;

public class MailServiceTest extends BaseDemoTest {

	@Autowired
	private MailService mailService;

	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

	@Test
	public void testSendMail() {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Steve");
		model.put("link", "http://www.example.org");
		model.put("username", "steve23");

		mailService.sendMail("steve@yahoo.com", "classpath://mailtemplate.vm", model);

		greenMail.waitForIncomingEmail(1);

	}

}
