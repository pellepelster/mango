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
package io.pelle.mango.client.web.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.test.util.FocusManager;

import java.util.List;

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

	@Override
	public int getOrder() {
		return this.module.getOrder();
	}

	public void execute() {
		FocusManager.getInstance().setCurrentWidget(this);
		AsyncCallbackFuture<List<IBaseTable.ITableRow<VOType>>> future = AsyncCallbackFuture.create();
		this.module.getDictionarySearch().search(future.getCallback());
		future.get();
	}

	public void assertSearchResults(int rowCount) {
		assertEquals(rowCount, this.module.getDictionarySearch().getDictionaryResult().getRows().size());
	}

	public ITableRow<VOType> getResultRow(int row) {
		return this.module.getDictionarySearch().getDictionaryResult().getRows().get(row);
	}

	public DictionaryEditorModuleTestUI<VOType> openEditor(int row) {
		long id = getResultRow(row).getVO().getId();
		return MangoClientSyncWebTest.getInstance().openEditor(module.getDictionaryModel(), id);
	}

}
