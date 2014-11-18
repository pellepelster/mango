package io.pelle.mango.server.log;

import org.springframework.beans.factory.annotation.Autowired;

public class MangoLogger {

	@Autowired
	private LogEventBus logEventBus;

	public void info(String message) {
		LogEvent logEvent = new LogEvent(message);
		sendLogMessage(logEvent);
	}

	private void sendLogMessage(final LogEvent logEvent) {
		logEventBus.post(logEvent);

	}

}
