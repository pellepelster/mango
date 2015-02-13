package io.pelle.mango.server.api.entity;

public class EntityWebHookCall {

	private Object payload;

	public EntityWebHookCall(Object payload) {
		super();
		this.payload = payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getPayload() {
		return payload;
	}

}
