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
package io.pelle.mango.client.gwt.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.vo.IBaseVO;

import com.google.gwt.view.client.ProvidesKey;

public class BaseTableRowKeyProvider<VOType extends IBaseVO> implements ProvidesKey<IBaseTable.ITableRow<VOType>>
{
	@Override
	public Object getKey(IBaseTable.ITableRow<VOType> item)
	{
		return item.getVO();
	}

}
