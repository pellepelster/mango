package io.pelle.mango.client.web.modules.webhook;

import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IEntityDescriptor;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("serial")
public class EntityWebhookDefitnition extends WebhookDefinition {

	public enum EntityWebHookEvents {
		ON_CREATE, ON_SAVE
	}

	public static EntityWebhookDefitnition INSTANCE = new EntityWebhookDefitnition();

	private EntityWebhookDefitnition() {
		super("entity", "Entity Webhook", Arrays.asList(new String[] { EntityWebHookEvents.ON_CREATE.toString(), EntityWebHookEvents.ON_SAVE.toString() }));
	}

	public Collection<IEntityDescriptor<?>> getEntityDescriptors() {
		return VOMetaModelProvider.getEntityDescriptors();
	}

	public WebhookVO createNewWebHook() {
		return createNewWebHook(null);
	}

	public WebhookVO createNewWebHook(Class<? extends IBaseEntity> entityClass) {
		
		WebhookVO webhook = new WebhookVO();
		
		if (entityClass != null) {
			webhook.setType(entityClass.getName());
		}
		webhook.setDefinitionId(EntityWebhookDefitnition.INSTANCE.getId());
		
		return webhook;
	}

}
