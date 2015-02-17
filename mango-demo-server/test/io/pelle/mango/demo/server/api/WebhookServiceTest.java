package io.pelle.mango.demo.server.api;

import static org.junit.Assert.assertFalse;
import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.server.BaseDemoTest;

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
}