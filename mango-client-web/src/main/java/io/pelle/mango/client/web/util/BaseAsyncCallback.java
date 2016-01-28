package io.pelle.mango.client.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseAsyncCallback<T, C> implements AsyncCallback<T> {
	private List<AsyncCallback<C>> parentCallbacks = new ArrayList<AsyncCallback<C>>();

	public BaseAsyncCallback() {
	}

	public BaseAsyncCallback(Optional<AsyncCallback<C>> parentCallback) {
		if (parentCallback.isPresent()) {
			this.parentCallbacks.add(parentCallback.get());
		}
	}

	protected void callParentCallbacks(C result) {
		for (Iterator<AsyncCallback<C>> iterator = this.parentCallbacks.iterator(); iterator.hasNext();) {
			AsyncCallback<C> parentCallback = iterator.next();
			iterator.remove();
			parentCallback.onSuccess(result);
		}
	}

	@Override
	public void onFailure(Throwable caught) {
		if (this.parentCallbacks.isEmpty()) {
			throw new RuntimeException(caught);
		}

		for (AsyncCallback<C> parentCallback : this.parentCallbacks) {
			parentCallback.onFailure(caught);
		}

		this.parentCallbacks.clear();
	}
}
