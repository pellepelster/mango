package io.pelle.mango.server.webhook;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.server.BaseEntityApiController;
import io.pelle.mango.server.api.WebHook;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHookApiController extends BaseEntityApiController {

	@RequestMapping(value = "api/entity/{entityName}/webhooks")
	public List<WebHook> listHooks(@PathVariable String entityName) {

		Class<? extends IBaseEntity> entityClass = (Class<? extends IBaseEntity>) getEntityClassByNameOrExplode(entityName);

		return Collections.emptyList();
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks/{hookId}", method = RequestMethod.DELETE)
	public boolean deleteHook(@PathVariable String entityName, @PathVariable String hookId) {

		Class<? extends IBaseEntity> entityClass = (Class<? extends IBaseEntity>) getEntityClassByNameOrExplode(entityName);

		return true;
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks", method = RequestMethod.POST)
	public WebHook addHook(@PathVariable String entityName, @RequestBody WebHookRegisterRequest webHookRegisterRequest) {

		Class<? extends IBaseEntity> entityClass = (Class<? extends IBaseEntity>) getEntityClassByNameOrExplode(entityName);

		return null;
	}

}