package io.pelle.mango.demo.server.test;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.showcase.CountryVO;
import io.pelle.mango.server.log.MangoLogger;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public class LogTask {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private MangoLogger mangoLogger;

	private Random random = new Random();

	public void createLogEntries() {

		int level = randInt(0, 2);

		if (level == 0) {
			mangoLogger.info("ping", "ping");
		} else if (level == 1) {
			mangoLogger.warn("ping", "ping");
		} else if (level == 2) {
			mangoLogger.error("ping", "ping");
		}

		for (CountryVO countryVO : baseEntityService.filter(SelectQuery.selectFrom(CountryVO.class))) {

			mangoLogger.info("country ping", countryVO);
		}

	}

	public int randInt(int min, int max) {
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
