package io.pelle.mango.client.gwt.modules.dictionary.container;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Widget;

public class HorizontalDiv extends Div {

	@Override
	protected void add(Widget child, com.google.gwt.user.client.Element container) {

		child.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

		super.add(child, container);
	}

}
