package io.pelle.mango.client.gwt.utils;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class VerticalFlowPanel extends FlowPanel {

	@Override
	public void add(Widget w) {
		w.getElement().getStyle().setDisplay(Display.BLOCK);
		super.add(w);
	}

	@Override
	public void insert(IsWidget w, int beforeIndex) {
		w.asWidget().getElement().getStyle().setDisplay(Display.BLOCK);
		super.insert(w, beforeIndex);
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		w.getElement().getStyle().setDisplay(Display.BLOCK);
		super.insert(w, beforeIndex);
	}

}
