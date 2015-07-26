package io.pelle.mango.server.log;

import io.pelle.mango.client.log.LOGLEVEL;

public interface IMangoLogger {

	void info(String message);

	void info(String message, Object reference);

	void warn(String message);

	void warn(String message, Object reference);

	void error(String message);

	void error(String message, Object reference);

	void log(String message, LOGLEVEL level, Object reference, long timestamp);

}
