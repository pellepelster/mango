package io.pelle.mango.server.state;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	private String id;

	private List<Transition> transitions = new ArrayList<>();
	
	public State(String id) {
		super();
		this.id = id;
	}
	
	public List<Transition> getTransitions() {
		return transitions;
	}

	public String getId() {
		return id;
	}
}