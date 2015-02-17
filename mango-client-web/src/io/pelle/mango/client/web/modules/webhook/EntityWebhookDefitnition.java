package io.pelle.mango.client.web.modules.webhook;

import io.pelle.mango.client.api.webhook.WebhookDefinition;

import java.util.Arrays;

@SuppressWarnings("serial")
public class EntityWebhookDefitnition extends WebhookDefinition {

	public enum EntityWebHookEvents {
		ON_CREATE, ON_SAVE
	}

	public static EntityWebhookDefitnition INSTANCE = new EntityWebhookDefitnition();

	public static String ENTITY_CLASS_NAME_KEY = "class";

	private EntityWebhookDefitnition() {
		super("Entity Webhook", Arrays.asList(new String[] { EntityWebHookEvents.ON_CREATE.toString(), EntityWebHookEvents.ON_SAVE.toString() }));
	}
}
