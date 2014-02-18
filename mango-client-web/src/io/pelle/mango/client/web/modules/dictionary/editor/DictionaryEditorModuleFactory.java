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
package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;
import io.pelle.mango.client.web.module.BaseModuleFactory;
import io.pelle.mango.client.web.module.ModuleHandler;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DictionaryEditorModuleFactory extends BaseModuleFactory
{

	@Override
	public boolean supports(String moduleUrl)
	{
		return ModuleUtils.urlContainsModuleId(moduleUrl, DictionaryEditorModule.MODULE_ID);
	}

	public static void openEditorForId(String dictionaryName, long voId)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName, voId));
	}

	public static void openEditorForId(String dictionaryName, long voId, AsyncCallback<IDictionaryEditor<?>> callback)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName, voId));
	}

	public static void openEditor(String dictionaryName)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName), null, new HashMap<String, Object>(), null);
	}

	public static void openEditor(String dictionaryName, Map<String, Object> parameters)
	{
		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(dictionaryName), parameters);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getNewInstance(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		new DictionaryEditorModule(moduleUrl, moduleCallback, parameters);
	}

}
