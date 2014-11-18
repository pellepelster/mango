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
package io.pelle.mango.client.gwt.modules.log;

import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.controls.time.DateColumn;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.log.LogModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class LogModuleUI extends BaseGwtModuleUI<LogModule> {

	private final VerticalPanel verticalPanel;

	private final AsyncDataProvider<LogEntryVO> dataProvider = new AsyncDataProvider<LogEntryVO>() {

		@Override
		protected void onRangeChanged(final HasData<LogEntryVO> display) {
			final int start = display.getVisibleRange().getStart();
			final int length = display.getVisibleRange().getLength();

			MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLog(start, length, new BaseErrorAsyncCallback<List<LogEntryVO>>() {

				@Override
				public void onSuccess(List<LogEntryVO> result) {
					updateRowCount(start + length + length, false);
					updateRowData(display, start, result);
				};
			});
		}
	};

	/**
	 * @param module
	 */
	public LogModuleUI(LogModule module) {
		super(module, LogModule.UI_MODULE_ID);

		verticalPanel = new VerticalPanel();
		verticalPanel.ensureDebugId(LogModule.UI_MODULE_ID);
		verticalPanel.setWidth("100%");
		verticalPanel.setHeight("100%");

		// - title -------------------------------------------------------------
		HTML title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		verticalPanel.add(title);

		DataGrid<LogEntryVO> dataGrid = new DataGrid<LogEntryVO>();
		dataGrid.setWidth("100%");
		dataGrid.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		dataGrid.addColumn(new DateColumn<LogEntryVO>() {
			@Override
			public Date getValue(LogEntryVO object) {
				return object.getTimestamp();
			}
		}, MangoClientWeb.MESSAGES.logTimestampTitle());

		dataGrid.addColumn(new TextColumn<LogEntryVO>() {

			@Override
			public String getValue(LogEntryVO object) {
				return object.getMessage();
			}
		}, MangoClientWeb.MESSAGES.logMessageTitle());

		dataProvider.addDataDisplay(dataGrid);
		verticalPanel.add(dataGrid);

		SimplePager pager = new SimplePager();
		pager.setDisplay(dataGrid);
		verticalPanel.add(pager);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return verticalPanel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
