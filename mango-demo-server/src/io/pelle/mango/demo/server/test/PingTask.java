package io.pelle.mango.demo.server.test;

import io.pelle.mango.server.log.MangoLogger;

import org.springframework.beans.factory.annotation.Autowired;

public class PingTask {

	@Autowired
	private MangoLogger mangoLogger;

	public void ping() {
		mangoLogger.info("ping");
	}

}
