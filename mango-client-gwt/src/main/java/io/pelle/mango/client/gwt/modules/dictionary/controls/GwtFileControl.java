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
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IFileInput;
import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStatusChangedHandler;
import gwtupload.client.SingleUploader;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.gwt.utils.HorizontalSpacer;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.util.DictionaryModelUtil;

public class GwtFileControl extends Div implements IGwtControl, ClickHandler, SimpleCallback<Void>, IUpdateListener {

	private final IFileControl fileControl;

	private SingleUploader singleUploader;

	private Anchor fileNameAnchor = new Anchor();

	private Button addButton = new Button(MangoClientWeb.getInstance().getMessages().addFile());

	private Button deleteButton = new Button(MangoClientWeb.getInstance().getMessages().removeFile());

	public static class FileControlUploadStatus extends BaseUploadStatus {
		public FileControlUploadStatus() {
			setProgressWidget(new FileControlProgressBar());
		}
	}

	public GwtFileControl(final IFileControl fileControl) {
		super();

		this.fileControl = fileControl;
		fileControl.addUpdateListener(this);

		fileNameAnchor.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		HorizontalSpacer.adapt(fileNameAnchor);
		add(fileNameAnchor);

		deleteButton.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		deleteButton.setVisible(false);
		deleteButton.addClickHandler(this);
		HorizontalSpacer.adapt(deleteButton);
		add(deleteButton);

		singleUploader = new SingleUploader(FileInputType.CUSTOM.withInput(IFileInput.FileInputType.CUSTOM.with(addButton).getInstance()), new FileControlUploadStatus());

		// singleUploader.getWidget().addStyleName(ButtonType.DEFAULT.getCssName());
		// singleUploader.getWidget().addStyleName(Styles.BTN);

		singleUploader.getForm().getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		add(singleUploader.getForm());

		singleUploader.setAutoSubmit(true);
		singleUploader.setEnabled(true);
		singleUploader.addOnStatusChangedHandler(new OnStatusChangedHandler() {

			@Override
			public void onStatusChanged(IUploader uploader) {
				switch (uploader.getStatus()) {
				case INPROGRESS:
				case SUBMITING:
				case QUEUED:
					fileNameAnchor.setText(MangoClientWeb.getInstance().getMessages().inProgress());
					break;
				case CANCELED:
				case CANCELING:
					fileNameAnchor.setText(MangoClientWeb.getInstance().getMessages().canceled());
					break;
				case ERROR:
					fileNameAnchor.setText(MangoClientWeb.getInstance().getMessages().failed());
					break;
				default:
					fileNameAnchor.setText(fileControl.getFileName());
					addButton.setVisible(false);
					deleteButton.setVisible(true);
					break;
				}

			}
		});

		singleUploader.setServletPath(GWT.getModuleBaseURL() + "../" + IFileControl.FILE_UPLOAD_BASE_URL + "/" + IFileControl.FILE_UPLOAD_URL);

		new ControlHelper(this, fileControl, this, true, false, true);
		singleUploader.ensureDebugId(DictionaryModelUtil.getDebugId(fileControl.getModel()));

		singleUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader uploader) {
				fileControl.setFileNameUUID(uploader.getFileInput().getFilename(), uploader.getServerMessage().getMessage());
			}
		});

		if (fileControl.hasHook()) {
			fileNameAnchor.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					fileControl.activate();
				}
			});
		}
		onUpdate();
		setWidth("100%");

	}

	@Override
	public void onClick(ClickEvent event) {
		fileControl.delete();
	}

	@Override
	public void showMessages(IValidationMessages validationMessages) {
	}

	@Override
	public void onCallback(Void t) {
		fileControl.delete();
	}

	@Override
	public void onUpdate() {

		deleteButton.setVisible(fileControl.isDeleteEnabled());
		
		fileNameAnchor.setVisible(!fileControl.getFileName().isEmpty());
		fileNameAnchor.setText(fileControl.getFileName());
		fileNameAnchor.setHref(fileControl.getFileUrl());
	}

	@Override
	public void setContent(Object content) {
	}
}
