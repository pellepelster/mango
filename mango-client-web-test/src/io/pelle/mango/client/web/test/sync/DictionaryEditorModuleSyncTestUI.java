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
package io.pelle.mango.client.web.test.sync;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.containers.EditableTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.test.modules.dictionary.container.EditableTableTest;

import com.google.common.base.Optional;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleSyncTestUI<VOType extends IBaseVO> extends BaseDictionaryModuleSyncTestUI implements IModuleUI<Object, DictionaryEditorModule<VOType>>, IEditorUpdateListener {

	private DictionaryEditorModule<VOType> module;

	public DictionaryEditorModuleSyncTestUI(DictionaryEditorModule<VOType> module) {
		super(module, DictionaryEditorModule.EDITOR_UI_MODULE_ID);
		this.module = module;
		module.addUpdateListener(this);
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
	public DictionaryEditorModule<VOType> getModule() {
		return this.module;
	}

	@Override
	public String getTitle() {
		return this.module.getTitle();
	}

	public <TableVOType extends IBaseVO> EditableTableTest<TableVOType> getEditableTableTest(EditableTableModel<TableVOType> tableModel) {
		return new EditableTableTest<TableVOType>(this.module.getElement(tableModel));
	}

	public void save() {
		AsyncCallbackFuture<Result<VOType>> future = AsyncCallbackFuture.create();
		this.module.getDictionaryEditor().save(Optional.of(future.getCallback()));
		future.get();
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public int getOrder() {
		return this.module.getOrder();
	}

}
