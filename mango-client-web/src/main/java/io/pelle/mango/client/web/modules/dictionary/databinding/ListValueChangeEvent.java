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
package io.pelle.mango.client.web.modules.dictionary.databinding;

import java.util.List;

public class ListValueChangeEvent
{
	public enum LIST_CHANGE_TPYE
	{
		ADD, REMOVE, UPDATE, WHOLE_LIST
	}

	private final String listAttributePath;
	private long oId;
	private final Object value;
	private final LIST_CHANGE_TPYE listChangeType;

	public ListValueChangeEvent(String listAttributePath, List<?> value)
	{
		super();
		this.listAttributePath = listAttributePath;
		this.value = value;
		this.listChangeType = LIST_CHANGE_TPYE.WHOLE_LIST;

	}

	public ListValueChangeEvent(String listAttributePath, long oId, Object value, LIST_CHANGE_TPYE listChangeType)
	{
		super();
		this.listAttributePath = listAttributePath;
		this.value = value;
		this.oId = oId;
		this.listChangeType = listChangeType;
	}

	public String getListAttributePath()
	{
		return listAttributePath;
	}

	public LIST_CHANGE_TPYE getListChangeType()
	{
		return listChangeType;
	}

	protected long getoId()
	{
		return oId;
	}

	public Object getValue()
	{
		return value;
	}

}
