package io.pelle.mango.client.web.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseErrorAsyncCallback<T> implements AsyncCallback<T> {
	private AsyncCallback<?> parentCallback = null;

	public BaseErrorAsyncCallback() {
	}

	public BaseErrorAsyncCallback(AsyncCallback<?> parentCallback) {
		this.parentCallback = parentCallback;
	}

	public AsyncCallback<?> getParentCallback() {
		return this.parentCallback;
	}

	@Override
	public void onFailure(Throwable caught) {
		if (this.parentCallback == null) {
			throw new RuntimeException(caught);
		} else {
			this.parentCallback.onFailure(caught);
		}
	}
}
