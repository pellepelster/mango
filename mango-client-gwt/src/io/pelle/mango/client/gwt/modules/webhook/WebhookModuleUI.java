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
package io.pelle.mango.client.gwt.modules.webhook;

import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.gwt.utils.MangoButton;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.log.LogModule;
import io.pelle.mango.client.web.modules.webhook.WebHookModule;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class WebhookModuleUI extends BaseGwtModuleUI<WebHookModule> {

	final static Logger LOG = Logger.getLogger("LogModuleUI");

	private final VerticalPanel panel;

	private HTML title;

	private VerticalPanel headerPanel;

	/**
	 * @param module
	 */
	public WebhookModuleUI(WebHookModule module) {
		super(module, WebHookModule.UI_MODULE_ID);

		panel = new VerticalPanel();
		panel.ensureDebugId(WebHookModule.UI_MODULE_ID);
		panel.setWidth("100%");
		panel.setHeight("100%");

		headerPanel = new VerticalPanel();

		// - title -------------------------------------------------------------
		title = new HTML(module.getTitle());
		title.addStyleName(GwtStyles.TITLE);
		headerPanel.add(title);
		panel.add(headerPanel);
		
		
		HeaderPanel headerPanel = new HeaderPanel();
		MangoButton addButton = new MangoButton(MangoClientWeb.MESSAGES.webHooksAdd());
		headerPanel.setHeaderWidget(addButton);
		
	    //ListBox webHookType = new ListBox();

	}

	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
