package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizeColumn extends Column implements ProvidesResize, RequiresResize {

	public ResizeColumn(ColumnSize firstSize, ColumnSize... otherSizes) {
		super(firstSize, otherSizes);
	}

	public ResizeColumn(ColumnSize size, Widget firstWidget, Widget... otherWidgets) {
		super(size, firstWidget, otherWidgets);
	}

	public ResizeColumn(String size) {
		super(size);
	}

	@Override
	public void onResize() {
		for (Widget widget : getChildren()) {

			if (widget instanceof RequiresResize) {
				((RequiresResize) widget).onResize();
			}
		}
	}

}
