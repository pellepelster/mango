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

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.ActionBar;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDictionaryModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.DictionaryEditorPanel;
import io.pelle.mango.client.gwt.utils.HtmlWithHelp;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.gwt.commons.toastr.Toastr;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleUI<VOType extends IBaseVO> extends BaseDictionaryModuleUI<DictionaryEditorModule<VOType>> implements IEditorUpdateListener {

	private final Div container;

	private static final String DICTIONARY_SAVE_BUTTON_DEBUG_ID = "DictionarySaveButton";

	private static final String DICTIONARY_BACK_BUTTON_DEBUG_ID = "DictionaryBackButton";

	private static final String DICTIONARY_REFRESH_BUTTON_DEBUG_ID = "DictionaryRefreshButton";

	private static final String DICTIONARY_INFO_BUTTON_DEBUG_ID = "DictionaryInfoButton";

	private static final String DICTIONARY_LOG_BUTTON_DEBUG_ID = "DictionaryLogButton";

	private final HtmlWithHelp editorTitle;

	@SuppressWarnings("rawtypes")
	public DictionaryEditorModuleUI(DictionaryEditorModule<VOType> module, final Optional<IModuleUI> previousModuleUI) {
		super(module, DictionaryEditorModule.EDITOR_UI_MODULE_ID);

		container = new Div();

		container.addStyleName(GwtStyles.DEBUG_BORDER);
		container.setWidth("100%");

		// - action panel ------------------------------------------------------
		ActionBar actionBar = new ActionBar();
		container.add(actionBar);

		// - title -------------------------------------------------------------
		editorTitle = new HtmlWithHelp(module.getTitle(), module.getHelpText());
		editorTitle.addStyleName(GwtStyles.TITLE);
		container.add(editorTitle);

		DictionaryEditorPanel<VOType> dictionaryEditorPanel = new DictionaryEditorPanel<VOType>(getModule());
		container.add(dictionaryEditorPanel);

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

		final Button refreshButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.editorRefresh(), MangoClientWeb.MESSAGES.editorRefresh(), new ClickHandler() {
			/** {@inheritDoc} */
			@Override
			public void onClick(ClickEvent event) {
				getModule().getDictionaryEditor().refresh();
			}

		}, DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName() + "-" + DICTIONARY_REFRESH_BUTTON_DEBUG_ID);
		refreshButton.setEnabled(false);

		if (getModule().getDictionaryEditor().getMetaInformation().isPresent()) {
			// info popup panel
			final MetaInformationPopupPanel infoPopupPanel = new MetaInformationPopupPanel(getModule().getDictionaryEditor(), getModule().getDictionaryModel());
			infoPopupPanel.setAutoHideEnabled(true);

			final Button infoButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.dictionaryInfo(), MangoClientWeb.MESSAGES.dictionaryInfo(), DictionaryEditorModule.MODULE_ID + "-"
					+ getModule().getDictionaryModel().getName() + "-" + DICTIONARY_INFO_BUTTON_DEBUG_ID);
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

			final Button logButton = actionBar.addToButtonGroup(getModule().getModuleUrl(), MangoClientWeb.RESOURCES.log(), MangoClientWeb.MESSAGES.log(), DictionaryEditorModule.MODULE_ID + "-" + getModule().getDictionaryModel().getName()
					+ "-" + DICTIONARY_LOG_BUTTON_DEBUG_ID);
			logButton.addClickHandler(new ClickHandler() {
				/** {@inheritDoc} */
				@Override
				public void onClick(ClickEvent event) {
					logPopupPanel.showRelativeTo(logButton);
				}
			});
		}

		for (final IButton button : getModule().getEditorButtons()) {
			actionBar.addSingleButton(button, new DictionaryContext(getModule().getDictionaryEditor()));

		}

		getModule().addUpdateListener(this);
	}

	@Override
	public void onUpdate() {
		editorTitle.setText(getModule().getTitle());
	}

	private void save() {
		getModule().getDictionaryEditor().save(new BaseErrorAsyncCallback<Result<VOType>>() {
			@Override
			public void onSuccess(Result<VOType> result) {
				if (getModule().getDictionaryEditor().getValidationMessages().hasErrors()) {
					Toastr.error(MangoClientWeb.MESSAGES.editorContainsErrors());
				}
			}
		});

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

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return container;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
