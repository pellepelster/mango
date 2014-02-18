package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncTestItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DateControlTestAsyncHelper extends BaseControlTestAsyncHelper<DateControlTest, Date>
{

	public DateControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void assertValueWithoutMillies(final Date expectedValue)
	{
		this.addAsyncTestItem(new BaseAsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				getAsyncTestItemResult().assertValueWithoutMillies(expectedValue);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription()
			{
				return "assertValueWithoutMillies(" + expectedValue + ")";
			}
		});
	}

}
