package io.pelle.mango.client.base.modules.dictionary.container;

import com.google.gwt.user.client.rpc.AsyncCallback;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;

public interface IStateContainer extends IBaseContainer<IUpdateListener> {

	void fireEvent(String eventId, AsyncCallback<Void> callback);

	String getCurrentStateId();

}
