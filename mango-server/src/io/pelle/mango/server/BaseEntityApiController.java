package io.pelle.mango.server;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseEntityApiController {

	@Autowired
	protected VOMetaDataService metaDataService;

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleException(Exception e) {
		return e.getMessage();
	}

	protected Class<? extends IBaseEntity> getEntityClassByNameOrExplode(String entityName) {

		Class<? extends IBaseEntity> entityClass = metaDataService.getEntityClassForName(entityName);

		if (entityClass == null) {
			throw new RuntimeException(String.format("could not find entity entity for name '%s'", entityName));
		}

		return entityClass;
	}

}
