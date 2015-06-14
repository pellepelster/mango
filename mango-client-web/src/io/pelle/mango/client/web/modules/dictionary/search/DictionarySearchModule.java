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
package io.pelle.mango.client.web.modules.dictionary.search;

import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.modules.BaseDictionarySearchModule;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.DictionaryElementUtil;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Dictionary search module
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class DictionarySearchModule<VOType extends IBaseVO> extends BaseDictionarySearchModule implements IBaseDictionaryModule {

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public static final String SEARCH_UI_MODULE_ID = MODULE_ID;

	public static final String SEARCH_QUERY_UI_MODULE_ID = MODULE_ID + "Query";

	public static final String SEARCH_QUERY_UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(SEARCH_QUERY_UI_MODULE_ID);

	public static final String SEARCH_QUERY_RESULT_UI_MODULE_ID = SEARCH_QUERY_UI_MODULE_ID + "Result";

	public static final String getSearchModuleLocator(String dictionaryName) {
		return ModuleUtils.getBaseUIModuleUrl(SEARCH_UI_MODULE_ID) + "&" + SEARCHDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName;
	}

	public static final String getSearchQueryResultModuleLocator(String searchText) {
		return ModuleUtils.getBaseUIModuleUrl(SEARCH_QUERY_RESULT_UI_MODULE_ID) + "&" + SEARCHTEXT_PARAMETER_ID + "=" + searchText;
	}

	private IDictionaryModel dictionaryModel;

	private DictionarySearch<VOType> dictionarySearch;

	private String title = MangoClientWeb.MESSAGES.dictionarySearch();

	public DictionarySearchModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {

		super(moduleUrl, moduleCallback, parameters);

		if (hasParameter(SEARCHDICTIONARYNAME_PARAMETER_ID)) {
			init(getSearchDictionaryName());
		} else {
			moduleCallback.onSuccess(this);
		}
	}

	private void init(String dictionaryName) {
		DictionarySearchModule.this.dictionaryModel = DictionaryModelProvider.getDictionary(dictionaryName);
		DictionarySearchModule.this.dictionarySearch = new DictionarySearch<VOType>(this.dictionaryModel);

		getModuleCallback().onSuccess(DictionarySearchModule.this);

	}

	@Override
	public String getTitle() {
		if (this.dictionarySearch != null) {
			return this.dictionarySearch.getTitle();
		} else {
			return this.title;
		}
	}

	public void search(String query, AsyncCallback<List<io.pelle.mango.client.search.SearchResultItem>> callback) {
		this.title = MangoClientWeb.MESSAGES.dictionarySearchResults(query);
		MangoClientWeb.getInstance().getRemoteServiceLocator().getSearchService().search(null, query, callback);
	}

	public DictionarySearch<VOType> getDictionarySearch() {
		return this.dictionarySearch;
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		if (!MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl))) {
			return false;
		}

		if (hasParameter(SEARCHDICTIONARYNAME_PARAMETER_ID) || ModuleUtils.hasUrlParameter(moduleUrl, SEARCHDICTIONARYNAME_PARAMETER_ID)) {
			return Objects.equal(getSearchDictionaryName(), ModuleUtils.getUrlParameter(moduleUrl, SEARCHDICTIONARYNAME_PARAMETER_ID));
		}

		return true;
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel) {
		return DictionaryElementUtil.getElement(this.dictionarySearch.getActiveFilter(), baseModel);
	}

	public IDictionaryModel getDictionaryModel() {
		return this.dictionaryModel;
	}

	@Override
	public String getHelpText() {
		return getDictionaryModel().getSearchModel().getHelpText();
	}

	public boolean isCreateEnabled() {
		return dictionaryModel.getSearchModel().isCreateEnabled();
	}
}
