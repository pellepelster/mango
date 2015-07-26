package io.pelle.mango.demo.server.test;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.server.log.IMangoLogger;

public class LogTask {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IMangoLogger mangoLogger;

	private Random random = new Random();

	public void createLogEntries() {

		int level = randInt(0, 2);

		if (level == 0) {
			mangoLogger.info("info message", "ping");
		} else if (level == 1) {
			mangoLogger.warn("warning message", "ping");
		} else if (level == 2) {
			mangoLogger.error("error message", "ping");
		}

		for (CountryVO countryVO : baseEntityService.filter(SelectQuery.selectFrom(CountryVO.class))) {

			level = randInt(0, 2);

			if (level == 0) {
				mangoLogger.info("country info message", countryVO);
			} else if (level == 1) {
				mangoLogger.warn("country warning message", countryVO);
			} else if (level == 2) {
				mangoLogger.error("country error message", countryVO);
			}
		}

	}

	public int randInt(int min, int max) {
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
