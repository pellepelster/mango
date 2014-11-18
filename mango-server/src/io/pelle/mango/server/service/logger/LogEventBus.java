package io.pelle.mango.server.service.logger;

import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;

public class LogEventBus {

	private AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));

	private LogEventBus() {
	}

	public void register(Object object) {
		eventBus.register(object);
	}

	public void post(LogEvent logEvent) {
		eventBus.register(logEvent);
	}
}
