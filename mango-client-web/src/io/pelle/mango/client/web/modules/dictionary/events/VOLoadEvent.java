package io.pelle.mango.client.web.modules.dictionary.events;

import io.pelle.mango.client.base.vo.IBaseVO;

import com.google.gwt.event.shared.GwtEvent;

public class VOLoadEvent extends GwtEvent<VOEventHandler>
{
	private IBaseVO baseVO;

	public VOLoadEvent(IBaseVO baseVO)
	{
		this.baseVO = baseVO;
	}

	public static Type<VOEventHandler> TYPE = new Type<VOEventHandler>();

	@Override
	public Type<VOEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(VOEventHandler handler)
	{
		handler.onVOEvent(this.baseVO);
	}

}