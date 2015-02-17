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
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.databinding.ValidationUtils;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;
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
import org.gwtbootstrap3.client.ui.FieldSet;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.constants.FormType;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.InputType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.common.base.Objects;
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
public class WebhookModuleUI extends BaseGwtModuleUI<WebHookModule> implements AsyncCallback<Result<WebhookVO>> {

	final static Logger LOG = Logger.getLogger("LogModuleUI");

	private final FlowPanel panel;

	private Heading title;

	private FlowPanel panelBody;

	private Map<WebhookVO, Panel> webhookPanels = new HashMap<WebhookVO, Panel>();

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
					addPanel(webhookDefinition, webhook);
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

	private void addPanel(final WebhookDefinition webhookDefinition, WebhookVO webhook) {
		FlowPanel webhookPanel = createWebhookPanel(webhook, webhookDefinition);
		webhookPanels.put(webhook, webhookPanel);
		panelBody.add(webhookPanel);
	}

	private FlowPanel createWebhookPanel(final WebhookVO webhook, final WebhookDefinition webhookDefinition) {

		EntityWebhookDefitnition entityWebhookDefitnition = (EntityWebhookDefitnition) webhookDefinition;

		final FlowPanel webhookPanel = new FlowPanel();

		Form form = new Form();
		webhookPanel.add(form);
		form.setType(FormType.INLINE);

		FieldSet fieldSet = new FieldSet();
		form.add(fieldSet);

		FormGroup formGroup = new FormGroup();
		fieldSet.add(formGroup);

		FormLabel nameLabel = new FormLabel();
		nameLabel.setText(MangoClientWeb.MESSAGES.webHookName());
		formGroup.add(nameLabel);

		final Input nameInput = new Input();
		formGroup.add(nameInput);
		nameInput.setType(InputType.TEXT);
		nameInput.setText(webhook.getName());

		FormLabel urlLabel = new FormLabel();
		urlLabel.setText(MangoClientWeb.MESSAGES.webHookURL());
		formGroup.add(urlLabel);

		final Input urlInput = new Input();
		formGroup.add(urlInput);
		urlInput.setType(InputType.TEXT);
		urlInput.setText(webhook.getUrl());

		FormLabel entityLabel = new FormLabel();
		entityLabel.setText(MangoClientWeb.MESSAGES.webHookName());
		formGroup.add(entityLabel);

		final ListBox entitiesListBox = new ListBox();

		for (IEntityDescriptor<?> entityDescriptor : entityWebhookDefitnition.getEntityDescriptors()) {
			entitiesListBox.addItem(Objects.firstNonNull(entityDescriptor.getLabel(), entityDescriptor.getName()), entityDescriptor.getVOEntityClass().getName());
		}

		formGroup.add(entitiesListBox);

		Button saveButton = new Button(MangoClientWeb.MESSAGES.webHookSave());
		formGroup.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				webhook.setName(nameInput.getText());
				webhook.setUrl(urlInput.getText());
				webhook.setType(webhookDefinition.getId());
				webhook.getData().put(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY, entitiesListBox.getValue(entitiesListBox.getSelectedIndex()));

				MangoClientWeb.getInstance().getRemoteServiceLocator().getWebhookService().addWebhook(webhook, WebhookModuleUI.this);
			}
		});

		Button deleteButton = new Button(MangoClientWeb.MESSAGES.webHookDelete());
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				panelBody.remove(webhookPanel);
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

	@Override
	public void onFailure(Throwable caught) {
		Toastr.error(caught.getMessage());
	}

	@Override
	public void onSuccess(Result<WebhookVO> result) {
		if (!result.isOk()) {
			Toastr.error(ValidationUtils.getErrorMessage(result.getValidationMessages()));
		}
	}

}
