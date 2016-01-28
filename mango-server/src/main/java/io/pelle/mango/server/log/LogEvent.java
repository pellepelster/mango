package io.pelle.mango.server.log;

import io.pelle.mango.client.log.LOGLEVEL;

import com.google.common.base.Objects;

public class LogEvent {

	private String message;

	private String reference;

	private final long timestamp;

	private final LOGLEVEL level;

	public LogEvent(String message, LOGLEVEL level) {
		this(message, level, System.currentTimeMillis());
	}

	public LogEvent(String message, LOGLEVEL level, long timeStamp) {
		super();
		this.message = message;
		this.level = level;
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

	public LOGLEVEL getLevel() {
		return level;
	}

}
