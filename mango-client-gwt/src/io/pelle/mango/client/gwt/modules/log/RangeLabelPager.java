package io.pelle.mango.client.gwt.modules.log;

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

public class RangeLabelPager extends AbstractPager {

	/**
	 * The label that shows the current range.
	 */
	private final HTML label = new HTML();

	/**
	 * Construct a new {@link RangeLabelPager}.
	 */
	public RangeLabelPager() {
		initWidget(label);
	}

	@Override
	protected void onRangeOrRowCountChanged() {
		HasRows display = getDisplay();
		Range range = display.getVisibleRange();
		int start = range.getStart();
		int end = start + range.getLength();
		label.setText(start + " - " + end + " : " + display.getRowCount(), HasDirection.Direction.LTR);
	}
}