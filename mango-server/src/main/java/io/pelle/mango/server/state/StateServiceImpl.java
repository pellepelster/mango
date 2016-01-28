package io.pelle.mango.server.state;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.state.CurrentState;
import io.pelle.mango.client.state.IStateService;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.util.EntityVOMapper;

public class StateServiceImpl implements IStateService {

	@Autowired
	private AutowireCapableBeanFactory factory;

	private static Function<Transition, io.pelle.mango.client.state.Transition> TRANSITION2TRANSITION = new Function<Transition, io.pelle.mango.client.state.Transition>() {

		@Override
		public io.pelle.mango.client.state.Transition apply(Transition input) {
			io.pelle.mango.client.state.Transition result = new io.pelle.mango.client.state.Transition();

			result.setId(input.getId());

			if (input.getEvent() != null) {
				result.setEvent(EVENT2EVENT.apply(input.getEvent()));
			}

			return result;
		}
	};

	private static Function<Event, io.pelle.mango.client.state.Event> EVENT2EVENT = new Function<Event, io.pelle.mango.client.state.Event>() {

		@Override
		public io.pelle.mango.client.state.Event apply(Event input) {
			io.pelle.mango.client.state.Event result = new io.pelle.mango.client.state.Event();

			result.setId(input.getId());

			return result;
		}

	};

	private class StateBuilderPredicate implements Predicate<StateBuilder> {

		private Class<?> voClass;

		public StateBuilderPredicate(Class<?> voClass) {
			super();
			this.voClass = voClass;
		}

		@Override
		public boolean apply(StateBuilder input) {
			return input.getVOClass().equals(voClass);
		}

	}

	@Autowired(required = false)
	private List<StateBuilder> stateBuilders = new ArrayList<>();

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Override
	public CurrentState getCurrentState(Long id, String voClassName) {

		IBaseEntity entity = getEntityOrExplode(id, voClassName);

		StateBuilder stateBuilder = getStateBuilder(entity);

		if (stateBuilder == null) {
			throw new RuntimeException(String.format("no state definition found for '%s'", entity.getClass().getName()));
		}

		CurrentState result = new CurrentState();

		result.setStateId(stateBuilder.getCurrentState(entity));
		List<Transition> transitions = stateBuilder.getTransitions(entity);
		result.setTransitions(new ArrayList<io.pelle.mango.client.state.Transition>(Collections2.transform(transitions, TRANSITION2TRANSITION)));

		return result;
	}

	private IBaseEntity getEntityOrExplode(Long id, String voClassName) {

		IBaseEntity entity = baseEntityDAO.read(id, EntityVOMapper.getInstance().getEntityClass(voClassName));

		if (entity == null) {
			throw new RuntimeException(String.format("entity '%s' with id %d not found", voClassName, id));
		}

		return entity;
	}

	public StateBuilder getStateBuilder(IBaseEntity vo) {

		Optional<StateBuilder> stateBuilder = Iterables.tryFind(stateBuilders, new StateBuilderPredicate(vo.getClass()));

		if (stateBuilder.isPresent()) {
			return stateBuilder.get();
		} else {
			return null;
		}
	}

	@Override
	public CurrentState triggerEvent(String eventId, Long id, String voClassName) {

		IBaseEntity entity = getEntityOrExplode(id, voClassName);

		if (entity == null) {
			throw new RuntimeException(String.format("entity '%s' with id %d not found", voClassName, id));
		}

		StateBuilder stateBuilder = getStateBuilder(entity);

		UntypedStateMachine stateMachine = stateBuilder.createStateMachine(entity);
		factory.autowireBean(stateMachine);

		stateMachine.fireImmediate(eventId);

		return getCurrentState(id, voClassName);
	}

}
