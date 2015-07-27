package io.pelle.mango.demo.server.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.server.api.webhook.Webhook;

@WebAppConfiguration
@Ignore
public class WebhookServiceTest extends BaseDemoTest {

	@Autowired
	private IWebhookService webhookService;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testAddEmptyWebhookDefinition() {
		baseEntityService.deleteAll(WebhookVO.class.getName());

		WebhookVO webhook = new WebhookVO();

		Result<WebhookVO> result = webhookService.addWebhook(webhook);
		assertFalse(result.isOk());
	}

	@Test
	public void testDeleteWebhookDefinition() {

		baseEntityService.deleteAll(WebhookVO.class.getName());

		WebhookVO webhook = EntityWebhookDefitnition.INSTANCE.createNewWebHook(Webhook.class);
		webhook.setName("abc");
		webhook.setUrl("def");

		Result<WebhookVO> result = webhookService.addWebhook(webhook);
		assertTrue(result.isOk());

		assertFalse(webhookService.getWebhooks().isEmpty());
		webhookService.removeWebhook(webhook);
		assertTrue(webhookService.getWebhooks().isEmpty());
	}

	@Test
	public void testAddWebhookDefinition() {

		baseEntityService.deleteAll(WebhookVO.class.getName());

		WebhookVO webhook = EntityWebhookDefitnition.INSTANCE.createNewWebHook(Webhook.class);
		webhook.setName("abc");
		webhook.setUrl("def");

		Result<WebhookVO> result = webhookService.addWebhook(webhook);
		assertTrue(result.isOk());
		assertFalse(result.getValue().isNew());
		assertEquals(Webhook.class.getName(), result.getValue().getType());

		List<WebhookVO> webhooks = webhookService.getWebhooks();

		assertEquals(1, webhooks.size());
		assertEquals("abc", webhooks.get(0).getName());
		assertEquals("def", webhooks.get(0).getUrl());
		assertFalse(webhooks.get(0).isNew());
	}
}