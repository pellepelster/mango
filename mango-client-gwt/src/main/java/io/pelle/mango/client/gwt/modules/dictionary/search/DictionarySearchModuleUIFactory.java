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
package io.pelle.mango.client.gwt.modules.dictionary.search;

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.gwt.modules.IGwtModuleUI;
import io.pelle.mango.client.web.module.BaseModuleUIFactory;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

public class DictionarySearchModuleUIFactory extends BaseModuleUIFactory<Panel, IGwtModuleUI<DictionarySearchModule<?>>> {

	public DictionarySearchModuleUIFactory() {
		super(new String[] { DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID, DictionarySearchModule.SEARCH_UI_MODULE_ID });
	}

	@Override
	public void getNewInstance(final String moduleUrl, final AsyncCallback<IGwtModuleUI<DictionarySearchModule<?>>> moduleCallback, Map<String, Object> parameters, Optional<IModuleUI<?, ?>> previousModuleUI) {
		ModuleHandler.getInstance().startModule(ModuleUtils.concatenate(moduleUrl, DictionarySearchModule.MODULE_LOCATOR), parameters, new BaseErrorAsyncCallback<IModule>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onSuccess(IModule result) {

				if (supports(moduleUrl, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID)) {
					moduleCallback.onSuccess(new DictionarySearchQueryModuleUI((DictionarySearchModule) result));
				} else if (supports(moduleUrl, DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID)) {
					moduleCallback.onSuccess(new DictionarySearchResultModuleUI((DictionarySearchModule) result));
				} else if (supports(moduleUrl, DictionarySearchModule.SEARCH_UI_MODULE_ID)) {
					moduleCallback.onSuccess(new DictionarySearchModuleUI((DictionarySearchModule) result));
				}
			}
		});
	}
}
