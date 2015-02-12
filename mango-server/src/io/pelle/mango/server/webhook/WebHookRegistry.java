package io.pelle.mango.server.webhook;

import io.pelle.mango.client.api.WebHookVO;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.dao.IDAOCallback;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@RestController
public class WebHookRegistry implements InitializingBean {

	private final RestTemplate restTemplate = new RestTemplate();

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

	public void registerEntityWebHook(Class<? extends IBaseEntity> entityClass, WebHookVO webHook) {
		hooks.invalidate(getKey(entityClass));
	}

	public List<WebHookVO> getAllEntityWebHooks(String key) {
		SelectQuery<WebHookVO> selectQuery = SelectQuery.selectFrom(WebHookVO.class).where(WebHookVO.TYPE.eq(key));
		return baseVODAO.filter(selectQuery);
	}

	public List<WebHookVO> getAllEntityWebHooks(Class<? extends IBaseEntity> entityClass) {
		return getAllEntityWebHooks(getKey(entityClass));
	}

	public <VOType extends IVOEntity> void callEntityWebHooks(List<WebHookVO> webHooks, VOType voEntity) {
		for (WebHookVO webHook : webHooks) {
			try {
				restTemplate.postForEntity(webHook.getUrl(), new EntityWebHookCall(voEntity), Void.class);
			} catch (HttpClientErrorException e) {
				e.getStatusCode();
			}
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