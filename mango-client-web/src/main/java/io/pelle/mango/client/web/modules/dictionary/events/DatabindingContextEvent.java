/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.modules.dictionary.events;

import com.google.gwt.event.shared.GwtEvent;

public class DatabindingContextEvent extends GwtEvent<DatabindingContextEventHandler>
{
	public DatabindingContextEvent()
	{
	}

	public static Type<DatabindingContextEventHandler> TYPE = new Type<DatabindingContextEventHandler>();

	@Override
	public Type<DatabindingContextEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(DatabindingContextEventHandler handler)
	{
		handler.onDataBindingContextChange(this);
	}

}