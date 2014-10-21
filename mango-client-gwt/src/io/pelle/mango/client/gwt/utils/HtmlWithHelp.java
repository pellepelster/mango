package io.pelle.mango.client.gwt.utils;

import com.google.gwt.user.client.ui.HTML;

public class HtmlWithHelp extends HTML {

	public HtmlWithHelp(String html, String helpText) {
		super(html);

		if (helpText != null) {
			HelpLabel.createLabel(this, helpText, 0);
		}
	}

}
