package io.pelle.mango.server.api.entity;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.server.BaseEntityApiController;
import io.pelle.mango.server.entity.EntityUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

@RestController
public class EntityApiController extends BaseEntityApiController {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@RequestMapping(value = "api/entity/{entityName}/query", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> List<T> queryGet(@PathVariable String entityName, @RequestParam String query) {

		Class<T> entityClass = (Class<T>) getEntityClassByNameOrExplode(entityName);

		try {
			SelectQuery<T> selectQuery = EntityUtils.createSelectQuery(entityClass, query);
			List<T> result = baseEntityDAO.filter(selectQuery);

			return result;
		} catch (Exception e) {
			throw new RuntimeException(String.format("'%s' is not an valid expression", query));
		}
	}

	@RequestMapping(value = "api/entity/{entityName}/query", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> List<T> queryPost(@PathVariable String entityName, @RequestBody QueryRequest queryRequest) {

		Class<T> entityClass = (Class<T>) getEntityClassByNameOrExplode(entityName);

		try {
			SelectQuery<T> selectQuery = EntityUtils.createSelectQuery(entityClass, queryRequest.getQuery());
			List<T> result = baseEntityDAO.filter(selectQuery);

			return result;
		} catch (Exception e) {
			throw new RuntimeException(String.format("'%s' is not an valid expression", queryRequest.getQuery()));
		}
	}

	@RequestMapping("api/entity/{entityName}/byid/{entityId}")
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

	@RequestMapping("api/entity/{entityName}/bynaturalkey/{naturalKey}")
	@SuppressWarnings("unchecked")
	public <T extends IBaseEntity> T entityByNaturalKey(@PathVariable String entityName, @PathVariable String naturalKey) {

		Class<? extends IBaseEntity> entityClass = getEntityClassByNameOrExplode(entityName);

		Optional<T> result = (Optional<T>) baseEntityDAO.searchByNaturalKey(entityClass, naturalKey);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException(String.format("no entity found for natural key '%s'", naturalKey));
		}
	}

}
