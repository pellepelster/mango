package io.pelle.mango.client.web.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseAsyncCallback<T, C> implements AsyncCallback<T>
{
	private List<AsyncCallback<C>> parentCallbacks = new ArrayList<AsyncCallback<C>>();

	public BaseAsyncCallback()
	{
	}

	public BaseAsyncCallback(AsyncCallback<C> parentCallback)
	{
		if (parentCallback != null)
		{
			this.parentCallbacks.add(parentCallback);
		}
	}

	public void addParentCallback(AsyncCallback<C> parentCallback)
	{
		if (parentCallback != null)
		{
			this.parentCallbacks.add(parentCallback);
		}
	}

	protected void callParentCallbacks(C result)
	{
		for (AsyncCallback<C> parentCallback : this.parentCallbacks)
		{
			parentCallback.onSuccess(result);
		}
		this.parentCallbacks.clear();
	}

	@Override
	public void onFailure(Throwable caught)
	{
		if (this.parentCallbacks.isEmpty())
		{
			throw new RuntimeException(caught);
		}

		for (AsyncCallback<C> parentCallback : this.parentCallbacks)
		{
			parentCallback.onFailure(caught);
		}
		this.parentCallbacks.clear();
	}
}
