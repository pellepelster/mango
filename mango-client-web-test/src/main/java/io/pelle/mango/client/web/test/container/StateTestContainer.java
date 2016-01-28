package io.pelle.mango.client.web.test.container;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IStateContainer;
import io.pelle.mango.client.web.test.AsyncCallbackFuture;

public class StateTestContainer extends BaseTestContainer implements IUpdateListener {

	private IStateContainer stateContainer;

	private String currentStateId;

	public StateTestContainer() {
		super();
	}

	public StateTestContainer(IStateContainer stateContainer) {
		super(stateContainer);
		this.stateContainer = stateContainer;

		stateContainer.addUpdateListener(this);
		onUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		currentStateId = stateContainer.getCurrentStateId();
	}

	public void fireEvent(String eventId) {

		AsyncCallbackFuture<Void> future = AsyncCallbackFuture.create();

		stateContainer.fireEvent(eventId, future);

		future.get();
	}

	public void assertCurrentState(String expected) {
		Assert.assertEquals(expected, currentStateId);
	}

}
