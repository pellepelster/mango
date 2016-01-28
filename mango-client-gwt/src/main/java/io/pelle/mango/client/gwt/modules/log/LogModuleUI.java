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

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class LogModuleUI extends BaseGwtModuleUI<LogModule> {

	final static Logger LOG = Logger.getLogger("LogModuleUI");

	private final Div container;

	private HTML title;

	private EndlessLogDataGrid logDataGrid;

	private Div headerContainer;

	/**
	 * @param module
	 */
	public LogModuleUI(LogModule module) {
		super(module, LogModule.UI_MODULE_ID);

		container = new Div();
		container.ensureDebugId(LogModule.UI_MODULE_ID);

		headerContainer = new Div();

		// - title -------------------------------------------------------------
		title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		headerContainer.add(title);
		container.add(headerContainer);

		logDataGrid = new EndlessLogDataGrid();
		logDataGrid.setWidth("98%");
		logDataGrid.setHeight("30em");
		container.add(logDataGrid);

		final SimplePager pager = new SimplePager();
		pager.setDisplay(logDataGrid);

		container.addAttachHandler(new Handler() {

			@Override
			public void onAttachOrDetach(AttachEvent event) {
				onResize();
			}
		});

	}

	@Override
	public void onResize() {

		if (headerContainer != null && container != null) {

			int totalHeight = container.getParent().getOffsetHeight();
			int headerHeight = headerContainer.getOffsetHeight() + headerContainer.getAbsoluteTop();

			int tableHeight = totalHeight - (headerHeight);
			logDataGrid.setHeight(tableHeight + "px");
			// GWT.log(tableHeight + "px");
		}

	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return container;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
