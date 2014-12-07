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
import io.pelle.mango.client.web.modules.log.LogModule;

import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.cellview.client.SimplePager;
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

	private EndlessLogDataGrid logDataGrid;

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

		logDataGrid = new EndlessLogDataGrid();
		logDataGrid.setWidth("98%");
		panel.add(logDataGrid);

		final SimplePager pager = new SimplePager();
		pager.setDisplay(logDataGrid);

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

		logDataGrid.setHeight(tableHeight + "px");
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
