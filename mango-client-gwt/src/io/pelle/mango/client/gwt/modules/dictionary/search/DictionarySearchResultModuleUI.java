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
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DictionarySearchResultModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements IModuleUpdateListener {
	
	private static final String DICTIONARY_SEARCH_RESULT_PANEL_STYLE = "dictionarySearchResultPanel";

//	private static final String DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE = "dictionarySearchResultItemPanel";
//
//	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE = "dictionarySearchResultItemTitle";
//
//	private static final String DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE = "dictionarySearchResultItemText";

	private final VerticalPanel verticalPanel;

	private VerticalPanel resultPanel;

	/**
	 * @param module
	 */
	public DictionarySearchResultModuleUI(final DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_QUERY_RESULT_UI_MODULE_ID);

		verticalPanel = new VerticalPanel();

		HTML title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(title);
		verticalPanel.setWidth("100%");

		resultPanel = new VerticalPanel();
		verticalPanel.add(resultPanel);
		resultPanel.addStyleName(DICTIONARY_SEARCH_RESULT_PANEL_STYLE);
		resultPanel.setWidth("100%");

		module.addUpdateListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return verticalPanel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

	@Override
	public void onUpdate() {
		throw new RuntimeException("not implemented");
		/*
		 * getModule().search(queryString, new
		 * BaseErrorAsyncCallback<List<SearchResultItemVO>>() {
		 * 
		 * @Override public void onSuccess(List<SearchResultItemVO> result) {
		 * for (int i = 0; i < resultPanel.getWidgetCount(); i++) {
		 * resultPanel.remove(i); }
		 * 
		 * for (SearchResultItemVO searchResultItem : result) { VerticalPanel
		 * resultItemPanel = new VerticalPanel();
		 * resultItemPanel.setWidth("100%");
		 * resultItemPanel.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_PANEL_STYLE
		 * );
		 * 
		 * HTML title = new HTML(searchResultItem.getTitle());
		 * title.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TITLE_STYLE);
		 * resultItemPanel.add(title);
		 * 
		 * HTML text = new HTML(searchResultItem.getText());
		 * text.addStyleName(DICTIONARY_SEARCH_RESULT_ITEM_TEXT_STYLE);
		 * resultItemPanel.add(text);
		 * 
		 * resultPanel.add(resultItemPanel); }
		 * 
		 * } });
		 */
	}
}
