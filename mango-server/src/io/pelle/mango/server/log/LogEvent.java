package io.pelle.mango.server.log;

import java.util.Date;

import com.google.common.base.Objects;

public class LogEvent {

	private String message;

	private final Date timestamp;

	public LogEvent(String message) {
		super();
		this.message = message;
		this.timestamp = new Date();
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("timestamp", timestamp).add("message", message).toString();
	}

}
