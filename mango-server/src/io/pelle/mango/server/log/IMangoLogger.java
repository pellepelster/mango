package io.pelle.mango.server.log;

public interface IMangoLogger {

	void info(String message);

	void info(String message, Object reference);

	void info(String message, Object reference, long timestamp);

}
