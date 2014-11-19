package io.pelle.mango.server.log;

import com.google.common.base.Objects;

public class LogEvent {

	private String message;

	private final long timestamp;

	public LogEvent(String message) {
		super();
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("timestamp", timestamp).add("message", message).toString();
	}

}
