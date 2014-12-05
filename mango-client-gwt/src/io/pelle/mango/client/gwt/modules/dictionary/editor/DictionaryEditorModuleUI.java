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
package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.util.HumanizedMessagePopup;
import io.pelle.mango.client.base.util.HumanizedMessagePopup.MESSAGE_TYPE;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.ActionBar;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.DictionaryEditorPanel;
import io.pelle.mango.client.gwt.utils.HtmlWithHelp;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionaryEditorModule<VOType>> implements IEditorUpdateListener {

	private final VerticalPanel verticalPanel;

	private static final String DICTIONARY_SAVE_BUTTON_DEBUG_ID = "DictionarySaveButton";

	private static final String DICTIONARY_BACK_BUTTON_DEBUG_ID = "DictionaryBackButton";

	private static final String DICTIONARY_REFRESH_BUTTON_DEBUG_ID = "DictionaryRefreshButton";

	private static final String DICTIONARY_INFO_BUTTON_DEBUG_ID = "DictionaryInfoButton";

	private static final String DICTIONARY_LOG_BUTTON_DEBUG_ID = "DictionaryLogButton";

	private final HtmlWithHelp editorTitle;

	@SuppressWarnings("rawtypes")
	public DictionaryEditorModuleUI(DictionaryEditorModule<VOType> module, final Optional<IModuleUI> previousModuleUI) {
		super(module, DictionaryEditorModule.EDITOR_UI_MODULE_ID);

		verticalPanel = new VerticalPanel();

		verticalPanel.addStyleName(GwtStyles.DEBUG_BORDER);
		verticalPanel.setWidth("100%");

		// - action panel ------------------------------------------------------
		ActionBar actionBar = new ActionBar();
		verticalPanel.add(actionBar);

		// - title -------------------------------------------------------------
		editorTitle = new HtmlWithHelp(module.getTitle(), module.getHelpText());
		editorTitle.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(editorTitle);

		DictionaryEditorPanel<VOType> dictionaryEditorPanel = new DictionaryEditorPanel<VOType>(getModule());
		verticalPanel.add(dictionaryEditorPanel);

		if (previousModuleUI.isPresent()) {

			actionBar.addSingleButton(MangoClientWeb.RESOURCES.back(), MangoClientWeb.MESSAGES.editorBack(), new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					ModuleHandler.getInstance().closeCurrentAndShow(previousModuleUI.get());
				}
			}, DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-" + DICTIONARY_BACK_BUTTON_DEBUG_ID);

		}

		actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.editorSave(), MangoClientWeb.MESSAGES.editorSave(), new ClickHandler() {
			/** {@inheritDoc} */
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		}, DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-" + DICTIONARY_SAVE_BUTTON_DEBUG_ID);

		final Button refreshButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.editorRefresh(),
				MangoClientWeb.MESSAGES.editorRefresh(), new ClickHandler() {
					/** {@inheritDoc} */
					@Override
					public void onClick(ClickEvent event) {
						getModule().getDictionaryEditor().refresh();
					}

				}, DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-" + DICTIONARY_REFRESH_BUTTON_DEBUG_ID);
		refreshButton.setEnabled(false);

		if (getModule().getDictionaryEditor().getMetaInformation().isPresent()) {
			// info popup panel
			final MetaInformationPopupPanel infoPopupPanel = new MetaInformationPopupPanel(getModule().getDictionaryEditor());
			infoPopupPanel.setAutoHideEnabled(true);

			final Button infoButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.dictionaryInfo(),
					MangoClientWeb.MESSAGES.dictionaryInfo(), DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-"
							+ DICTIONARY_INFO_BUTTON_DEBUG_ID);
			infoButton.addClickHandler(new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					infoPopupPanel.showRelativeTo(infoButton);
				}
			});
		}

		if (getModule().isLogDisplayEnabled()) {
			
			final LogPopupPanel logPopupPanel = new LogPopupPanel(getModule().getDictionaryEditor());
			logPopupPanel.setAutoHideEnabled(true);

			final Button logButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.log(), MangoClientWeb.MESSAGES.log(),
					DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-" + DICTIONARY_LOG_BUTTON_DEBUG_ID);
			logButton.addClickHandler(new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					logPopupPanel.showRelativeTo(logButton);
				}
			});
		}

		for (final IButton button : getModule().getEditorButtons()) {
			actionBar.addSingleButton(button);

		}

		getModule().addUpdateListener(this);
	}

	@Override
	public void onUpdate() {
		editorTitle.setText(getModule().getTitle());
	}

	private void save() {
		if (getModule().getDictionaryEditor().getValidationMessages().hasErrors()) {
			HumanizedMessagePopup.showMessageAndFadeAfterMouseMove(MangoClientWeb.MESSAGES.editorContainsErrors(), MESSAGE_TYPE.ERROR);
		} else {
			getModule().getDictionaryEditor().save();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean close() {

		if (getModule().getDictionaryEditor().isDirty()) {
			return Window.confirm(MangoClientWeb.MESSAGES.editorClose());
		} else {
			return true;
		}
	}

	@Override
	public boolean contributesToBreadCrumbs() {
		return true;
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return super.isInstanceOf(moduleUrl)
				&& Objects.equal(getModule().getEditorDictionaryName(),
						ModuleUtils.getUrlParameter(moduleUrl, DictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID));
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

}
