package io.pelle.mango.client.gwt.utils;

import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.web.MangoClientWeb;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class HelpLabel {

	public static void createLabel(Widget parent, String helpText, Integer right) {

		final Label label = new Label(MangoClientWeb.MESSAGES.helpShort());
		final PopupPanel helpPopup = new PopupPanel(true);

		parent.addStyleName(GwtStyles.CONTROL_HAS_FEEDBACK_STYLE);
		parent.getElement().appendChild(label.getElement());

		label.addStyleName(GwtStyles.CONTROL_FEEDBACK_HELP_STYLE);
		if (right != null) {
			label.getElement().getStyle().setRight(right, Unit.PX);
		}
		label.getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);

		helpPopup.add(new HTML(SafeHtmlUtils.fromTrustedString(helpText)));

		parent.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Element targetElement = event.getNativeEvent().getEventTarget().cast();

				if (targetElement == label.getElement()) {
					helpPopup.showRelativeTo(label);
				}
			}
		}, ClickEvent.getType());

		FadeAnimation.adaptMouseOver(parent, label.getElement());
	}

}
