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
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.utils.FadeAnimation;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.TextControl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class GwtTextControl extends Composite implements IGwtControl {

	private SimplePanel parent;

	private Label helpSpan;

	private PopupPanel helpPopup;

	private TextBox textBox;

	public GwtTextControl(TextControl textControl) {
		super();

		parent = new SimplePanel();
		parent.addStyleName(GwtStyles.CONTROL_HAS_FEEDBACK_STYLE);

		textBox = new TextBox();
		new ControlHelper(textBox, textControl, this, true);
		textBox.ensureDebugId(DictionaryModelUtil.getDebugId(textControl.getModel()));
		textBox.setMaxLength(textControl.getModel().getMaxLength());

		parent.getElement().appendChild(textBox.getElement());

		helpSpan = new Label(MangoClientWeb.MESSAGES.helpShort());
		helpSpan.addStyleName(GwtStyles.CONTROL_FEEDBACK_HELP_STYLE);
		helpSpan.getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);

		parent.getElement().appendChild(helpSpan.getElement());

		initWidget(parent);

		helpPopup = new PopupPanel(true);
		helpPopup.setTitle("fds");
		helpPopup.add(new HTML(SafeHtmlUtils.fromString("dsfdsfds")));

		parent.sinkEvents(Event.ONMOUSEOUT);
		parent.sinkEvents(Event.ONMOUSEOVER);

		Event.sinkEvents(helpSpan.getElement(), Event.ONCLICK);

		helpSpan.addHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				helpPopup.showRelativeTo(helpSpan);
			}
		}, ClickEvent.getType());

		FadeAnimation.adaptMouseOver(parent, helpSpan.getElement());
	}

	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof String) {
				textBox.setValue((String) content);
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			textBox.setValue("");
		}
	}

}
