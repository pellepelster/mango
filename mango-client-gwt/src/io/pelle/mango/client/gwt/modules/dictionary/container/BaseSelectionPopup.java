package io.pelle.mango.client.gwt.modules.dictionary.container;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;

public abstract class BaseSelectionPopup<VOType extends IBaseVO> {

	private final String message;

	private Modal modal;

	private SimpleCallback<VOType> voSelectHandler;

	protected BaseSelectionPopup(String message, final SimpleCallback<VOType> voSelectHandler) {
		this.message = message;
		this.voSelectHandler = voSelectHandler;
	}

	private void createOkButton(FlowPanel panel) {
		Button button = new Button(MangoClientWeb.getInstance().getMessages().buttonOk());
		button.setType(ButtonType.PRIMARY);
		button.setIcon(IconType.CHECK);
		panel.add(button);
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getCurrentSelection(new AsyncCallback<VOType>() {

					@Override
					public void onFailure(Throwable caught) {
						throw new RuntimeException(caught);
					}

					@Override
					public void onSuccess(VOType result) {
						closeDialogWithSelection(result);
					}
				});
			}
		});
	}

	protected void closeDialogWithSelection(VOType selection) {
		voSelectHandler.onCallback(selection);
		modal.hide();
	}

	private void createCancelButton(FlowPanel panel) {

		Button button = new Button(MangoClientWeb.getInstance().getMessages().buttonCancel());
		button.setType(ButtonType.DEFAULT);
		button.setIcon(IconType.CLOSE);

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modal.hide();
			}
		});
		panel.add(button);
	}

	public void show() {
		if (modal == null) {
			initDialogBox();
		}

		modal.show();
	}

	protected void initDialogBox() {
		modal = new Modal();
		modal.setTitle(MangoClientWeb.getInstance().getMessages().voSelectionTitle(message));

		ModalBody modalBody = new ModalBody();
		modalBody.add(createDialogBoxContent());
		modal.add(modalBody);

		ModalFooter modalFooter = new ModalFooter();
		createOkButton(modalFooter);
		createCancelButton(modalFooter);

		modal.add(modalFooter);
	}

	public void setVoSelectHandler(SimpleCallback<VOType> voSelectHandler) {
		this.voSelectHandler = voSelectHandler;
	}

	protected abstract void getCurrentSelection(AsyncCallback<VOType> asyncCallback);

	protected abstract Widget createDialogBoxContent();

}
