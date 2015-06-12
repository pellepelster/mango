package io.pelle.mango.server.api.webhook;

import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition.EntityWebHookEvents;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.dao.IDAOCallback;
import io.pelle.mango.server.api.entity.EntityWebHookCall;
import io.pelle.mango.server.log.IMangoLogger;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class EntityWebhookRegistry implements InitializingBean {

	@Autowired
	private IMangoLogger mangoLogger;

	private final AsyncRestTemplate restTemplate = new AsyncRestTemplate();

	private LoadingCache<String, List<WebhookVO>> hooks = CacheBuilder.newBuilder().build(new CacheLoader<String, List<WebhookVO>>() {
		public List<WebhookVO> load(String key) {
			return getAllEntityWebhooks(key);
		}
	});

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	private String getCacheKey(Class<?> entityClass) {
		return entityClass.getName();
	}

	private String getLogReference(WebhookVO webHook) {
		return webHook.getName();
	}

	public Result<Boolean> deleteHook(WebhookVO webhook) {
		return deleteHook(webhook.getType(), webhook.getName());
	}

	public Result<Boolean> deleteHook(String entityClassName, String hookName) {

		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class).where(WebhookVO.TYPE.eq(entityClassName).and(WebhookVO.NAME.eq(hookName)));

		Optional<WebhookVO> webHook = baseVODAO.read(selectQuery);

		if (webHook.isPresent()) {
			baseVODAO.delete(webHook.get());
			hooks.invalidate(entityClassName);
		} else {
			throw new WebhookException(String.format("webhook with name '%s' does not exist", hookName));
		}

		return new Result<Boolean>();
	}

	public Result<WebhookVO> validateRegisterRequest(WebhookVO webhook) {

		Result<WebhookVO> result = new Result<WebhookVO>();
		result.setValue(webhook);

		if (StringUtils.isEmpty(webhook.getType())) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no entity class name provided"));
			return result;
		}

		if (StringUtils.isEmpty(webhook.getName())) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no name provided"));
		}

		if (StringUtils.isEmpty(webhook.getUrl())) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no URL provided"));
		}

		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class).where(WebhookVO.TYPE.eq(webhook.getType()).and(WebhookVO.NAME.eq(webhook.getName())));

		if (baseVODAO.read(selectQuery).isPresent()) {
			throw new WebhookException(String.format("webhook with name '%s' already registered", webhook.getName()));
		}

		return result;
	}

	public Result<WebhookVO> addEntityWebHook(WebhookVO webhook) {

		Result<WebhookVO> result = validateRegisterRequest(webhook);

		if (result.isOk()) {
			result.setValue(baseVODAO.create(result.getValue()));
			hooks.invalidate(webhook.getType());
		}

		return result;

	}

	public List<WebhookVO> getAllEntityWebhooks(String key) {
		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class).where(WebhookVO.TYPE.eq(key));
		return baseVODAO.filter(selectQuery);
	}

	public List<WebhookVO> getAllEntityWebHooks(Class<? extends IBaseEntity> entityClass) {
		return getAllEntityWebhooks(getCacheKey(entityClass));
	}

	public <VOType extends IVOEntity> void callEntityWebHooks(EntityWebHookEvents event, List<WebhookVO> webHooks, VOType voEntity) {

		for (final WebhookVO webHook : webHooks) {

			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new EntityWebHookCall(event.toString(), voEntity));

			ListenableFuture<ResponseEntity<Void>> result = null;

			try {
				result = restTemplate.postForEntity(webHook.getUrl(), httpEntity, Void.class);
			} catch (Exception e) {
				mangoLogger.error(String.format("execution of webhook '%s' failed: %s", webHook.getName(), e.getMessage()), getLogReference(webHook));
				return;
			}

			result.addCallback(new ListenableFutureCallback<ResponseEntity<Void>>() {

				@Override
				public void onSuccess(ResponseEntity<Void> result) {
				}

				@Override
				public void onFailure(Throwable ex) {
					mangoLogger.error(String.format("execution of webhook '%s' failed: %s", webHook.getName(), ex.getMessage()), getLogReference(webHook));
				}
			});

		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		baseEntityDAO.registerCallback(new IDAOCallback() {

			@Override
			public void onUpdate(IVOEntity voEntity) {
			}

			@Override
			public void onDeleteAll(Class voEntityClass) {
			}

			@Override
			public void onDelete(IVOEntity voEntity) {
				if (voEntity.getClass().equals(Webhook.class)) {
					hooks.invalidateAll();
				}
			}

			@Override
			public void onCreate(IVOEntity voEntity) {
				callEntityWebHooks(EntityWebHookEvents.ON_CREATE, hooks.getUnchecked(getCacheKey(voEntity.getClass())), voEntity);
			}
		});
	}

	public List<WebhookVO> getWebhooks() {
		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class);
		return baseVODAO.filter(selectQuery);
	}
}