package io.pelle.mango.client.gwt.utils;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HtmlWithHelp extends SimplePanel {

	private InlineLabel html;

	public HtmlWithHelp(String text, String helpText) {
		super();

		html = new InlineLabel(text);
		add(html);

		if (helpText != null) {
			HelpLabel.createLabel(this, helpText, true);
		}
	}

	public void setText(String text) {
		html.setText(text);
	}

}
