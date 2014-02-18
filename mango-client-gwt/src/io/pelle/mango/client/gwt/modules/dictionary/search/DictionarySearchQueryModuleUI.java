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

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.IGwtModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.modules.dictionary.search.ISearchUpdateListener;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DictionarySearchQueryModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements ISearchUpdateListener, IGwtModuleUI<DictionarySearchModule<VOType>> {

	public static final String DICTIONARY_SEARCH_INPUT_PANEL_STYLE = "dictionarySearchInputPanel";

	private final VerticalPanel verticalPanel;

	public DictionarySearchQueryModuleUI(final DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName(DICTIONARY_SEARCH_INPUT_PANEL_STYLE);

		HorizontalPanel searchTextPanel = new HorizontalPanel();
		verticalPanel.add(searchTextPanel);

		final TextBox searchTextBox = new TextBox();
		searchTextBox.setWidth("95%");
		searchTextPanel.add(searchTextBox);
		searchTextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				ModuleHandler.getInstance().startUIModule(DictionarySearchModule.getSearchQueryResultModuleLocator(searchTextBox.getText()), Direction.CENTER.toString());
			}
		});

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
	}
}
