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
package io.pelle.mango.client.gwt.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHandler;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.BaseTableDataGrid;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.container.EditableTable;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.client.web.util.DummyAsyncCallback;
import io.pelle.mango.gwt.commons.ImageButton;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtEditableTable<VOType extends IBaseVO> extends BaseTableDataGrid<VOType> implements IContainer<Panel>, IMangoCellTable<VOType> {

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final EditableTable<VOType> editableTable;

	public GwtEditableTable(final EditableTable<VOType> editableTable) {
		super(editableTable);

		this.editableTable = editableTable;

		createModelColumns();

		setTableWidth(100d, Unit.PCT);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth("99%");
		simpleLayoutPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		verticalPanel.setWidth("100%");
		verticalPanel.add(simpleLayoutPanel);

		createAddButton();

		TextHeader textHeader = new TextHeader("");

		Column<IBaseTable.ITableRow<VOType>, Void> column = new Column<IBaseTable.ITableRow<VOType>, Void>(new ImageActionCell<VOType>(MangoClientWeb.RESOURCES.delete(), new SimpleCallback<IBaseTable.ITableRow<VOType>>() {

			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow) {
				editableTable.delete(tableRow, new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>() {

					@Override
					public void onSuccess(List<IBaseTable.ITableRow<VOType>> result) {
						// do nothing
						;
					}
				});
			}
		})) {

			@Override
			public Void getValue(IBaseTable.ITableRow<VOType> vo) {
				return null;
			}
		};

		addColumn(column, textHeader);

	}

	private void createAddButton() {

		ImageButton addButton = new ImageButton(MangoClientWeb.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editableTable.add(DummyAsyncCallback.<List<ITableRow<VOType>>> dummyAsyncCallback());
			}
		});
		verticalPanel.add(addButton);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseDictionaryControl baseControl) {
		return (Column<IBaseTable.ITableRow<VOType>, ?>) ControlHandler.getInstance().createColumn(baseControl, true, getDataProvider(), this);
	}

	@Override
	public Set<String> getHighlightedTexts() {
		return Collections.emptySet();
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return verticalPanel;
	}

}
