package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncTestItem;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseControlTestAsyncHelper<T extends BaseControlTest<? extends IBaseControl<ValueType, ?>, ValueType>, ValueType> extends BaseAsyncHelper<T> {

	public BaseControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void setValue(final ValueType value) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().setValue(value);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "setValue(" + value + ")";
			}

		});
	}

	public void assertValue(final ValueType expectedValue) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertValue(expectedValue);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertValue(" + expectedValue + ")";
			}
		});
	}

	public void parse(final String valueString) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().parse(valueString);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "parse(" + valueString + ")";
			}
		});
	}

	public void assertMandatory() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertMandatory();
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertMandatory";
			}

		});
	}

	public void assertHasErrors() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertHasErrors();
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertHasErrors";
			}
		});

	}

	public void assertHasNoErrors() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertHasNoErrors();
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertHasNoErrors";
			}
		});

	}

	public void assertHasErrorWithText(final String text) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertHasErrorWithText(text);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertHasErrorWithText(" + text + ")";
			}
		});
	}

}
