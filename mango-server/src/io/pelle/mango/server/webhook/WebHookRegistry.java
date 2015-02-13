package io.pelle.mango.server.webhook;

import io.pelle.mango.client.api.WebHookVO;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.dao.IDAOCallback;
import io.pelle.mango.server.log.IMangoLogger;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@RestController
public class WebHookRegistry implements InitializingBean {

	@Autowired
	private IMangoLogger mangoLogger;

	private final AsyncRestTemplate restTemplate = new AsyncRestTemplate();

	private LoadingCache<String, List<WebHookVO>> hooks = CacheBuilder.newBuilder().build(new CacheLoader<String, List<WebHookVO>>() {
		public List<WebHookVO> load(String key) {
			return getAllEntityWebHooks(key);
		}
	});

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	private String getKey(Class<?> entityClass) {
		return entityClass.getName();
	}

	public WebHookVO registerEntityWebHook(Class<? extends IBaseEntity> entityClass, WebHookVO webHook) {

		if (StringUtils.isEmpty(webHook.getName())) {
			throw new RuntimeException("no name provided");
		}

		if (StringUtils.isEmpty(webHook.getUrl())) {
			throw new RuntimeException("no URL provided");
		}

		hooks.invalidate(getKey(entityClass));

		return baseVODAO.create(webHook);

	}

	public List<WebHookVO> getAllEntityWebHooks(String key) {
		SelectQuery<WebHookVO> selectQuery = SelectQuery.selectFrom(WebHookVO.class).where(WebHookVO.TYPE.eq(key));
		return baseVODAO.filter(selectQuery);
	}

	public List<WebHookVO> getAllEntityWebHooks(Class<? extends IBaseEntity> entityClass) {
		return getAllEntityWebHooks(getKey(entityClass));
	}

	private String getReference(WebHookVO webHook) {
		return webHook.getName();
	}

	public <VOType extends IVOEntity> void callEntityWebHooks(List<WebHookVO> webHooks, VOType voEntity) {
		for (final WebHookVO webHook : webHooks) {

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
				callEntityWebHooks(hooks.getUnchecked(getKey(voEntity.getClass())), voEntity);
			}
		});
	}
}