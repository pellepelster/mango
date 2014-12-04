package io.pelle.mango.demo.server.test;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.server.log.MangoLogger;

import org.springframework.beans.factory.annotation.Autowired;

public class LogTask {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private MangoLogger mangoLogger;

	public void createLogEntries() {
		mangoLogger.info("ping", "ping");

		for (CountryVO countryVO : baseEntityService.filter(SelectQuery.selectFrom(CountryVO.class))) {
			mangoLogger.info("country ping", countryVO);
		}

	}

}
