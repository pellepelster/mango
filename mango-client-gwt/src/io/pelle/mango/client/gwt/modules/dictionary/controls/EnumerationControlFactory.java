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

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.EnumerationControl;

import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * control factory for enumeration controls
 * 
 * @author pelle
 * 
 */
public class EnumerationControlFactory extends BaseControlFactory<IEnumerationControlModel, EnumerationControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(EnumerationControl enumerationControl, LAYOUT_TYPE layoutType)
	{
		return new GwtEnumerationControl(enumerationControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl)
	{
		return baseControl instanceof EnumerationControl;
	}

	@Override
	public Column<IBaseTable.ITableRow<IBaseVO>, ?> createColumn(final EnumerationControl enumerationControl, boolean editable,
			ListDataProvider<?> listDataProvider, AbstractCellTable<?> abstractCellTable)
	{

		if (editable)
		{
			List<String> enumList = GwtEnumerationControl.getSortedEnumList(enumerationControl.getModel());
			final SelectionCell selectionCell = new SelectionCell(enumList);

			Column<IBaseTable.ITableRow<IBaseVO>, String> column = new Column<IBaseTable.ITableRow<IBaseVO>, String>(selectionCell)
			{

				@Override
				public String getValue(IBaseTable.ITableRow<IBaseVO> tableRow)
				{
					return tableRow.getElement(enumerationControl.getModel()).format();
				}
			};

			FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<IBaseVO>, String>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<IBaseVO> tableRow, String value)
				{
					tableRow.getElement(enumerationControl.getModel()).setValue(GwtEnumerationControl.getEnumForText(enumerationControl.getModel(), value));
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(enumerationControl, editable, listDataProvider, abstractCellTable);
		}
	}

}
