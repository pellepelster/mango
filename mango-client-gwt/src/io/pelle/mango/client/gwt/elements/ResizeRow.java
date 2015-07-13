package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.Row;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizeRow extends Row implements ProvidesResize, RequiresResize {

	public ResizeRow() {
		super();
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
