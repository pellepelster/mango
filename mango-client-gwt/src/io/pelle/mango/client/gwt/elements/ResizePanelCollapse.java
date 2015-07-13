package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.PanelCollapse;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizePanelCollapse extends PanelCollapse implements ProvidesResize, RequiresResize {

	@Override
	public void onResize() {

		for (Widget widget : getChildren()) {

			if (widget instanceof RequiresResize) {
				((RequiresResize) widget).onResize();
			}

		}
	}

}
