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

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHandler;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.result.DictionaryResult;

import com.google.gwt.user.cellview.client.Column;

public class ResultCellTable<VOType extends IBaseVO> extends BaseCellTable<VOType>
{

	public ResultCellTable(final DictionaryResult<VOType> dictionaryResult)
	{
		super(dictionaryResult);
		createModelColumns();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<VOType, ?> getColumn(BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl)
	{
		return (Column<VOType, ?>) ControlHandler.getInstance().createColumn((BaseDictionaryControl<IBaseControlModel, ?>) baseControl, false, null, this);
	}

}
