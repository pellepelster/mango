package io.pelle.mango.server.state;

import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = String.class, eventType = String.class, contextType = Integer.class)
public class StateMachine extends AbstractUntypedStateMachine {
	
	protected void log(String from, String to, String event, Integer context) {
		System.out.println("Transition from '" + from + "' to '" + to + "' on event '" + event + "' with context '" + context + "'.");
	}

}