package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizeDiv extends Div implements ProvidesResize, RequiresResize {

	public ResizeDiv() {
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
