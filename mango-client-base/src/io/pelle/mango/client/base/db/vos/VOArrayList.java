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
package io.pelle.mango.client.base.db.vos;

import io.pelle.mango.client.base.vo.BaseParentArrayList;
import io.pelle.mango.client.base.vo.IBaseVO;


public class VOArrayList<VOType extends IBaseVO> extends BaseParentArrayList<VOType>
{

	private static final long serialVersionUID = 963930615370437776L;

	public VOArrayList()
	{
		super();
	}

	public VOArrayList(VOType parent, String listItemParentVOAttributeName)
	{
		super(parent, listItemParentVOAttributeName);
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(VOType vo)
	{
		vo.set(getListItemParentVOAttributeName(), getParent());

		return getList().add(vo);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o)
	{
		if (contains(o))
		{
			VOType vo = (VOType) o;
			vo.set(getListItemParentVOAttributeName(), null);
		}

		return getList().remove(o);
	}

	

}
