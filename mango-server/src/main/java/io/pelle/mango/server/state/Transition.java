package io.pelle.mango.server.state;

public class Transition {

	private String id;

	private State state;

	private Event event;

	public Transition(String id, String stateId) {
		this(id, stateId, id);
	}

	public Transition(String id, String stateId, String eventId) {
		super();
		this.id = id;
		this.state = new State(stateId);
		this.event = new Event(eventId);
	}

	public String getId() {
		return id;
	}

	public State getState() {
		return state;
	}

	public Event getEvent() {
		return event;
	}

}