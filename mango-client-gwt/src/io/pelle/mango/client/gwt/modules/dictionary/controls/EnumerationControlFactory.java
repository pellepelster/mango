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
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.EnumerationControl;

import java.util.ArrayList;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * control factory for enumeration controls
 * 
 * @author pelle
 * 
 */
public class EnumerationControlFactory<ENUM_TYPE> extends BaseControlFactory<IEnumerationControlModel<ENUM_TYPE>, EnumerationControl<ENUM_TYPE>> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(EnumerationControl<ENUM_TYPE> enumerationControl, LAYOUT_TYPE layoutType) {
		return new GwtEnumerationControl(enumerationControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl) {
		return baseControl instanceof EnumerationControl;
	}

	@Override
	public <VOType extends IBaseVO> Column<ITableRow<VOType>, ?> createColumn(final EnumerationControl<ENUM_TYPE> enumerationControl, boolean editable, ListDataProvider<ITableRow<VOType>> listDataProvider,
			IMangoCellTable<VOType> mangoCellTable) {
		if (editable) {

			final SelectionCell selectionCell = new SelectionCell(new ArrayList(enumerationControl.getEnumerationMap().values()));

			Column<IBaseTable.ITableRow<VOType>, String> column = new Column<IBaseTable.ITableRow<VOType>, String>(selectionCell) {

				@Override
				public String getValue(IBaseTable.ITableRow<VOType> tableRow) {
					return tableRow.getElement(enumerationControl.getModel()).format();
				}
			};

			FieldUpdater<IBaseTable.ITableRow<VOType>, String> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<VOType>, String>() {
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<VOType> tableRow, String value) {
					tableRow.getElement(enumerationControl.getModel()).setValue(GwtEnumerationControl.getEnumForText(enumerationControl, value));
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		} else {
			return super.createColumn(enumerationControl, editable, listDataProvider, mangoCellTable);
		}
	}

}
