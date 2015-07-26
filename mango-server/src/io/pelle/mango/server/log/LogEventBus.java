package io.pelle.mango.server.log;

import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.google.common.eventbus.AsyncEventBus;

public class LogEventBus {

	private static final Logger LOG = Logger.getLogger(LogEventBus.class);

	private AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));

	public void register(Object object) {
		eventBus.register(object);
	}

	public void post(LogEvent logEvent) {
		LOG.debug("post log event: '" + logEvent.toString() + "'");
		eventBus.post(logEvent);
	}
}
