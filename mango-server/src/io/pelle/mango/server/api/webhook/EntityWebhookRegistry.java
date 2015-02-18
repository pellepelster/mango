package io.pelle.mango.server.api.webhook;

import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.dao.IDAOCallback;
import io.pelle.mango.db.util.EntityVOMapper;
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

	private String getReference(WebhookVO webHook) {
		return webHook.getName();
	}

	public void deleteHook(Class<? extends IBaseEntity> entityClass, String hookName) {

		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class).where(WebhookVO.TYPE.eq(entityClass.getName()).and(WebhookVO.NAME.eq(hookName)));

		Optional<WebhookVO> webHook = baseVODAO.read(selectQuery);

		if (webHook.isPresent()) {
			baseVODAO.delete(webHook.get());
			hooks.invalidate(getCacheKey(entityClass));
		} else {
			throw new WebhookException(String.format("webhook with name '%s' does not exist", hookName));
		}
	}

	@SuppressWarnings("unchecked")
	private Class<? extends IBaseEntity> getEntityClass(WebhookVO webhook) {

		String entityClassName = webhook.getConfig().get(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY);

		if (entityClassName == null || StringUtils.isEmpty(entityClassName.toString())) {
			return null;
		} else {
			try {
				return EntityVOMapper.getInstance().getEntityClass(Class.forName(entityClassName.toString()));
			} catch (ClassNotFoundException e) {
				return null;
			}
		}
	}

	public Result<WebhookVO> validateRegisterRequest(WebhookVO webhook) {

		Result<WebhookVO> result = new Result<WebhookVO>();
		result.setVO(webhook);

		Class<? extends IBaseEntity> entityClass = getEntityClass(webhook);
		if (entityClass == null) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no entity class name provided"));
			return result;
		}

		if (StringUtils.isEmpty(webhook.getName())) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no name provided"));
		}

		if (StringUtils.isEmpty(webhook.getUrl())) {
			result.getValidationMessages().add(new ValidationMessage(IMessage.SEVERITY.ERROR, EntityWebhookRegistry.class.getName(), "no URL provided"));
		}

		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class).where(WebhookVO.TYPE.eq(getCacheKey(entityClass)).and(WebhookVO.NAME.eq(webhook.getName())));

		if (baseVODAO.read(selectQuery).isPresent()) {
			throw new WebhookException(String.format("webhook with name '%s' already registered", webhook.getName()));
		}

		return result;
	}

	public Result<WebhookVO> registerEntityWebHook(WebhookVO webhook) {

		Result<WebhookVO> result = validateRegisterRequest(webhook);

		if (result.isOk()) {
			result.setVO(baseVODAO.create(result.getVO()));
			hooks.invalidate(getCacheKey(getEntityClass(webhook)));
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

	public <VOType extends IVOEntity> void callEntityWebHooks(List<WebhookVO> webHooks, VOType voEntity) {
		for (final WebhookVO webHook : webHooks) {

			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new EntityWebHookCall(voEntity));
			ListenableFuture<ResponseEntity<Void>> result = restTemplate.postForEntity(webHook.getUrl(), httpEntity, Void.class);

			result.addCallback(new ListenableFutureCallback<ResponseEntity<Void>>() {

				@Override
				public void onSuccess(ResponseEntity<Void> result) {
				}

				@Override
				public void onFailure(Throwable ex) {
					mangoLogger.error(String.format("execution of webhook '%s' failed: %s", webHook.getName(), ex.getMessage()), getReference(webHook));
				}
			});

		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		baseEntityDAO.registerCallback(new IDAOCallback() {

			@Override
			public <VOType extends IVOEntity> void onUpdate(VOType voEntity) {
			}

			@Override
			public void onDeleteAll(Class<? extends IVOEntity> voEntityClass) {
			}

			@Override
			public <VOType extends IVOEntity> void onDelete(VOType voEntity) {
			}

			@Override
			public <VOType extends IVOEntity> void onCreate(VOType voEntity) {
				callEntityWebHooks(hooks.getUnchecked(getCacheKey(voEntity.getClass())), voEntity);
			}
		});
	}

	public List<WebhookVO> getWebhooks() {
		SelectQuery<WebhookVO> selectQuery = SelectQuery.selectFrom(WebhookVO.class);
		return baseVODAO.filter(selectQuery);
	}
}