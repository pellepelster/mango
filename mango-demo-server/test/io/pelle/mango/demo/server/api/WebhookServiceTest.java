package io.pelle.mango.demo.server.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.server.api.webhook.Webhook;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
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
		assertFalse(result.getValidationMessages().isEmpty());
	}

	@Test
	public void testAddWebhookDefinition() {
		baseEntityService.deleteAll(WebhookVO.class.getName());

		WebhookVO webhook = new WebhookVO();
		webhook.setName("abc");
		webhook.setUrl("def");
		webhook.getConfig().put(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY, Webhook.class.getName());
		webhook.setType(EntityWebhookDefitnition.INSTANCE.getId());

		Result<WebhookVO> result = webhookService.addWebhook(webhook);
		assertTrue(result.getValidationMessages().isEmpty());
		assertEquals(Webhook.class.getName(), result.getVO().getConfig().get(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY));
	}

}