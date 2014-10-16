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

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.utils.FadeAnimation;
import io.pelle.mango.client.web.MangoClientWeb;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BaseControlWithHelp<CONTROL_TYPE extends Widget> extends Composite {

	private SimplePanel parent;

	private Label helpLabel;

	private PopupPanel helpPopup;

	private CONTROL_TYPE widget;

	public BaseControlWithHelp(final CONTROL_TYPE widget, IBaseControlModel baseControlModel) {
		super();
		this.widget = widget;

		parent = new SimplePanel();
		parent.addStyleName(GwtStyles.CONTROL_HAS_FEEDBACK_STYLE);
		parent.add(widget);

		if (baseControlModel.getHelpText() != null) {

			helpLabel = new Label(MangoClientWeb.MESSAGES.helpShort());
			parent.getElement().appendChild(helpLabel.getElement());

			helpLabel.addStyleName(GwtStyles.CONTROL_FEEDBACK_HELP_STYLE);
			helpLabel.getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);

			helpPopup = new PopupPanel(true);
			helpPopup.add(new HTML(SafeHtmlUtils.fromTrustedString(baseControlModel.getHelpText())));
			parent.addDomHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Element targetElement = event.getNativeEvent().getEventTarget().cast();

					if (targetElement == helpLabel.getElement()) {
						helpPopup.showRelativeTo(helpLabel);
					}
				}
			}, ClickEvent.getType());

			FadeAnimation.adaptMouseOver(parent, helpLabel.getElement());
		}
		initWidget(parent);
	}

	public CONTROL_TYPE getWidget() {
		return widget;
	}
}
