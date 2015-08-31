package io.pelle.mango.client.gwt.utils;

import org.gwtbootstrap3.client.ui.html.Span;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import io.pelle.mango.client.gwt.GwtStyles;

public class HorizontalSpacer extends Span {

    public HorizontalSpacer() {
        super();
		adapt(this);

    }
    
    public static HorizontalSpacer create() {
    	return new HorizontalSpacer();
    }
    
    public static void adapt(Widget widget) {
    	widget.getElement().getStyle().setPaddingRight(GwtStyles.SPACING, Unit.PX);
    }
    
}
