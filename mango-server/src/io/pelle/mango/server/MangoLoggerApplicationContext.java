package io.pelle.mango.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import io.pelle.mango.server.log.IMangoLogger;
import io.pelle.mango.server.log.MangoLogger;

@Configuration
@EnableAsync
public class MangoLoggerApplicationContext {

	@Bean
	public IMangoLogger mangoLogger() {
		return new MangoLogger();
	}

}
