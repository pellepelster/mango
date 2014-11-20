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
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.gwt.modules.dictionary.controls.time.DateColumn;
import io.pelle.mango.client.gwt.modules.log.EndlessDataGrid.EndlessDataGridCallback;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.log.LogModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class LogModuleUI extends BaseGwtModuleUI<LogModule> {

	final static Logger LOG = Logger.getLogger("LogModuleUI");

	private final FlowPanel panel;

	private HTML title;

	private EndlessDataGrid<LogEntryVO> dataGrid;

	private VerticalPanel headerPanel;

	/**
	 * @param module
	 */
	public LogModuleUI(LogModule module) {
		super(module, LogModule.UI_MODULE_ID);

		panel = new FlowPanel();
		panel.ensureDebugId(LogModule.UI_MODULE_ID);
		panel.setWidth("100%");
		panel.setHeight("100%");

		headerPanel = new VerticalPanel();

		// - title -------------------------------------------------------------
		title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		headerPanel.add(title);
		panel.add(headerPanel);

		dataGrid = new EndlessDataGrid<LogEntryVO>();
		dataGrid.setWidth("98%");
		dataGrid.setDataGridCallback(new EndlessDataGridCallback<LogEntryVO>() {

			private AsyncCallback<List<LogEntryVO>> callback = new BaseErrorAsyncCallback<List<LogEntryVO>>() {
				@Override
				public void onSuccess(List<LogEntryVO> result) {
					setData(result);
				};
			};

			@Override
			void initalGetData(int pageSize) {
				MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLog(pageSize, callback);
			}

			@Override
			void getDataBefore(LogEntryVO item, int pageSize) {
				MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLogAfter(item.getTimestamp(), pageSize, callback);
			}

			@Override
			void getDataAfter(LogEntryVO item, int pageSize) {
				MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLogBefore(item.getTimestamp(), pageSize, callback);
			}
		});
		panel.add(dataGrid);

		dataGrid.addColumn(new DateColumn<LogEntryVO>() {
			@Override
			public Long getValue(LogEntryVO object) {
				return object.getTimestamp();
			}
		}, MangoClientWeb.MESSAGES.logTimestampTitle());

		dataGrid.addColumn(new TextColumn<LogEntryVO>() {

			@Override
			public String getValue(LogEntryVO object) {
				return object.getMessage();
			}
		}, MangoClientWeb.MESSAGES.logMessageTitle());

		final SimplePager pager = new SimplePager();
		pager.setDisplay(dataGrid);

		panel.addAttachHandler(new Handler() {

			@Override
			public void onAttachOrDetach(AttachEvent event) {
				onResize();
			}
		});

	}

	@Override
	public void onResize() {

		int totalHeight = panel.getParent().getOffsetHeight();
		int headerHeight = headerPanel.getOffsetHeight() + headerPanel.getAbsoluteTop();

		int tableHeight = totalHeight - (headerHeight);
		LOG.info("tableHeight:" + tableHeight);

		dataGrid.setHeight(tableHeight + "px");
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
