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
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.List;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DictionarySearchResultModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements IModuleUpdateListener {

	private static final String DICTIONARY_SEARCH_RESULT_PANEL_STYLE = "dictionarySearchResultPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE = "dictionarySearchResultItemPanel";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE = "dictionarySearchResultItemTitle";

	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE = "dictionarySearchResultItemText";

	private final Div container;

	private VerticalPanel resultPanel;

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

		resultPanel = new VerticalPanel();
		container.add(resultPanel);
		resultPanel.addStyleName(DICTIONARY_SEARCH_RESULT_PANEL_STYLE);
		resultPanel.setWidth("100%");

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
				for (int i = 0; i < resultPanel.getWidgetCount(); i++) {
					resultPanel.remove(i);
				}

				for (SearchResultItem searchResultItem : result) {
					Div resultItemContainer = new Div();
					resultItemContainer.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE);

					HTML title = new HTML(searchResultItem.getDictionaryId());
					title.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE);
					resultItemContainer.add(title);

					HTML text = new HTML(Long.toString(searchResultItem.getId()));
					text.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE);
					resultItemContainer.add(text);

					resultPanel.add(resultItemContainer);
				}

			}
		});

	}
}
