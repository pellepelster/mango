package io.pelle.mango.client.gwt.modules.dictionary.controls;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class ControlHtmlRenderer implements SafeHtmlRenderer<String> {
	private static final String NBSP = "&nbsp;";

	private static final SafeHtml EMPTY = SafeHtmlUtils.fromSafeConstant(NBSP + NBSP + NBSP + NBSP + NBSP + NBSP);

	private static ControlHtmlRenderer instance;

	public static ControlHtmlRenderer getInstance() {
		if (instance == null) {
			instance = new ControlHtmlRenderer();
		}
		return instance;
	}

	private ControlHtmlRenderer() {
	}

	public SafeHtml render(String object) {

		return (object == null || object.isEmpty()) ? EMPTY : SafeHtmlUtils.fromString(object);
	}

	public void render(String object, SafeHtmlBuilder appendable) {
		appendable.append(SafeHtmlUtils.fromString(object));
	}
}