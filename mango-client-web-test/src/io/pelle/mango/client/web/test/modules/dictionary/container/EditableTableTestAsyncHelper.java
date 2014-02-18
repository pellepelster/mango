package io.pelle.mango.client.web.test.modules.dictionary.container;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncTestItem;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EditableTableTestAsyncHelper<VOType extends IBaseVO> extends BaseAsyncHelper<EditableTableTest<VOType>> {

	public EditableTableTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void add() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().add(new BaseErrorAsyncCallback() {
					@Override
					public void onSuccess(Object result) {
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});

			}

			@Override
			public String getDescription() {
				return "add";
			}
		});
	}

	public void delete() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().delete(new BaseErrorAsyncCallback() {
					@Override
					public void onSuccess(Object result) {
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});

			}

			@Override
			public String getDescription() {
				return "delete";
			}
		});
	}

	public void assertRowCount(final int expectedRowCount) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertRowCount(expectedRowCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertRowCount(" + expectedRowCount + ")";
			}

		});
	}

}