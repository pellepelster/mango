package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class TimestampCell extends AbstractSafeHtmlCell<Long> {

	public TimestampCell() {
		super(TimestampSafeHtmlRenderer.getInstance());
	}

	public TimestampCell(SafeHtmlRenderer<Long> renderer) {
		super(renderer);
	}

	@Override
	public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(value);
		}
	}
}