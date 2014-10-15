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

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHandler;
import io.pelle.mango.client.gwt.modules.dictionary.container.BaseTableRowKeyProvider;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.result.DictionaryResult;

import java.util.Set;

import com.google.gwt.user.cellview.client.Column;

public class ResultCellTable<VOType extends IBaseVO> extends BaseCellTable<VOType> {

	private DictionaryResult<VOType> dictionaryResult;

	public ResultCellTable(final DictionaryResult<VOType> dictionaryResult) {
		super(dictionaryResult, new BaseTableRowKeyProvider<VOType>());
		this.dictionaryResult = dictionaryResult;
		setPageSize(dictionaryResult.getMaxResults());
		createModelColumns();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl) {
		return (Column<IBaseTable.ITableRow<VOType>, ?>) ControlHandler.getInstance().createColumn((BaseDictionaryControl<IBaseControlModel, ?>) baseControl, false, null, this);
	}

	@Override
	public Set<String> getHighlightedTexts() {
		return dictionaryResult.getHighlightTexts();
	}

}
