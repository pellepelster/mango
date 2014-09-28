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
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.DateControl;

import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * control factory for date controls
 * 
 * @author pelle
 * 
 */
public class DateControlFactory extends BaseControlFactory<IDateControlModel, DateControl> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(DateControl dateControl, LAYOUT_TYPE layoutType) {
		return new GwtDateControl(dateControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") BaseDictionaryControl baseControl) {
		return baseControl instanceof DateControl;
	}

	@Override
	public <VOType extends IBaseVO> Column<ITableRow<VOType>, ?> createColumn(final DateControl dateControl, boolean editable, ListDataProvider<ITableRow<VOType>> listDataProvider, IMangoCellTable<VOType> mangoCellTable) {
		if (editable) {
			final DatePickerCell datePickerCell = new DatePickerCell();

			Column<IBaseTable.ITableRow<VOType>, Date> column = new Column<IBaseTable.ITableRow<VOType>, Date>(datePickerCell) {

				@Override
				public Date getValue(IBaseTable.ITableRow<VOType> tableRow) {
					Object date = tableRow.getElement(dateControl.getModel()).getValue();

					if (date == null) {
						return new Date();
					} else {
						return (Date) tableRow.getElement(dateControl.getModel()).getValue();
					}
				}
			};

			FieldUpdater<IBaseTable.ITableRow<VOType>, Date> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<VOType>, Date>() {
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<VOType> tableRow, Date value) {
					tableRow.getElement(dateControl.getModel()).setValue(value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		} else {
			return super.createColumn(dateControl, editable, listDataProvider, mangoCellTable);
		}
	}

}
