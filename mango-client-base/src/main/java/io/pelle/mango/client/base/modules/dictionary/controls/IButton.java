package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;

public interface IButton {
	ImageResource getImage();

	String getTitle();

	String getId();

	void setEnabled(boolean enabled);

	boolean isEnabled();

	void addUpdatehandler(IButtonUpdateHandler updateHandler);

	void onClick(final ClickEvent event, DictionaryContext dictionaryContext);
}
