package io.pelle.mango.server.log;

import io.pelle.mango.client.log.LOGLEVEL;

import org.springframework.beans.factory.annotation.Autowired;

public class MangoLogger implements IMangoLogger {

	@Autowired
	private LogEventBus logEventBus;

	@Autowired
	private LogReferenceKeyMapperRegistry referenceKeyMapperRegistry;

	public void log(String message, LOGLEVEL level, Object reference, long timestamp) {
		LogEvent logEvent = null;

		if (timestamp > -1) {
			logEvent = new LogEvent(message, level, timestamp);
		} else {
			logEvent = new LogEvent(message, level);
		}

		String referenceKey = referenceKeyMapperRegistry.getLogReferenceKey(reference);
		logEvent.setReference(referenceKey);

		sendLogMessage(logEvent);
	}

	private void sendLogMessage(final LogEvent logEvent) {
		logEventBus.post(logEvent);
	}

	@Override
	public void warn(String message) {
		warn(message, null);
	}

	@Override
	public void warn(String message, Object reference) {
		log(message, LOGLEVEL.WARNING, reference, -1);
	}

	@Override
	public void error(String message) {
		error(message, null);
	}

	@Override
	public void error(String message, Object reference) {
		log(message, LOGLEVEL.ERROR, reference, -1);
	}

	@Override
	public void info(String message) {
		info(message, null);
	}

	@Override
	public void info(String message, Object reference) {
		log(message, LOGLEVEL.INFO, reference, -1);
	}

}
