package io.pelle.mango.client.gwt.utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.gwt.GwtStyles;

public class ActionImage extends Image implements MouseOverHandler, MouseOutHandler, ClickHandler {
	private boolean disabled = false;

	private SimpleCallback<Void> actionCallback;

	public ActionImage(ImageResource imageResource, SimpleCallback<Void> actionCallback) {
		super(imageResource);
		this.actionCallback = actionCallback;

		addHandler(this, MouseOverEvent.getType());
		addHandler(this, MouseOutEvent.getType());
		addHandler(this, ClickEvent.getType());
	}

	public void setActionCallback(SimpleCallback<Void> actionCallback) {
		this.actionCallback = actionCallback;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		if (!disabled) {
			getElement().getStyle().setOpacity(GwtStyles.ENABLED_OPACITY);
		}
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		if (!disabled) {
			getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (!disabled) {
			actionCallback.onCallback(null);
		}
	}

	public void disable() {
		disabled = true;
		getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);
	}

	public void enable() {
		disabled = false;
	}

}
