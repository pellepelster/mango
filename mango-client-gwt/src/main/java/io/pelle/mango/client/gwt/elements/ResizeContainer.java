package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.Container;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizeContainer extends Container implements ProvidesResize, RequiresResize {

	public ResizeContainer() {
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
