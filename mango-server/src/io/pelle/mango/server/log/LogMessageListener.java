package io.pelle.mango.server.log;

import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.log.LogEntryVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;


public class LogMessageListener {

	private static final Logger LOG = Logger.getLogger(LogMessageListener.class);

	@Autowired
	private IBaseEntityService baseEntityService;

	@Subscribe
	public void onLogEvent(LogEvent logEvent) {
		
		LOG.debug("received log event : '" + logEvent.toString() + "'");

		LogEntryVO logEntry = new LogEntryVO();
		logEntry.setMessage(logEvent.getMessage());
		
		baseEntityService.create(logEntry);
	}

	@Autowired
	public void setLogEventBus(LogEventBus logEventBus) {
		logEventBus.register(this);
	}

}