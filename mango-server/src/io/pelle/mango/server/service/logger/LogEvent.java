package io.pelle.mango.server.service.logger;

import com.google.common.base.Objects;

public class LogEvent {

	private String message;

	public LogEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("message", message).toString();
	}

}
