package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class DateSafeHtmlRenderer implements SafeHtmlRenderer<Date> {

	private static DateSafeHtmlRenderer instance;

	public static DateSafeHtmlRenderer getInstance() {
		if (instance == null) {
			instance = new DateSafeHtmlRenderer();
		}
		return instance;
	}

	private DateSafeHtmlRenderer() {
	}

	public SafeHtml render(Date object) {
		return (object == null) ? SafeHtmlUtils.EMPTY_SAFE_HTML : SafeHtmlUtils.fromString(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(object));
	}

	public void render(Date object, SafeHtmlBuilder appendable) {
		appendable.append(SafeHtmlUtils.fromString(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(object)));
	}
}