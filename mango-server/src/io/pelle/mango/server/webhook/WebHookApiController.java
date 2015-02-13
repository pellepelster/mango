package io.pelle.mango.server.webhook;

import io.pelle.mango.client.api.WebHookVO;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.server.BaseEntityApiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

@RestController
public class WebHookApiController extends BaseEntityApiController {

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private WebHookRegistry webHookRegistry;

	@RequestMapping(value = "api/entity/{entityName}/webhooks")
	public List<WebHookVO> listHooks(@PathVariable String entityName) {
		return webHookRegistry.getAllEntityWebHooks(getEntityClassByNameOrExplode(entityName));
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks/{hookName}", method = RequestMethod.DELETE)
	public boolean deleteHook(@PathVariable String entityName, @PathVariable String hookName) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		SelectQuery<WebHookVO> selectQuery = SelectQuery.selectFrom(WebHookVO.class).where(WebHookVO.TYPE.eq(entityClass.getName()).and(WebHookVO.NAME.eq(hookName)));

		Optional<WebHookVO> webHook = baseVODAO.read(selectQuery);

		if (webHook.isPresent()) {
			baseVODAO.delete(webHook.get());
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks", method = RequestMethod.POST)
	public WebHookVO addHook(@PathVariable String entityName, @RequestBody WebHookRegisterRequest webHookRegisterRequest) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		WebHookVO webHook = new WebHookVO();
		webHook.setType(entityClass.getName());
		webHook.setName(webHookRegisterRequest.getName());
		webHook.setUrl(webHookRegisterRequest.getUrl());

		return webHookRegistry.registerEntityWebHook(entityClass, webHook);
	}

}