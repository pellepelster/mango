package io.pelle.mango.client.gwt.modules.webhook;

import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.webhook.EntityWebhookDefitnition;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.constants.FormType;
import org.gwtbootstrap3.client.ui.constants.InputType;

import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class WebhookPanel extends FlowPanel {

	public interface WebhookPanelCallback {

		void onAdd(WebhookVO webhook);

		void onDelete(WebhookVO webhook);

		void onCancel(WebhookVO webhook);

	}

	private Input nameInput;

	private Input urlInput;

	private ListBox entitiesListBox;

	private Button saveButton;

	private Button deleteButton;

	private Button cancelButton;

	private Button editButton;

	private ButtonGroup buttonGroup;

	private WebhookVO webhook;

	public WebhookPanel(final WebhookPanelCallback callback, final WebhookVO webhook, final WebhookDefinition webhookDefinition) {

		this.webhook = webhook;

		addStyleName(GwtStyles.WEBHOOK_CONTAINER);
		EntityWebhookDefitnition entityWebhookDefitnition = (EntityWebhookDefitnition) webhookDefinition;

		Form form = new Form();
		add(form);
		form.setType(FormType.INLINE);

		// name
		FormGroup nameFormGroup = new FormGroup();
		form.add(nameFormGroup);

		FormLabel nameLabel = new FormLabel();
		nameLabel.setText(MangoClientWeb.MESSAGES.webHookName());
		nameFormGroup.add(nameLabel);

		nameInput = new Input();
		nameFormGroup.add(nameInput);
		nameInput.setType(InputType.TEXT);

		// url
		FormGroup urlFormGroup = new FormGroup();
		form.add(urlFormGroup);

		FormLabel urlLabel = new FormLabel();
		urlLabel.setText(MangoClientWeb.MESSAGES.webHookURL());
		urlFormGroup.add(urlLabel);

		urlInput = new Input();
		urlFormGroup.add(urlInput);
		urlInput.setType(InputType.TEXT);

		// url
		FormGroup entitiesFormGroup = new FormGroup();
		form.add(entitiesFormGroup);

		FormLabel entityLabel = new FormLabel();
		entityLabel.setText(MangoClientWeb.MESSAGES.webHookName());
		entitiesFormGroup.add(entityLabel);

		entitiesListBox = new ListBox();
		entitiesFormGroup.add(entitiesListBox);

		for (IEntityDescriptor<?> entityDescriptor : entityWebhookDefitnition.getEntityDescriptors()) {
			entitiesListBox.addItem(Objects.firstNonNull(entityDescriptor.getLabel(), entityDescriptor.getName()), entityDescriptor.getVOEntityClass().getName());
		}

		// URL
		FormGroup buttondFormGroup = new FormGroup();
		form.add(buttondFormGroup);

		buttonGroup = new ButtonGroup();
		buttondFormGroup.add(buttonGroup);

		saveButton = new Button(MangoClientWeb.MESSAGES.webHookSave());
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				webhook.setName(nameInput.getText());
				webhook.setUrl(urlInput.getText());
				webhook.setType(webhookDefinition.getId());
				webhook.getData().put(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY, entitiesListBox.getValue(entitiesListBox.getSelectedIndex()));

				callback.onAdd(webhook);
			}
		});

		deleteButton = new Button(MangoClientWeb.MESSAGES.webHookDelete());
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				callback.onDelete(webhook);
			}
		});

		cancelButton = new Button(MangoClientWeb.MESSAGES.webHookCancel());
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				callback.onCancel(webhook);
			}
		});

		editButton = new Button(MangoClientWeb.MESSAGES.webHookEdit());
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setStatus(true);
			}
		});

		setStatus(false);
		updateUI();
	}

	private void updateUI() {
		nameInput.setText(webhook.getName());
		urlInput.setText(webhook.getUrl());

		for (int i = 0; i < entitiesListBox.getItemCount(); i++) {
			if (entitiesListBox.getValue(i).equals(webhook.getData().get(EntityWebhookDefitnition.ENTITY_CLASS_NAME_KEY))) {
				entitiesListBox.setSelectedIndex(i);
			}
		}
	}

	public void setStatus(boolean status) {

		nameInput.setEnabled(status);
		urlInput.setEnabled(status);
		entitiesListBox.setEnabled(status);

		for (int i = 0; i < buttonGroup.getWidgetCount(); i++) {
			buttonGroup.remove(i);
		}

		if (status) {
			buttonGroup.add(saveButton);
			buttonGroup.add(deleteButton);
			buttonGroup.add(cancelButton);
			buttonGroup.remove(editButton);
		} else {
			buttonGroup.remove(saveButton);
			buttonGroup.remove(deleteButton);
			buttonGroup.remove(cancelButton);
			buttonGroup.add(editButton);
		}
	}

	public void reset() {
		setStatus(false);
		updateUI();
	}

}
