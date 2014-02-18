package io.pelle.mango.client.web.test;

import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;

import java.util.LinkedList;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@SuppressWarnings("serial")
public class AsyncTestItemList extends LinkedList<AsyncTestItem> {

	@Override
	public boolean add(AsyncTestItem arg0) {

		boolean hasStop = Iterables.any(this, new Predicate<AsyncTestItem>() {

			@Override
			public boolean apply(AsyncTestItem input) {
				return input.getStop();
			}
		});

		if (hasStop) {
			throw new RuntimeException("async stop item found");
		} else {
			return super.add(arg0);
		}
	}

}
