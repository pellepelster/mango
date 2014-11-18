package io.pelle.mango.client.gwt.modules.dictionary.controls.time;

import java.util.Date;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class DateCell extends AbstractSafeHtmlCell<Date> {

	public DateCell() {
		super(DateSafeHtmlRenderer.getInstance());
	}

	public DateCell(SafeHtmlRenderer<Date> renderer) {
		super(renderer);
	}

	@Override
	public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(value);
		}
	}
}