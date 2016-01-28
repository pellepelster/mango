package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class TimestampSafeHtmlRenderer implements SafeHtmlRenderer<Long> {

	private static TimestampSafeHtmlRenderer instance;

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);

	private static final DateTimeFormat TIME_FORMAT = DateTimeFormat.getFormat("hh:mm:ss.SSS");

	private String format(Long value) {
		Date date = new Date(value);
		return DATE_FORMAT.format(date) + " " + TIME_FORMAT.format(date);
	}

	public static TimestampSafeHtmlRenderer getInstance() {
		if (instance == null) {
			instance = new TimestampSafeHtmlRenderer();
		}
		return instance;
	}

	private TimestampSafeHtmlRenderer() {
	}

	public SafeHtml render(Long object) {
		return (object == null) ? SafeHtmlUtils.EMPTY_SAFE_HTML : SafeHtmlUtils.fromString(format(object));
	}

	public void render(Long object, SafeHtmlBuilder appendable) {
		appendable.append(SafeHtmlUtils.fromString(format(object)));
	}
}