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
package io.pelle.mango.client.gwt.modules.dictionary.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.base.modules.dictionary.IListUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.gwt.modules.dictionary.controls.GwtFileControl;
import io.pelle.mango.client.web.modules.dictionary.container.FileList;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtFileList extends Div implements IContainer<Panel>, IListUpdateListener<IFileControl> {

	private ListGroup listGroup = new ListGroup();

	private Div buttonContainer = new Div();

	private Map<IFileControl, ListGroupItem> m = new HashMap<>();

	private FileList fileList;

	public GwtFileList(final FileList fileList) {

		this.fileList = fileList;

		add(listGroup);
		add(buttonContainer);

		Button button = new Button("add");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fileList.addNewFile();
			}
		});
		buttonContainer.add(button);

		fileList.addUpdateListener(this);
		onUpdate();
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return this;
	}

	@Override
	public void onUpdate() {

		for (int i = 0; i < listGroup.getWidgetCount(); i++) {
			listGroup.remove(i);
		}

		for (IFileControl fileControl : fileList.getFileControls()) {
			createListGroupItem(fileControl);
		}

	}

	private void createListGroupItem(IFileControl fileControl) {

		final ListGroupItem listGroupItem = new ListGroupItem();

		GwtFileControl gwtFileControl = new GwtFileControl(fileControl, new SimpleCallback<Void>() {

			@Override
			public void onCallback(Void t) {
				listGroup.remove(listGroupItem);
			}
		});

		listGroupItem.add(gwtFileControl);

		listGroup.add(listGroupItem);
		m.put(fileControl, listGroupItem);
	}

	@Override
	public void onAdded(Collection<IFileControl> added) {
		for (IFileControl fileControl : added) {
			createListGroupItem(fileControl);
		}
	}

	@Override
	public void onRemoved(Collection<IFileControl> removed) {
		for (IFileControl fileControl : removed) {
			if (m.containsKey(fileControl)) {
				listGroup.remove(m.get(fileControl));
				m.remove(fileControl);
			}
		}
	}

}
