package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.gwt.utils.HtmlUtils;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HighlightingTextCell extends TextCell {

	private IMangoCellTable<?> mangoCellTable;

	public HighlightingTextCell(IMangoCellTable<?> mangoCellTable) {
		this.mangoCellTable = mangoCellTable;
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, String data, SafeHtmlBuilder sb) {
		if (data == null) {
			render(context, (SafeHtml) null, sb);
		} else {

			String valueToRender = data;

			if (!mangoCellTable.getHighlightedTexts().isEmpty()) {
				for (String highlightedText : mangoCellTable.getHighlightedTexts()) {
					HtmlUtils.strong(highlightedText, valueToRender, sb);
				}
			} else {
				sb.appendEscaped(valueToRender);
			}
		}
	}
}
