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

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearch;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.test.modules.dictionary.container.TableRowTest;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.List;

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchModuleTestUI<VOType extends IBaseVO> extends BaseDictionaryModuleTestUI implements IModuleUI<Object, DictionarySearchModule<VOType>> {

	private DictionarySearchModule<VOType> module;

	public DictionarySearchModuleTestUI(DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_UI_MODULE_ID);
		this.module = module;
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs() {
		return false;
	}

	@Override
	public Object getContainer() {
		return null;
	}

	@Override
	public DictionarySearchModule<VOType> getModule() {
		return this.module;
	}

	@Override
	public String getTitle() {
		return this.module.getTitle();
	}

	public DictionarySearch<VOType> getDictionarySearch() {
		return this.module.getDictionarySearch();
	}

	public void search(final AsyncCallback<DictionarySearchModuleTestUI<VOType>> asyncCallback) {
		this.module.getDictionarySearch().search(new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>() {
			@Override
			public void onSuccess(List<IBaseTable.ITableRow<VOType>> result) {
				asyncCallback.onSuccess(DictionarySearchModuleTestUI.this);
			}
		});

	}

	public TableRowTest<VOType> getResultTableRow(int rowIndex) {
		return new TableRowTest<VOType>(this.module.getDictionarySearch().getDictionaryResult().getTableRow(rowIndex));
	}

	public void assertTitle(String expectedTitle) {
		Assert.assertEquals(expectedTitle, this.module.getTitle());
	}

	public void assertResultCount(int expectedResultCount) {
		Assert.assertEquals(expectedResultCount, this.module.getDictionarySearch().getDictionaryResult().getRows().size());
	}

	@Override
	public int getOrder() {
		return this.module.getOrder();
	}

}
