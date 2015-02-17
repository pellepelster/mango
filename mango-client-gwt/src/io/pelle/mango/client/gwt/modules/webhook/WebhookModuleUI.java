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
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.gwt.modules.webhook.WebhookPanel.WebhookPanelCallback;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.databinding.ValidationUtils;
import io.pelle.mango.client.web.modules.webhook.WebHookModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.gwt.commons.toastr.Toastr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class WebhookModuleUI extends BaseGwtModuleUI<WebHookModule> implements AsyncCallback<Result<WebhookVO>>, WebhookPanelCallback {

	final static Logger LOG = Logger.getLogger("LogModuleUI");

	private final FlowPanel panel;

	private Heading title;

	private FlowPanel panelBody;

	private Map<WebhookVO, WebhookPanel> webhookPanels = new HashMap<WebhookVO, WebhookPanel>();

	/**
	 * @param module
	 */
	public WebhookModuleUI(WebHookModule module) {
		super(module, WebHookModule.UI_MODULE_ID);

		panel = new FlowPanel();
		panel.ensureDebugId(WebHookModule.UI_MODULE_ID);
		panel.setWidth("80%");
		// panel.setHeight("100%");

		// - title -------------------------------------------------------------
		title = new Heading(HeadingSize.H2);
		title.setText(getModule().getTitle());
		panel.add(title);

		ButtonGroup addWebhookButtonGroup = new ButtonGroup();
		panel.add(addWebhookButtonGroup);

		Button addWebhookButton = new Button(MangoClientWeb.MESSAGES.webHooksAdd());
		addWebhookButton.setDataToggle(Toggle.DROPDOWN);
		addWebhookButtonGroup.add(addWebhookButton);

		DropDownMenu downMenu = new DropDownMenu();
		addWebhookButtonGroup.add(downMenu);

		panelBody = new FlowPanel();
		panel.add(panelBody);

		for (final WebhookDefinition webhookDefinition : getModule().getWebHookDefinitions()) {
			AnchorListItem anchorListItem = new AnchorListItem(webhookDefinition.getName());
			anchorListItem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					WebhookVO webhook = new WebhookVO();
					WebhookPanel webhookPanel = addPanel(webhookDefinition, webhook);
					webhookPanel.setStatus(true);
				}
			});
			downMenu.add(anchorListItem);
		}

		MangoClientWeb.getInstance().getRemoteServiceLocator().getWebhookService().getWebhooks(new BaseErrorAsyncCallback<List<WebhookVO>>() {

			@Override
			public void onSuccess(List<WebhookVO> result) {
				for (WebhookVO webhook : result) {

					Optional<WebhookDefinition> webhookDefinition = getModule().getWebHookDefinitionById(webhook.getType());

					if (webhookDefinition.isPresent()) {
						addPanel(webhookDefinition.get(), webhook);
					}

				}
			}
		});

	}

	private WebhookPanel addPanel(final WebhookDefinition webhookDefinition, WebhookVO webhook) {
		WebhookPanel webhookPanel = new WebhookPanel(this, webhook, webhookDefinition);
		webhookPanels.put(webhook, webhookPanel);
		panelBody.add(webhookPanel);
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

	@Override
	public void onFailure(Throwable caught) {
		Toastr.error(caught.getMessage());
	}

	@Override
	public void onSuccess(Result<WebhookVO> result) {
		if (result.isOk()) {
			webhookPanels.get(result.getVO()).setStatus(false);
		} else {
			Toastr.error(ValidationUtils.getErrorMessage(result.getValidationMessages()));
		}
	}

	@Override
	public void onAdd(WebhookVO webhook) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getWebhookService().addWebhook(webhook, WebhookModuleUI.this);
	}

	@Override
	public void onDelete(WebhookVO webhook) {

		WebhookPanel webhookPanel = webhookPanels.get(webhook);
		panelBody.remove(webhookPanel);
		webhookPanels.remove(webhook);
	}

	@Override
	public void onCancel(WebhookVO webhook) {
		WebhookPanel webhookPanel = webhookPanels.get(webhook);
		webhookPanel.reset();
	}

}
