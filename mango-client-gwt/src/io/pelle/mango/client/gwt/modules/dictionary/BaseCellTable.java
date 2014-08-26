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
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.container.BaseTableRowKeyProvider;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.layout.WidthCalculationStrategy;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class BaseCellTable<VOType extends IBaseVO> extends CellTable<IBaseTable.ITableRow<VOType>>
{
	private ListDataProvider<IBaseTable.ITableRow<VOType>> dataProvider = new ListDataProvider<IBaseTable.ITableRow<VOType>>();

	private SingleSelectionModel<IBaseTable.ITableRow<VOType>> selectionModel;

	private List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls;

	public static final String DEFAULT_TABLE_HEIGHT = "15em";

	public static final String DEFAULT_TABLE_WIDTH = "5em";

	public static final int DEFAULT_MAX_RESULTS = 15;

	private BaseTableElement<VOType, ?> baseTableElement;
	
	public BaseCellTable(BaseTableElement<VOType, ?> baseTableElement, BaseTableRowKeyProvider<VOType> keyProvider)
	{
		super(keyProvider);
		selectionModel = new SingleSelectionModel<IBaseTable.ITableRow<VOType>>(keyProvider);
		dataProvider.addDataDisplay(this);
		this.baseTableElement = baseTableElement;
		this.baseControls = baseTableElement.getControls();
	}

	protected void createModelColumns()
	{
		for (BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl : baseControls)
		{
			TextHeader textHeader = new TextHeader(DictionaryModelUtil.getColumnLabel(baseControl.getModel()));
			Column<IBaseTable.ITableRow<VOType>, ?> column = getColumn(baseControl);
			setColumnWidth(column, WidthCalculationStrategy.getInstance().getControlColumnWidth(baseControl.getModel()), Unit.PX);

			addColumn(column, textHeader);
		}

		setSelectionModel(selectionModel);
		addDomHandler(new DoubleClickHandler()
		{
			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{
				baseTableElement.activateSelection();
			}
		}, DoubleClickEvent.getType());

		selectionModel.addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				baseTableElement.setSelection(selectionModel.getSelectedObject());
			}
		});
	}

	public void setRows(List<IBaseTable.ITableRow<VOType>> rows)
	{
		dataProvider.setList(rows);
	}

	protected IBaseTable.ITableRow<VOType> getSelection()
	{
		return selectionModel.getSelectedObject();
	}

	protected abstract Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl);

}
