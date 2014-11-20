package io.pelle.mango.server.log;

import org.springframework.beans.factory.annotation.Autowired;

public class MangoLogger {

	@Autowired
	private LogEventBus logEventBus;

	public void info(String message) {
		info(message, null);
	}

	public void info(String message, String reference) {
		info(message, reference, -1);
	}

	public void info(String message, String reference, long timestamp) {
		LogEvent logEvent = null;
		
		if (timestamp > -1) {
			logEvent = new LogEvent(message, timestamp);
		} else {
			logEvent = new LogEvent(message);
		}
		logEvent.setReference(reference);
		sendLogMessage(logEvent);
	}

	private void sendLogMessage(final LogEvent logEvent) {
		logEventBus.post(logEvent);

	}

}
