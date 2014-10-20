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

import org.junit.Assert;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.test.util.FocusManager;

import com.google.common.base.Optional;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleTestUI<VOType extends IBaseVO> extends BaseDictionaryModuleTestUI implements IModuleUI<Object, DictionaryEditorModule<VOType>>, IEditorUpdateListener {

	private DictionaryEditorModule<VOType> module;

	private boolean dirty = false;
	private String title;;
	
	public DictionaryEditorModuleTestUI(DictionaryEditorModule<VOType> module) {
		super(module, DictionaryEditorModule.EDITOR_UI_MODULE_ID);
		this.module = module;
		module.addUpdateListener(this);
		onUpdate();
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

	public void assertTitle(String expectedTitle) {
		Assert.assertEquals(expectedTitle, title);
	}

	public void save() {
		
		FocusManager.getInstance().setCurrentWidget(this);

		AsyncCallbackFuture<Result<VOType>> future = AsyncCallbackFuture.create();
		this.module.getDictionaryEditor().save(Optional.of(future.getCallback()));
		future.get();
	}

	@Override
	public void onUpdate() {
		this.dirty = module.isDirty();
		this.title = module.getTitle();
	}

	@Override
	public int getOrder() {
		return this.module.getOrder();
	}

	public void assertNotDirty() {
		Assert.assertFalse(dirty);
	}

	public void assertDirty() {
		Assert.assertTrue(dirty);
	}

	@Override
	public String getTitle() {
		return title;
	}

}
