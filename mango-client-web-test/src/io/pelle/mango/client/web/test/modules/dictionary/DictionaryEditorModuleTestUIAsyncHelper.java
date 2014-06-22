/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.test.modules.dictionary;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.model.containers.EditableTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.container.EditableTableTestAsyncHelper;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public class DictionaryEditorModuleTestUIAsyncHelper<VOType extends IBaseVO> extends BaseDictionaryModuleTestUIAsyncHelper<DictionaryEditorModuleTestUI> {

	public DictionaryEditorModuleTestUIAsyncHelper(String asynTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(asynTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public void assertTitle(final String expectedTitle) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertTitle(expectedTitle);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertTitle(" + expectedTitle + ")";
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

	public void assertHasErrors(final int errorCount) {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertHasErrors(errorCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertHasErrors(" + errorCount + ")";
			}

		});
	}

	public <TableVOType extends IBaseVO> EditableTableTestAsyncHelper<TableVOType> getEditableTableTest(final EditableTableModel<TableVOType> tableModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				DictionaryEditorModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getEditableTableTest(tableModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getEditableTableTest";
			}

		});

		return new EditableTableTestAsyncHelper<TableVOType>(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public void save() {
		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().save(new BaseErrorAsyncCallback<DictionaryEditorModuleTestUI>() {

					@Override
					public void onSuccess(DictionaryEditorModuleTestUI result) {
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});
			}

			@Override
			public String getDescription() {
				return "save";
			}

		});
	}
}
