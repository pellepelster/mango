package io.pelle.mango.server.log;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.google.common.base.Function;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.log.LOGLEVEL;
import io.pelle.mango.client.log.LogEntryVO;

public class MangoLogger implements IMangoLogger {

	private static final Logger LOG = Logger.getLogger(MangoLogger.class);

	@Autowired
	private LogReferenceKeyMapperRegistry referenceKeyMapperRegistry;

	@Autowired
	private IBaseEntityService baseEntityService;

	private static Function<LogEvent, LogEntryVO> LOG_EVENT2LOG_ENTRY = new Function<LogEvent, LogEntryVO>() {

		@Override
		public LogEntryVO apply(LogEvent logEvent) {

			LogEntryVO logEntry = new LogEntryVO();
			logEntry.setMessage(logEvent.getMessage());
			logEntry.setTimestamp(logEvent.getTimestamp());
			logEntry.setReference(logEvent.getReference());
			logEntry.setLevel(logEvent.getLevel());
			return logEntry;
		}
	};

	@Async
	@Override
	public void log(String message, LOGLEVEL level, Object reference, long timestamp) {
		LogEvent logEvent = null;

		if (timestamp > -1) {
			logEvent = new LogEvent(message, level, timestamp);
		} else {
			logEvent = new LogEvent(message, level);
		}

		String referenceKey = referenceKeyMapperRegistry.getLogReferenceKey(reference);
		logEvent.setReference(referenceKey);

		baseEntityService.create(LOG_EVENT2LOG_ENTRY.apply(logEvent));
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
