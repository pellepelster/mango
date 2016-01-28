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
package io.pelle.mango.client.gwt.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;

public abstract class BaseTableDataGrid<VOType extends IBaseVO> extends BaseDataGrid<VOType>implements IUpdateListener {

	private BaseTableElement<VOType, ? extends IBaseTableModel> baseTable;

	public BaseTableDataGrid(final BaseTableElement<VOType, ? extends IBaseTableModel> baseTable) {
		super(baseTable.getControls());

		this.baseTable = baseTable;

		baseTable.addUpdateListener(this);

		onUpdate();
	}

	@Override
	public void onUpdate() {
		if (baseTable != null) {
			getDataProvider().setList(baseTable.getRows());

			if (!baseTable.getSelection().isEmpty()) {
				if (baseTable.getSelection().size() == 1) {
					getSelectionModel().setSelected(baseTable.getSelection().get(0), true);
				} else {
					throw new RuntimeException("more than one table row selcted");
				}
			}
		}
	}

}
