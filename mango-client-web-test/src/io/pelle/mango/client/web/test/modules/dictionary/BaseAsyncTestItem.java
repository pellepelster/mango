package io.pelle.mango.client.web.test.modules.dictionary;

import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;

public abstract class BaseAsyncTestItem implements AsyncTestItem {

	@Override
	public boolean getStop() {
		return false;
	}

}
