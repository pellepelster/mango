package io.pelle.mango.client.base.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.resources.client.ImageResource;

public abstract class BaseButton implements IButton
{

	private final ImageResource image;

	private final String title;

	private final String debugId;

	private boolean enabled = true;

	private List<IButtonUpdateHandler> updateHandlers = new ArrayList<IButtonUpdateHandler>();

	public BaseButton(ImageResource image, String title, String debugId)
	{
		super();
		this.image = image;
		this.title = title;
		this.debugId = debugId;
	}

	@Override
	public ImageResource getImage()
	{
		return this.image;
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public String getDebugId()
	{
		return this.debugId;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void addUpdatehandler(IButtonUpdateHandler updateHandler)
	{
		this.updateHandlers.add(updateHandler);
	}

}
