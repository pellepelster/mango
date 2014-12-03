package io.pelle.mango.server.log;

import org.springframework.beans.factory.annotation.Autowired;

public class MangoLogger implements IMangoLogger {

	@Autowired
	private LogEventBus logEventBus;

	@Autowired
	private LogReferenceKeyMapperRegistry referenceKeyMapperRegistry;

	@Override
	public void info(String message) {
		info(message, null);
	}

	@Override
	public void info(String message, Object reference) {
		info(message, reference, -1);
	}

	@Override
	public void info(String message, Object reference, long timestamp) {
		LogEvent logEvent = null;

		if (timestamp > -1) {
			logEvent = new LogEvent(message, timestamp);
		} else {
			logEvent = new LogEvent(message);
		}

		String referenceKey = null;

		if (reference instanceof String) {
			referenceKey = (String) reference;
		} else {
			referenceKey = referenceKeyMapperRegistry.getLogReferenceKey(reference);
		}

		logEvent.setReference(referenceKey);
		sendLogMessage(logEvent);
	}

	private void sendLogMessage(final LogEvent logEvent) {
		logEventBus.post(logEvent);
	}

}
