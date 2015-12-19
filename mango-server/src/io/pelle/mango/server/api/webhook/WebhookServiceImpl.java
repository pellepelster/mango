package io.pelle.mango.server.api.webhook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.pelle.mango.client.api.webhook.IWebhookService;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;

@Transactional
public class WebhookServiceImpl implements IWebhookService {

	@Autowired
	private EntityWebhookRegistry entityWebhookRegistry;

	@Override
	public List<WebhookVO> getWebhooks() {
		return entityWebhookRegistry.getWebhooks();
	}

	@Override
	public Result<WebhookVO> addWebhook(WebhookVO webhook) {
		return entityWebhookRegistry.addEntityWebHook(webhook);
	}

	@Override
	public Boolean removeWebhook(WebhookVO webhook) {
		return entityWebhookRegistry.deleteHook(webhook).getValue();
	}

}
