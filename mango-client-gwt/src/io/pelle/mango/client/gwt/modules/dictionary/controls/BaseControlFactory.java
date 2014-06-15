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
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author pelle
 * 
 */
public abstract class BaseControlFactory<ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>> implements
		IGwtControlFactory<ControlModelType, ControlType>
{

	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final ControlType baseControl, boolean editable, final ListDataProvider<?> listDataProvider,
			final AbstractCellTable<?> abstractCellTable)
	{

		Column<IBaseTable.ITableRow<IBaseVO>, String> column;

		if (editable)
		{
			final EditTextCellWithValidation editTextCell = new EditTextCellWithValidation(baseControl);

			column = new TableRowColumn(baseControl.getModel(), editTextCell);

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, String value)
				{

					Object key = BaseCellTable.KEYPROVIDER.getKey(tableRow);

					ViewData<String> viewData = (ViewData<String>) editTextCell.getViewData(key);

					// if (validationMessages != null &&
					// ValidationUtils.hasError(validationMessages))
					// {
					// viewData.setValidationMessages(validationMessages);
					// // dataGrid.redraw();
					// }
					// else
					// {
					// viewData.getValidationMessages().clear();
					//
					// // vo.set(baseControl.getModel().getAttributePath(),
					// //
					// TypeHelper.convert(vo.getAttributeDescriptor(baseControl.getModel().getAttributePath()).getAttributeType(),
					// // value));
					// }

					listDataProvider.refresh();
				}
			};
			column.setFieldUpdater(fieldUpdater);

		}
		else
		{
			column = new TableRowColumn(baseControl.getModel(), new TextCell());
		}

		return column;

	}

}
