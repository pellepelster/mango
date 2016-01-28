package io.pelle.mango.client.web.modules.dictionary.container;

import com.google.gwt.user.client.rpc.AsyncCallback;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IStateContainer;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IStateModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.state.CurrentState;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

public class StateContainer extends BaseContainerElement<IStateModel, IUpdateListener> implements IStateContainer {

	private CurrentState currentState;
	
	public StateContainer(IStateModel stateModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(stateModel, parent);
	}

	@Override
	public void update() {
		setEnabled(!getParent().getRootElement().getVOWrapper().isNew());

		IBaseVO vo = getParent().getRootElement().getVOWrapper().getContent();

		if (vo != null && !vo.isNew()) {
			MangoClientWeb.getInstance().getRemoteServiceLocator().getStateService().getCurrentState(vo.getId(), vo.getClass().getName(), new BaseErrorAsyncCallback<CurrentState>() {
				
				@Override
				public void onSuccess(CurrentState result) {
					currentState = result;
				}
			});
		}
	}

	@Override
	public void fireEvent(String eventId, final AsyncCallback<Void> callback) {

		IBaseVO vo = getParent().getRootElement().getVOWrapper().getContent();
				
		MangoClientWeb.getInstance().getRemoteServiceLocator().getStateService().triggerEvent(eventId, vo.getId(), vo.getClass().getName(), new BaseErrorAsyncCallback<CurrentState>() {

			@Override
			public void onSuccess(CurrentState result) {
				currentState = result;
				callback.onSuccess(null);
			} });
	}

	public CurrentState getCurrentState() {
		return currentState;
	}

	@Override
	public String getCurrentStateId() {
		return currentState == null ? null : currentState.getStateId();
	}
	
}
