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
package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.IContentAwareModel;

public interface IBaseTableModel extends IBaseContainerModel, IContentAwareModel
{
	final static int DEFAULT_VISBLE_ROWS = 5;

	final static int DEFAULT_PAGING_SIZE = 25;

	Integer getVisibleRows();

	int getPagingSize();

}
