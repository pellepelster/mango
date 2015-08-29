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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.SingleUploader;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.utils.ActionImage;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

public class GwtFileControl extends Composite implements IGwtControl, ClickHandler, SimpleCallback<Void>, IUpdateListener {

	private final IFileControl fileControl;

	private SingleUploader singleUploader = new SingleUploader(FileInputType.BUTTON);

	private HorizontalPanel panel = new HorizontalPanel();

	private Anchor fileNameAnchor = new Anchor();

	private ActionImage deleteAction = new ActionImage(MangoClientWeb.RESOURCES.delete(), this);

	public GwtFileControl(final IFileControl fileControl, SimpleCallback<Void> deleteCallback) {
		super();

		this.fileControl = fileControl;

		if (deleteCallback != null) {
			deleteAction.setActionCallback(deleteCallback);
		}

		fileControl.addUpdateListener(this);

		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setSpacing(GwtStyles.SPACING);

		panel.add(fileNameAnchor);
		panel.add(deleteAction);

		panel.add(singleUploader.getForm());
		initWidget(panel);

		singleUploader.setAutoSubmit(true);
		singleUploader.setEnabled(true);

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
	}

	public GwtFileControl(final IFileControl fileControl) {
		this(fileControl, null);
	}

	@Override
	public void onClick(ClickEvent event) {
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

		if (fileControl.isDeleteEnabled()) {
			deleteAction.enable();
		} else {
			deleteAction.disable();
		}

		fileNameAnchor.setText(fileControl.getFileName());
		fileNameAnchor.setHref(fileControl.getFileUrl());
	}

	@Override
	public void setContent(Object content) {
	}
}
