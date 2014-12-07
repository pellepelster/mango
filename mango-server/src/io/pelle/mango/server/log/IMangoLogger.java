package io.pelle.mango.server.log;

public interface IMangoLogger {

	void info(String message);

	void info(String message, Object reference);

	void warn(String message);

	void warn(String message, Object reference);

	void error(String message);

	void error(String message, Object reference);

	// void info(String message, Object reference, long timestamp);

}
