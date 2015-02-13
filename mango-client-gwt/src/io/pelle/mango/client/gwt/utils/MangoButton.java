package io.pelle.mango.client.gwt.utils;

import com.google.gwt.resources.client.ImageResource;

import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.gwt.commons.ImageButton;

public class MangoButton extends ImageButton {

	public MangoButton() {
		super();
		
		addStyleName(GwtStyles.BUTTON);
		addStyleName(GwtStyles.BUTTON_DEFAULT);
	}

	public MangoButton(ImageResource imageResource) {
		super(imageResource);
	}

	public MangoButton(String text) {
		super(text);
	}

}
