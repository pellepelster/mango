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

import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.ActionBar;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.DictionaryFilterPanel;
import io.pelle.mango.client.gwt.modules.dictionary.DictionaryResultPanel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.modules.dictionary.search.ISearchUpdateListener;

import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionarySearchModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionarySearchModule<VOType>> implements ISearchUpdateListener {

	private static final String DICTIONARY_CREATE_BUTTON_DEBUG_ID = "DictionaryCreateButton";

	private static final String DICTIONARY_SEARCH_BUTTON_DEBUG_ID = "DictionarySearchButton";

	private final VerticalPanel verticalPanel;

	private final HTML searchTitle;

	/**
	 * @param module
	 */
	public DictionarySearchModuleUI(final DictionarySearchModule<VOType> module) {
		super(module, DictionarySearchModule.SEARCH_UI_MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName(GwtStyles.DEBUG_BORDER);
		verticalPanel.ensureDebugId(DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName());
		verticalPanel.setWidth("100%");

		// - action panel ------------------------------------------------------
		ActionBar actionBar = new ActionBar();
		verticalPanel.add(actionBar);

		// - title -------------------------------------------------------------
		searchTitle = new HTML(module.getTitle());
		searchTitle.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(searchTitle);

		// searchModel.getResultModel()
		final DictionaryResultPanel<VOType> dictionaryResultPanel = new DictionaryResultPanel<VOType>(getModule().getDictionaryModel(), getModule().getDictionarySearch().getDictionaryResult());

		if (getModule().getDictionarySearch().hasFilter()) {
			// searchModel.getFilterModel().get(0)
			final DictionaryFilterPanel<VOType> dictionaryFilter = new DictionaryFilterPanel<VOType>(getModule().getDictionarySearch().getActiveFilter(), (Class<VOType>) module.getDictionaryModel().getVOClass());

			// dictionaryFilter.addStyleName(GwtStyles.VERTICAL_SPACING);
			verticalPanel.add(dictionaryFilter);

			actionBar.addToButtonGroup(module.getModuleUrl(), MangoClientWeb.RESOURCES.searchSearch(), MangoClientWeb.MESSAGES.searchSearch(), new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					module.getDictionarySearch().search();
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);
		} else {
			actionBar.addToButtonGroup(module.getModuleUrl(), MangoClientWeb.RESOURCES.searchSearch(), MangoClientWeb.MESSAGES.searchSearch(), new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					module.getDictionarySearch().search();
				}
			}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_SEARCH_BUTTON_DEBUG_ID);

		}

		actionBar.addToButtonGroup(module.getModuleUrl(), MangoClientWeb.RESOURCES.dictionaryCreate(), MangoClientWeb.MESSAGES.dictionaryCreate(), new ClickHandler() {
			/** {@inheritDoc} */
			@Override
			public void onClick(ClickEvent event) {
				DictionaryEditorModuleFactory.openEditor(getModule().getDictionaryModel().getName());
			}
		}, DictionarySearchModule.MODULE_ID + "-" + module.getDictionaryModel().getName() + "-" + DICTIONARY_CREATE_BUTTON_DEBUG_ID);

		dictionaryResultPanel.addStyleName(GwtStyles.TOP_SPACER);
		dictionaryResultPanel.setWidth("100%");
		verticalPanel.add(dictionaryResultPanel);

		getModule().getDictionarySearch().addUpdateListener(this);
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
	public boolean isInstanceOf(String moduleUrl) {
		return super.isInstanceOf(moduleUrl) && Objects.equal(getModule().getSearchDictionaryName(), ModuleUtils.getUrlParameter(moduleUrl, DictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID));
	}

	@Override
	public void onUpdate() {
		searchTitle.setText(getModule().getTitle());
	}
}
