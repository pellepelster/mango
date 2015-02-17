package io.pelle.mango.server.api.webhook;

import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class WebhookServiceImpl implements IWebhookService {

	@Autowired
	private EntityWebhookRegistry entityWebhookRegistry;

	@Override
	public List<WebhookVO> getWebhooks() {
		return null;
	}

	@Override
	public Result<WebhookVO> addWebhook(WebhookVO webhook) {
		return entityWebhookRegistry.registerEntityWebHook(webhook);
	}

}
