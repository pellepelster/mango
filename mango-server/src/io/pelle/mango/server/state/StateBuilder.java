package io.pelle.mango.server.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.builder.To;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseEntity;

public class StateBuilder {

	private class StatePredicate implements Predicate<State> {

		private String id;

		public StatePredicate(String id) {
			super();
			this.id = id;
		}

		@Override
		public boolean apply(State input) {
			return Objects.equal(input.getId(), id);
		}

	}

	public static Transition transition(String id, String stateId) {
		Transition transition = new Transition(id, stateId);
		return transition;
	}

	public static Transition transition(String id, String stateId, String eventId) {
		Transition transition = new Transition(id, stateId, eventId);
		return transition;
	}

	public static Event event(String id) {
		Event event = new Event(id);
		return event;
	}

	private List<State> states = new ArrayList<>();

	private Class<? extends IBaseEntity> entityClass;

	private String attributeName;

	private StateBuilder(Class<? extends IBaseEntity> entityClass, String attributeName) {
		super();
		this.entityClass = entityClass;
		this.attributeName = attributeName;
	}

	public static StateBuilder create(Class<? extends IBaseEntity> entityClass, IAttributeDescriptor<String> attributeName) {
		return new StateBuilder(entityClass, attributeName.getAttributeName());
	}

	private void createStateIfNotExists(State state) {

		if (!Iterables.any(states, new StatePredicate(state.getId()))) {
			states.add(state);
		}

	}

	public StateBuilder addState(String id, Transition... transitions) {

		Optional<State> state = Iterables.tryFind(states, new StatePredicate(id));

		if (!state.isPresent()) {
			state = Optional.of(new State(id));
			states.add(state.get());
		}

		state.get().getTransitions().addAll(Arrays.asList(transitions));

		for (Transition transition : transitions) {
			createStateIfNotExists(transition.getState());
		}

		return this;
	}

	public List<State> getStates() {
		return states;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UntypedStateMachine createStateMachine(IBaseEntity entity) {

		UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachine.class);

		for (State state : states) {

			for (Transition transition : state.getTransitions()) {
				To to = builder.externalTransition().from(state.getId()).to(transition.getState().getId());
				
				if (transition.getEvent() != null) {
					to.on(transition.getEvent().getId()).callMethod("log");
				}
				
			}
		}
		
		return builder.newStateMachine(getInitalState(entity));
	}


	public String getCurrentState(IBaseEntity entity) {
		
		Object attributeValue = io.pelle.mango.db.util.BeanUtils.getAttribute(entity, attributeName);
		
		if (attributeValue != null) {
			return attributeValue.toString();
		} else {
			return null;
		}
	}

	private void setCurrentState(IBaseEntity entity, String state) {
		io.pelle.mango.db.util.BeanUtils.setAttribute(entity, attributeName, state);
	}

	private String getInitalState(IBaseEntity entity) {
		return states.get(0).getId();
	}

	private String getCurrentStateWithInitalFallback(IBaseEntity entity) {
		return Objects.firstNonNull(getCurrentState(entity), getInitalState(entity));
	}
	
	
	public Class<? extends IBaseEntity> getVOClass() {
		return entityClass;
	}

	public List<Transition> getTransitions(IBaseEntity entity) {
		return Iterables.tryFind(states, new StatePredicate(getCurrentStateWithInitalFallback(entity))).get().getTransitions();
	}

	public void setInitalState(IBaseEntity entity) {
		setCurrentState(entity, getInitalState(entity));
	}
}
