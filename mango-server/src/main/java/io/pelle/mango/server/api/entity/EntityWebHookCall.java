package io.pelle.mango.server.api.entity;

import java.util.Date;

public class EntityWebHookCall {

	private Object payload;

	private String event;

	private String timestamp;

	public EntityWebHookCall(String event, Object payload) {
		super();
		this.event = event;
		this.payload = payload;
		this.timestamp = Long.toString(new Date().getTime());
	}

	public Object getPayload() {
		return payload;
	}

	public String getEvent() {
		return event;
	}

	public String getTimestamp() {
		return timestamp;
	}
}
