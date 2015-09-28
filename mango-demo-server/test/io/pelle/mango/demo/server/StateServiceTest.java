package io.pelle.mango.demo.server;

import static io.pelle.mango.server.state.StateBuilder.transition;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.state.CurrentState;
import io.pelle.mango.client.state.IStateService;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.state.StateBuilder;

public class StateServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IStateService stateService;

	@Before
	public void beforeEach() {
		baseEntityService.deleteAll(Entity1VO.class.getName());
	}

	@Test
	public void testImpliciteStateCreation() {

		StateBuilder sb = StateBuilder.create(Entity1.class, Entity1VO.STATE1);
		sb.addState("stateA", transition("transitionAtoB", "stateB"));

		assertEquals(2, sb.getStates().size());
		assertEquals("stateA", sb.getStates().get(0).getId());
		assertEquals(1, sb.getStates().get(0).getTransitions().size());
		assertEquals("stateB", sb.getStates().get(1).getId());

		sb.addState("stateA", transition("transitionAtoB1", "stateB"));

		assertEquals(2, sb.getStates().size());
		assertEquals("stateA", sb.getStates().get(0).getId());
		assertEquals(2, sb.getStates().get(0).getTransitions().size());
		assertEquals("stateB", sb.getStates().get(1).getId());
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testInvalidEntity1() {
		stateService.getCurrentState(Long.MAX_VALUE, Entity1VO.class.getName());
	}

	@Test
	public void testEntity1InitialState() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1(UUID.randomUUID().toString());
		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);
		assertTrue(result.isOk());

		CurrentState currentState = stateService.getCurrentState(result.getValue().getId(), result.getValue().getClass().getName());
		assertEquals("stateA", currentState.getStateId());
		assertEquals(1, currentState.getTransitions().size());
		assertEquals("transitionAtoB", currentState.getTransitions().get(0).getId());
	}

	@Test
	public void testEntity1TriggerEvent() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1(UUID.randomUUID().toString());
		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);
		assertTrue(result.isOk());

		CurrentState currentState = stateService.getCurrentState(result.getValue().getId(), result.getValue().getClass().getName());
		assertEquals("stateA", currentState.getStateId());

		currentState = stateService.triggerEvent("transitionAtoB", result.getValue().getId(), result.getValue().getClass().getName());
		assertEquals("stateB", currentState.getStateId());
	}

}
