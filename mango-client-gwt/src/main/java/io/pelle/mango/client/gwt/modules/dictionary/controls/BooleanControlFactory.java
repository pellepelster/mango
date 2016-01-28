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
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.BooleanControl;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * control factory for boolean controls
 * 
 * @author pelle
 * 
 */
public class BooleanControlFactory extends BaseControlFactory<IBooleanControlModel, BooleanControl> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(BooleanControl booleanControl, LAYOUT_TYPE layoutType) {
		switch (layoutType) {
		case FILTER:
			return new GwtBooleanFilterControl(booleanControl);
		default:
			return new GwtBooleanControl(booleanControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControlModel) {
		return baseControlModel instanceof BooleanControl;
	}

	@Override
	public <VOType extends IBaseVO> Column<ITableRow<VOType>, ?> createColumn(final BooleanControl booleanControl, boolean editable, ListDataProvider<ITableRow<VOType>> listDataProvider, IMangoCellTable<VOType> mangoCellTable) {

		if (editable) {
			final CheckboxCell checkboxCell = new CheckboxCell();

			Column<IBaseTable.ITableRow<VOType>, Boolean> column = new Column<IBaseTable.ITableRow<VOType>, Boolean>(checkboxCell) {

				@Override
				public Boolean getValue(IBaseTable.ITableRow<VOType> tableRow) {
					return (Boolean) tableRow.getElement(booleanControl.getModel()).getValue();
				}
			};

			FieldUpdater<IBaseTable.ITableRow<VOType>, Boolean> fieldUpdater = new FieldUpdater<IBaseTable.ITableRow<VOType>, Boolean>() {
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseTable.ITableRow<VOType> tableRow, Boolean value) {
					tableRow.getElement(booleanControl.getModel()).setValue(value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		} else {
			return super.createColumn(booleanControl, editable, listDataProvider, mangoCellTable);
		}
	}

}
