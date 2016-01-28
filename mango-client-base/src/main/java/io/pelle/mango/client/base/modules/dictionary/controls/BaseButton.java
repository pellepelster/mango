package io.pelle.mango.client.base.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.resources.client.ImageResource;

public abstract class BaseButton implements IButton {

	private ImageResource image;

	private String title;

	private final String id;

	private boolean enabled = true;

	private List<IButtonUpdateHandler> updateHandlers = new ArrayList<IButtonUpdateHandler>();

	public BaseButton(ImageResource image, String title, String id) {
		this(id);
		this.image = image;
		this.title = title;
	}

	public BaseButton(String id) {
		super();
		this.id = id;
	}

	@Override
	public ImageResource getImage() {
		return this.image;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public void addUpdatehandler(IButtonUpdateHandler updateHandler) {
		this.updateHandlers.add(updateHandler);
	}

}
