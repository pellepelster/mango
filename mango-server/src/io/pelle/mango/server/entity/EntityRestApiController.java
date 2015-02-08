package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.base.MangoConstants;
import io.pelle.mango.server.vo.VOMetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(MangoConstants.ENTITY_API_REST_PATH)
public class EntityRestApiController {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Autowired
	private VOMetaDataService metaDataService;

	@SuppressWarnings("unchecked")
	@RequestMapping("byid/{entityName}/{entityId}")
	public <T extends IBaseEntity> T entityById(@PathVariable String entityName, @PathVariable long entityId) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		return (T) baseEntityDAO.read(entityId, entityClass);
	}

	@RequestMapping("bynaturalkey/{entityName}/{naturalKey}")
	public <T extends IBaseEntity> T entityByNaturalKey(@PathVariable String entityName, @PathVariable String naturalKey) {

		// return baseEntityDAO.getByNaturalKey(entityClass, naturalKey);
		return null;
	}

	private Class<? extends IBaseEntity> getEntityClassByNameOrExplode(String entityName) {

		Class<? extends IBaseEntity> entityClass = metaDataService.getEntityClassForName(entityName);

		if (entityClass == null) {
			throw new RuntimeException(String.format("could not find entity entity for name '%s'", entityName));
		}

		return entityClass;
	}

}
