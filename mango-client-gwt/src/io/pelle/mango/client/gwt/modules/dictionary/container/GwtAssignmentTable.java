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
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ControlHandler;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.widgets.ImageButton;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.container.AssignmentTable;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.layout.WidthCalculationStrategy;

import java.util.Collection;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtAssignmentTable<VOType extends IBaseVO> extends BaseCellTable<VOType> implements IContainer<Panel>
{

	private final AssignmentTable<VOType> assignmentTable;

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<IBaseTable.ITableRow<VOType>> dataProvider = new ListDataProvider<IBaseTable.ITableRow<VOType>>();

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	public GwtAssignmentTable(AssignmentTable<VOType> assignmentTable)
	{
		super(assignmentTable.getControls());

		this.assignmentTable = assignmentTable;
		setWidth("100%");

		dataProvider.addDataDisplay(this);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth(WidthCalculationStrategy.getInstance().getTableWidthCss(assignmentTable.getModel()));
		simpleLayoutPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		verticalPanel.add(simpleLayoutPanel);
		verticalPanel.setWidth("100%");

		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setWidth("1px");
		verticalPanel.add(buttonPanel);

		createAddButton(buttonPanel);

		createModelColumns();

		TextHeader textHeader = new TextHeader("");
		Column<IBaseTable.ITableRow<VOType>, Void> column = new Column<IBaseTable.ITableRow<VOType>, Void>(new ImageActionCell(MangoClientWeb.RESOURCES.delete(),
				new SimpleCallback<IBaseTable.ITableRow<VOType>>()
				{

					@Override
					public void onCallback(IBaseTable.ITableRow<VOType> vo)
					{
						dataProvider.getList().remove(vo);
						// fireValueChanges();
					}
				}))
		{
			@Override
			public Void getValue(IBaseTable.ITableRow<VOType> vo)
			{
				return null;
			}
		};

		addColumn(column, textHeader);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<VOType, ?> getColumn(BaseDictionaryControl baseControl)
	{
		return (Column<VOType, ?>) ControlHandler.getInstance().createColumn(baseControl, false, dataProvider, this);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	public void setContent(Object content)
	{
		if (content instanceof List)
		{
			this.dataProvider.getList().clear();
			this.dataProvider.getList().addAll((Collection<IBaseTable.ITableRow<VOType>>) content);
		}
		else
		{
			throw new RuntimeException("unsupported content type '" + content.getClass().getName() + "'");
		}
	}

	private void createAddButton(VerticalPanel buttonPanel)
	{
		ImageButton addButton = new ImageButton(MangoClientWeb.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				VOSelectionPopup.create(assignmentTable, new SimpleCallback<VOType>()
				{

					@Override
					public void onCallback(VOType vo)
					{
						if (!dataProvider.getList().contains(vo))
						{
							throw new RuntimeException("TODO");
							// dataProvider.getList().add(new TableRow<VOType,
							// IBaseTableModel>(vo));
							// fireValueChanges();
						}
					}
				}).show();
			}
		});
		buttonPanel.add(addButton);
	}

}
