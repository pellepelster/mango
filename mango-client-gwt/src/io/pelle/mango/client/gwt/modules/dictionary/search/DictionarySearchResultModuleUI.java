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

import io.pelle.mango.client.base.module.IModule.IModuleUpdateListener;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.query.DictionaryModelQuery;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.gwt.utils.HtmlUtils;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.common.base.Optional;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

public class DictionarySearchResultModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements
		IModuleUpdateListener {

	private static final String DICTIONARY_SEARCH_RESULT_PANEL_STYLE = "dictionarySearchResultPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE = "dictionarySearchResultItemPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE = "dictionarySearchResultItemTitle";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE = "dictionarySearchResultItemText";

	private final Div container;

	private Div resultContainer;

	/**
	 * @param module
	 */
	public DictionarySearchResultModuleUI(final DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID);

		container = new Div();

		HTML title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		container.add(title);
		container.setWidth("100%");

		resultContainer = new Div();
		container.add(resultContainer);
		resultContainer.addStyleName(DICTIONARY_SEARCH_RESULT_PANEL_STYLE);

		module.addUpdateListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return container;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

	@Override
	public void onUpdate() {

		getModule().search(getModule().getSearchText(), new BaseErrorAsyncCallback<List<SearchResultItem>>() {

			@Override
			public void onSuccess(List<SearchResultItem> result) {

				for (int i = 0; i < resultContainer.getWidgetCount(); i++) {
					resultContainer.remove(i);
				}

				if (result.size() == 0) {
					Div noSearchResults = new Div();
					noSearchResults.add(new HTML(MangoClientWeb.MESSAGES.noSearchResults()));
					resultContainer.add(noSearchResults);
				} else {
					for (SearchResultItem searchResultItem : result) {

						IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(searchResultItem.getDictionaryId());

						Div resultItemContainer = new Div();
						resultItemContainer.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE);

						HTML title = new HTML(DictionaryUtil.getLabel(dictionaryModel) + ": " + searchResultItem.getLabel());
						title.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE);
						resultItemContainer.add(title);

						SafeHtmlBuilder sb = new SafeHtmlBuilder();
						boolean first = true;
						
						for (Map.Entry<String, String> attributeEntry : searchResultItem.getAttributes().entrySet()) {
							
							Optional<IBaseControlModel> baseControlModel = DictionaryModelQuery.create(dictionaryModel).getControls()
									.getControlModelByAttributePath(attributeEntry.getKey());

							if (baseControlModel.isPresent()) {

								if (!first) {
									sb.appendHtmlConstant(", ");
								}
								first = false;

								sb.appendHtmlConstant(DictionaryModelUtil.getColumnLabel(baseControlModel.get()));
								sb.appendHtmlConstant(": ");
								HtmlUtils.highlightTexts(getModule().getSearchText(), attributeEntry.getValue(), sb);
							}
						}

						HTML text = new HTML(sb.toSafeHtml());
						text.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE);
						resultItemContainer.add(text);

						resultContainer.add(resultItemContainer);
					}
				}
			}
		});
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return this.getUiModuleId().equals(ModuleUtils.getUIModuleId(moduleUrl));
	}

}
