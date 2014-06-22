package io.pelle.mango.client.web.test;

import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;

import java.util.LinkedList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RecursiveAsyncCallback implements AsyncCallback<Object> {

	private final LinkedList<AsyncTestItem> asyncTestItems;

	private final AsyncCallback finalCallback;

	public RecursiveAsyncCallback(LinkedList<AsyncTestItem> asyncTestItems, AsyncCallback<?> finalCallback) {
		super();
		this.asyncTestItems = asyncTestItems;
		this.finalCallback = finalCallback;
	}

	@Override
	public void onFailure(Throwable caught) {
		throw new RuntimeException(caught);
	}

	@Override
	public void onSuccess(Object result) {

		if (this.asyncTestItems.isEmpty()) {
			this.finalCallback.onSuccess(result);
		} else {
			AsyncTestItem asyncTestItem = this.asyncTestItems.removeFirst();

			GWT.log("running async test item '" + asyncTestItem.getDescription() + "'");
			asyncTestItem.run(new RecursiveAsyncCallback(this.asyncTestItems, this.finalCallback));
		}

	}
}
