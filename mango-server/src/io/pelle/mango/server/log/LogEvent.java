package io.pelle.mango.server.log;

import com.google.common.base.Objects;

public class LogEvent {

	private String message;

	private String reference;

	private final long timestamp;

	public LogEvent(String message) {
		this(message, System.currentTimeMillis());
	}

	public LogEvent(String message, long timeStamp) {
		super();
		this.message = message;
		this.timestamp = timeStamp;
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

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

}
