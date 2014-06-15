package io.pelle.mango.client.base.modules.dictionary.controls;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;

public interface IButton extends ClickHandler
{
	ImageResource getImage();

	String getTitle();

	String getDebugId();

	void setEnabled(boolean enabled);

	boolean isEnabled();

	void addUpdatehandler(IButtonUpdateHandler updateHandler);

}
