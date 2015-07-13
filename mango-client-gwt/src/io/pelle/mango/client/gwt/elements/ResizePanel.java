package io.pelle.mango.client.gwt.elements;

import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.PanelType;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class ResizePanel extends Panel implements ProvidesResize, RequiresResize {

	public ResizePanel() {
		super();
	}

	public ResizePanel(PanelType type) {
		super(type);
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
