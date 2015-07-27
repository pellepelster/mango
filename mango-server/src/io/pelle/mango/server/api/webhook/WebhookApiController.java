package io.pelle.mango.server.api.webhook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.web.modules.dictionary.databinding.ValidationUtils;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.server.BaseEntityApiController;

@RestController
public class WebhookApiController extends BaseEntityApiController {

	@Autowired
	private IBaseVODAO baseVODAO;

	@Autowired
	private EntityWebhookRegistry webHookRegistry;

	@RequestMapping(value = "api/entity/{entityName}/webhooks")
	public List<WebhookVO> listHooks(@PathVariable String entityName) {
		return webHookRegistry.getAllEntityWebHooks(getEntityClassByNameOrExplode(entityName));
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks/{hookName}", method = RequestMethod.DELETE)
	public void deleteHook(@PathVariable String entityName, @PathVariable String hookName) {
		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);
		webHookRegistry.deleteHook(entityClass.getName(), hookName);
	}

	@ExceptionHandler(WebhookException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public WebhookErrorResponse handleWebHookException(WebhookException e) {
		return new WebhookErrorResponse(e.getMessage());
	}

	@RequestMapping(value = "api/entity/{entityName}/webhooks", method = RequestMethod.POST)
	public WebhookVO addHook(@PathVariable String entityName, @RequestBody WebhookRegisterRequest webHookRegisterRequest) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		WebhookVO webHook = EntityWebhookDefitnition.INSTANCE.createNewWebHook(entityClass);
		webHook.setName(webHookRegisterRequest.getName());
		webHook.setUrl(webHookRegisterRequest.getUrl());

		Result<WebhookVO> result = webHookRegistry.addEntityWebHook(webHook);

		if (result.isOk()) {
			return result.getValue();
		} else {
			throw new WebhookException(ValidationUtils.getErrorMessage(result.getValidationMessages()));
		}

	}

}