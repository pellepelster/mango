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
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.controls.IButtonUpdateHandler;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.widgets.ImageButton;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Gmail style button bar
 * 
 * @author pelle
 * 
 */
public class ActionBar extends HorizontalPanel
{

	private final FlowPanel buttonToolbar = new FlowPanel();

	private final Map<String, FlowPanel> buttonBars = new HashMap<String, FlowPanel>();

	public ActionBar()
	{
		setWidth("100%");
		setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		// addStyleName(GwtStyles.SEPARATOR_BORDER_BOTTOM);
		// addStyleName(GwtStyles.SEPARATOR_BORDER_TOP);
		addStyleName(GwtStyles.DEBUG_BORDER);
		// addStyleName(GwtStyles.VERTICAL_SPACING);
		buttonToolbar.addStyleName(GwtStyles.BUTTON_TOOLBAR);
		add(buttonToolbar);
	}

	private Button addButton(String buttonGroupName, ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		if (!buttonBars.containsKey(buttonGroupName))
		{
			FlowPanel buttonGroup = new FlowPanel();
			buttonGroup.addStyleName(GwtStyles.BUTTON_GROUP);
			buttonBars.put(buttonGroupName, buttonGroup);
			buttonToolbar.add(buttonGroup);
		}

		ImageButton button = new ImageButton();
		button.addStyleName(GwtStyles.BUTTON);
		button.addStyleName(GwtStyles.BUTTON_DEFAULT);

		button.setResource(imageResource);
		button.setTitle(title);
		buttonBars.get(buttonGroupName).add(button);

		if (clickHandler != null)
		{
			button.addClickHandler(clickHandler);
		}
		button.ensureDebugId(debugId);

		return button;
	}

	public Button addToButtonGroup(String buttonGroupName, ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(buttonGroupName, imageResource, title, clickHandler, debugId);
	}

	public Button addSingleButton(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(UUID.uuid(), imageResource, title, clickHandler, debugId);
	}

	public Button addSingleButton(final IButton button)
	{
		final Button uiButton = addButton(UUID.uuid(), button.getImage(), button.getTitle(), button, button.getDebugId());

		button.addUpdatehandler(new IButtonUpdateHandler()
		{

			@Override
			public void onUpdate()
			{
				uiButton.setEnabled(button.isEnabled());
			}
		});

		return uiButton;
	}

	public Button addSingleButton(ImageResource imageResource, String title, String debugId)
	{
		return addButton(UUID.uuid(), imageResource, title, null, debugId);
	}

}
