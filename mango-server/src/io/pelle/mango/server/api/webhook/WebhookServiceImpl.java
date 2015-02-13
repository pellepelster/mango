package io.pelle.mango.server.api.webhook;

import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.api.webhook.WebhookVO;

import java.util.Arrays;
import java.util.List;

public class WebhookServiceImpl implements IWebhookService {

	private List<WebhookDefinition> WEBHOOK_DEFINITIONS = Arrays.asList(new WebhookDefinition[] { EntityWebhookRegistry.ENTITY_WEBHOOK });

	@Override
	public List<WebhookDefinition> getWebhookDefinitions() {
		return WEBHOOK_DEFINITIONS;
	}

	@Override
	public List<WebhookVO> getWebhooks() {
		return null;
	}

}
