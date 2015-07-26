package io.pelle.mango.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import io.pelle.mango.server.log.IMangoLogger;
import io.pelle.mango.server.log.LogEventBus;
import io.pelle.mango.server.log.LogMessageListener;
import io.pelle.mango.server.log.MangoLogger;

@Configuration
@EnableAsync
public class MangoLoggerApplicationContext {

	@Bean
	public IMangoLogger mangoLogger() {
		return new MangoLogger();
	}

	@Bean
	public LogEventBus logEventBus() {
		return new LogEventBus();
	}

	@Bean
	public LogMessageListener logMessageListener() {
		return new LogMessageListener();
	}

}
