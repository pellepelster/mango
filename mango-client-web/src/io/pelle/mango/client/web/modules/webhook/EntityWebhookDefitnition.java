package io.pelle.mango.client.web.modules.webhook;

import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.client.base.vo.IEntityDescriptor;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("serial")
public class EntityWebhookDefitnition extends WebhookDefinition {

	public enum EntityWebHookEvents {
		ON_CREATE, ON_SAVE
	}

	public static EntityWebhookDefitnition INSTANCE = new EntityWebhookDefitnition();

	public static String ENTITY_CLASS_NAME_KEY = "class";

	private EntityWebhookDefitnition() {
		super("entity", "Entity Webhook", Arrays.asList(new String[] { EntityWebHookEvents.ON_CREATE.toString(), EntityWebHookEvents.ON_SAVE.toString() }));
	}

	public Collection<IEntityDescriptor<?>> getEntityDescriptors() {
		return VOMetaModelProvider.getEntityDescriptors();
	}
}
