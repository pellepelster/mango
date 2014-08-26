package io.pelle.mango.client.web.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DummyAsyncCallback<T> implements AsyncCallback<T>
{
	@SuppressWarnings("rawtypes")
	private static DummyAsyncCallback instance = new DummyAsyncCallback();

	private DummyAsyncCallback()
	{
	}

	@Override
	public void onFailure(Throwable caught)
	{
	}

	@Override
	public void onSuccess(Object result)
	{
	}

	@SuppressWarnings("unchecked")
	public static <A> DummyAsyncCallback<A> dummyAsyncCallback()
	{
		return instance;
	}

}
