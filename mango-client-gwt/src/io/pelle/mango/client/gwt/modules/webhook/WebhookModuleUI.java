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

import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.webhook.WebHookModule;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.FieldSet;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.constants.FormType;
import org.gwtbootstrap3.client.ui.constants.InputType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
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

	private org.gwtbootstrap3.client.ui.Panel listWebhookPanel;

	private Map<WebhookVO, org.gwtbootstrap3.client.ui.Panel> webhookPanels = new HashMap<WebhookVO, org.gwtbootstrap3.client.ui.Panel>();

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

		org.gwtbootstrap3.client.ui.Panel addWebhookPanel = new org.gwtbootstrap3.client.ui.Panel();
		addWebhookPanel.setType(PanelType.INFO);
		panel.add(addWebhookPanel);

		ButtonGroup addWebhookButtonGroup = new ButtonGroup();
		addWebhookPanel.add(addWebhookButtonGroup);

		Button addWebhookButton = new Button(MangoClientWeb.MESSAGES.webHooksAdd());
		addWebhookButton.setDataToggle(Toggle.DROPDOWN);
		addWebhookButtonGroup.add(addWebhookButton);

		DropDownMenu downMenu = new DropDownMenu();
		addWebhookButtonGroup.add(downMenu);

		listWebhookPanel = new org.gwtbootstrap3.client.ui.Panel();
		panel.add(listWebhookPanel);

		for (final WebhookDefinition webhookDefinition : getModule().getWebHookDefinitions()) {
			AnchorListItem anchorListItem = new AnchorListItem(webhookDefinition.getName());
			anchorListItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					WebhookVO webhook = new WebhookVO();
					org.gwtbootstrap3.client.ui.Panel webhookPanel = createWebhookPanel(webhook, webhookDefinition);
					webhookPanels.put(webhook, webhookPanel);
					listWebhookPanel.add(webhookPanel);
				}

			});
			downMenu.add(anchorListItem);
		}

	}

	private org.gwtbootstrap3.client.ui.Panel createWebhookPanel(final WebhookVO webhook, WebhookDefinition webhookDefinition) {

		final org.gwtbootstrap3.client.ui.Panel webhookPanel = new org.gwtbootstrap3.client.ui.Panel();

		Form form = new Form();
		form.setType(FormType.INLINE);

		FieldSet fieldSet = new FieldSet();
		form.add(fieldSet);

		FormGroup formGroup = new FormGroup();
		fieldSet.add(formGroup);

		FormLabel nameLabel = new FormLabel();
		nameLabel.setText(MangoClientWeb.MESSAGES.webHookName());
		formGroup.add(nameLabel);

		Input nameInput = new Input();
		formGroup.add(nameInput);
		nameInput.setType(InputType.TEXT);

		FormLabel urlLabel = new FormLabel();
		urlLabel.setText(MangoClientWeb.MESSAGES.webHookURL());
		formGroup.add(urlLabel);

		Input urlInput = new Input();
		formGroup.add(urlInput);
		urlInput.setType(InputType.TEXT);

		webhookPanel.add(form);

		Button saveButton = new Button(MangoClientWeb.MESSAGES.webHookSave());
		formGroup.add(saveButton);

		Button deleteButton = new Button(MangoClientWeb.MESSAGES.webHookDelete());
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				listWebhookPanel.remove(webhookPanel);
				webhookPanels.remove(webhook);
			}
		});
		formGroup.add(deleteButton);

		return webhookPanel;
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
