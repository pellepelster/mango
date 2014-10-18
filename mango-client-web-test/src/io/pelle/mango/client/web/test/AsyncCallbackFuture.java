package io.pelle.mango.client.web.test;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AsyncCallbackFuture<V> extends AbstractFuture<V> implements AsyncCallback<V> {

	public static <V> AsyncCallbackFuture<V> create() {
		return new AsyncCallbackFuture<V>();
	}

	@Override
	public void onFailure(Throwable caught) {
		super.setException(caught);
	}

	@Override
	public V get() {
		try {
			return super.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onSuccess(V result) {
		super.set(result);
	}

	AsyncCallback<V> getCallback() {
		return this;
	}
}
