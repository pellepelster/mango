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

import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.Panel;

public class DictionarySearchQueryModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements ISearchUpdateListener, IGwtModuleUI<DictionarySearchModule<VOType>> {

	public static final String DICTIONARY_SEARCH_INPUT_PANEL_STYLE = "dictionarySearchInputPanel";

	private final Div container;

	public DictionarySearchQueryModuleUI(final DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_QUERY_UI_MODULE_ID);

		container = new Div();
		container.addStyleName(DICTIONARY_SEARCH_INPUT_PANEL_STYLE);
		container.setWidth("100%");

		final TextBox searchTextBox = new TextBox();
		container.add(searchTextBox);
		searchTextBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				ModuleHandler.getInstance().startUIModule(DictionarySearchModule.getSearchQueryResultModuleLocator(searchTextBox.getText()), Direction.CENTER.toString());
			}
		});
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
	}
}
