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

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.web.module.BaseModuleUIFactory;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public class DictionaryEditorModuleTestUIFactory extends BaseModuleUIFactory<Object, DictionaryEditorModuleTestUI> {

	public DictionaryEditorModuleTestUIFactory() {
		super(new String[] { DictionaryEditorModule.EDITOR_UI_MODULE_ID });
	}

	@Override
	public void getNewInstance(final String moduleUrl, final AsyncCallback<DictionaryEditorModuleTestUI> moduleCallback, Map<String, Object> parameters, final Optional<IModuleUI<?, ?>> previousModuleUI) {
		ModuleHandler.getInstance().startModule(ModuleUtils.concatenate(moduleUrl, DictionaryEditorModule.MODULE_LOCATOR), parameters, new BaseErrorAsyncCallback<IModule>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(IModule result) {
				if (supports(moduleUrl, DictionaryEditorModule.MODULE_ID)) {
					moduleCallback.onSuccess(new DictionaryEditorModuleTestUI((DictionaryEditorModule) result));
				}
			}
		});
	}

}
