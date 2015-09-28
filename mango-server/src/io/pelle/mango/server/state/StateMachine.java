package io.pelle.mango.server.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.db.dao.IBaseEntityDAO;

@StateMachineParameters(stateType = String.class, eventType = String.class, contextType = StateContext.class)
public class StateMachine extends AbstractUntypedStateMachine {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	private IBaseEntity baseEntity;

	private String attributeName;

	public StateMachine(IBaseEntity baseEntity, String attributeName) {
		super();
		this.baseEntity = baseEntity;
		this.attributeName = attributeName;
	}

	public void onEvent(String from, String to, String event, StateContext context) {

		IBaseEntity entity = baseEntityDAO.read(baseEntity.getId(), baseEntity.getClass());

		System.out.println("Transition from '" + from + "' to '" + to + "' on event '" + event + "' with context '" + context + "'.");

		io.pelle.mango.db.util.BeanUtils.setAttribute(entity, attributeName, to);

		baseEntityDAO.save(entity);
	}

}