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
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public class DictionarySearchModuleTestUIAsyncHelper<VOType extends IBaseVO> extends BaseDictionaryModuleTestUIAsyncHelper<DictionarySearchModuleTestUI> {

	public DictionarySearchModuleTestUIAsyncHelper(String parentUuid, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(parentUuid, asyncTestItems, asyncTestItemResults);
	}

	public void assertTitle(final String expectedTitle) {
		this.getAsyncTestItems().add(new BaseAsyncTestItem() {
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

	public void assertResultCount(final int expectedResultCount) {
		this.getAsyncTestItems().add(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().assertResultCount(expectedResultCount);
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "assertResultCount(" + expectedResultCount + ")";
			}
		});
	}

	public DictionaryEditorModuleTestUIAsyncHelper<VOType> openEditor() {
		final String uuid = UUID.uuid();

		this.getAsyncTestItems().add(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {

				String dictionaryName = getAsyncTestItemResult().getModule().getDictionaryModel().getName();

				getAsyncTestItemResult().getModule().getDictionarySearch().getDictionaryResult().activateSelection();

				AsyncCallback<IModuleUI> moduleCallback = new BaseErrorAsyncCallback<IModuleUI>() {
					@Override
					public void onSuccess(IModuleUI result) {
						getAsyncTestItemResults().put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				};

				ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName, 0), null, new HashMap<String, Object>(), Optional.of(moduleCallback));
			}

			@Override
			public String getDescription() {
				return "openEditor";
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(uuid, getAsyncTestItems(), getAsyncTestItemResults());

	}

	public void execute() {
		this.getAsyncTestItems().add(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				getAsyncTestItemResult().search(new BaseErrorAsyncCallback<DictionarySearchModuleTestUI>() {
					@Override
					public void onSuccess(DictionarySearchModuleTestUI result) {
						asyncCallback.onSuccess(getAsyncTestItemResult());
					}
				});
			}

			@Override
			public String getDescription() {
				return "execute";
			}
		});
	}

	public void getResultList(final BaseErrorAsyncCallback<List<ITableRow<VOType>>> resultListAsyncCallback) {
		this.getAsyncTestItems().add(new AsyncTestItem() {

			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				resultListAsyncCallback.onSuccess(getAsyncTestItemResult().getDictionarySearch().getDictionaryResult().getRows());
			}

			@Override
			public String getDescription() {
				return "getResultList";
			}

		});
	}
}
