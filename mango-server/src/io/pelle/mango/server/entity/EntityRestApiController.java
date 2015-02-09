package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

@RestController
public class EntityRestApiController {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Autowired
	private VOMetaDataService metaDataService;

	@RequestMapping("entity/{entityName}/api/rest/byid/{entityId}")
	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> T entityById(@PathVariable String entityName, @PathVariable long entityId) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		T result = (T) baseEntityDAO.read(entityId, entityClass);

		if (result != null) {
			return result;
		} else {
			throw new RuntimeException(String.format("no entity found with id  %d", entityId));
		}

	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleException(Exception e) {
		return e.getMessage();
	}

	@RequestMapping("entity/{entityName}/api/rest/bynaturalkey/{naturalKey}")
	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> T entityByNaturalKey(@PathVariable String entityName, @PathVariable String naturalKey) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		Optional<T> result = (Optional<T>) baseEntityDAO.getByNaturalKey(entityClass, naturalKey);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException(String.format("no entity found for natural key '%s'", naturalKey));
		}
	}

	private Class<? extends IBaseEntity> getEntityClassByNameOrExplode(String entityName) {

		Class<? extends IBaseEntity> entityClass = metaDataService.getEntityClassForName(entityName);

		if (entityClass == null) {
			throw new RuntimeException(String.format("could not find entity entity for name '%s'", entityName));
		}

		return entityClass;
	}

}
