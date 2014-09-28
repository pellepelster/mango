package io.pelle.mango.client.gwt.utils;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class HtmlUtils {

	public static void strong(String strongText, String text, SafeHtmlBuilder sb) {

		if (strongText == null || text == null) {
			return;
		}

		if (text.trim().isEmpty()) {
			sb.appendEscaped(text);
			return;
		}

		int currentFromIndex = 0;
		int lastIndex = 0;
		while (currentFromIndex > -1 && lastIndex < strongText.length()) {

			currentFromIndex = text.indexOf(strongText, currentFromIndex);

			if (currentFromIndex > -1) {
				sb.appendEscaped(text.substring(lastIndex, currentFromIndex));
			}

			if (currentFromIndex > -1) {
				sb.append(SafeHtmlUtils.fromTrustedString("<strong>"));
				sb.appendEscaped(strongText);
				sb.append(SafeHtmlUtils.fromTrustedString("</strong>"));

				lastIndex = currentFromIndex + strongText.length();
			}

		}

		if (lastIndex < text.length()) {
			sb.appendEscaped(text.substring(lastIndex));
		}
	}
}
