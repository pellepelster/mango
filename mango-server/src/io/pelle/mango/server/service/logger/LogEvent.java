package io.pelle.mango.server.service.logger;

public class LogEvent {

	private String message;

	public LogEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
