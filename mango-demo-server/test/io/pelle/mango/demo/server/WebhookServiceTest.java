package io.pelle.mango.demo.server;

import static org.junit.Assert.assertFalse;
import io.pelle.mango.client.api.webhook.IWebhookService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class WebhookServiceTest extends BaseDemoTest {

	@Autowired
	private IWebhookService webhookService;

	@Test
	public void testGetWebhookDefinitions() {

		assertFalse(webhookService.getWebhookDefinitions().isEmpty());

	}

}