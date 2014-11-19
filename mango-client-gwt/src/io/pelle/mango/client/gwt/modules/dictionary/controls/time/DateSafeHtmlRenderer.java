package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class DateSafeHtmlRenderer implements SafeHtmlRenderer<Long> {

	private static DateSafeHtmlRenderer instance;

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_LONG);

	public static DateSafeHtmlRenderer getInstance() {
		if (instance == null) {
			instance = new DateSafeHtmlRenderer();
		}
		return instance;
	}

	private DateSafeHtmlRenderer() {
	}

	public SafeHtml render(Long object) {
		return (object == null) ? SafeHtmlUtils.EMPTY_SAFE_HTML : SafeHtmlUtils.fromString(FORMAT.format(new Date(object)));
	}

	public void render(Long object, SafeHtmlBuilder appendable) {
		appendable.append(SafeHtmlUtils.fromString(FORMAT.format(new Date(object))));
	}
}