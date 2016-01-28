package io.pelle.mango.db.dao;

import static com.codahale.metrics.MetricRegistry.name;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.util.EntityVOMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class BaseEntityVODelegate {

	private static final String FILTER_METRIC_KEY = "filter";

	private Map<Class<? extends IBaseEntity>, Timer> filterTimers = new HashMap<Class<? extends IBaseEntity>, Timer>();

	@Autowired(required = false)
	public void setMetricRegistry(MetricRegistry metricRegistry, EntityVOMapper entityVOMapper) {

		for (Class<? extends IBaseEntity> entityClass : entityVOMapper.getEntityClasses()) {
			filterTimers.put(entityClass, metricRegistry.timer(name(entityClass, FILTER_METRIC_KEY)));
		}

	}

	protected Timer.Context getFilterContext(Class<? extends IBaseEntity> entityClass) {

		Timer timer = filterTimers.get(entityClass);

		if (timer != null) {
			return timer.time();
		}

		return null;
	}
}
