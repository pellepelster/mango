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
package io.pelle.mango.client.gwt.modules.dictionary;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.controls.IButtonUpdateHandler;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.utils.MangoButton;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.ButtonToolBar;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;

/**
 * Gmail style button bar
 * 
 * @author pelle
 * 
 */
public class ActionBar extends ButtonGroup {

	private final ButtonToolBar buttonToolbar = new ButtonToolBar();

	private final Map<String, ButtonGroup> buttonBars = new HashMap<String, ButtonGroup>();

	public ActionBar() {
		setWidth("100%");
		addStyleName(GwtStyles.DEBUG_BORDER);
		add(buttonToolbar);

	}

	private MangoButton addButton(String buttonGroupName, ImageResource imageResource, String title, ClickHandler clickHandler, String debugId) {
		if (!buttonBars.containsKey(buttonGroupName)) {
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonBars.put(buttonGroupName, buttonGroup);
			buttonToolbar.add(buttonGroup);
		}

		MangoButton button = new MangoButton();
		button.setResource(imageResource);
		button.setTitle(title);

		buttonBars.get(buttonGroupName).add(button);

		if (clickHandler != null) {
			button.addClickHandler(clickHandler);
		}
		button.ensureDebugId(debugId);

		return button;
	}

	private MangoButton addButton(String buttonGroupName, ImageResource imageResource, String title, String debugId, final IButton button, final DictionaryContext dictionaryContext) {
		return addButton(buttonGroupName, imageResource, title, new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				button.onClick(event, dictionaryContext);
			}
		}, debugId);
	}

	public MangoButton addToButtonGroup(String buttonGroupName, ImageResource imageResource, String title, ClickHandler clickHandler, String debugId) {
		return addButton(buttonGroupName, imageResource, title, clickHandler, debugId);
	}

	public MangoButton addToButtonGroup(String buttonGroupName, ImageResource imageResource, String title, String debugId) {
		return addButton(buttonGroupName, imageResource, title, null, debugId);
	}

	public MangoButton addSingleButton(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId) {
		return addButton(UUID.uuid(), imageResource, title, clickHandler, debugId);
	}

	public MangoButton addSingleButton(final IButton button, DictionaryContext dictionaryContext) {

		final MangoButton uiButton = addButton(UUID.uuid(), button.getImage(), button.getTitle(), button.getId(), button, dictionaryContext);

		button.addUpdatehandler(new IButtonUpdateHandler() {

			@Override
			public void onUpdate() {
				uiButton.setEnabled(button.isEnabled());
			}
		});

		return uiButton;
	}

}
