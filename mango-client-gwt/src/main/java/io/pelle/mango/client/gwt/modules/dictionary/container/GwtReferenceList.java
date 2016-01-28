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

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.container.ReferenceList;
import io.pelle.mango.client.web.util.DictionaryModelUtil;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtReferenceList<VOTYPE extends IBaseVO> implements IContainer<Panel>, IUpdateListener {

	private HorizontalDiv panel = new HorizontalDiv();

	private VOTable<VOTYPE> availableVoTable;

	private VOTable<VOTYPE> selectedVoTable;

	private ReferenceList<VOTYPE> referenceList;

	private Button removeButton;

	private Button addButton;

	public GwtReferenceList(final ReferenceList<VOTYPE> referenceList) {

		this.referenceList = referenceList;
		referenceList.addUpdateListener(this);

		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceList.getModel().getDictionaryName());

		List<IBaseControlModel> columns = DictionaryModelUtil.getLabelControlsWithFallback(dictionaryModel);

		availableVoTable = new VOTable<VOTYPE>(columns);
		availableVoTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		availableVoTable.setWidth("25%");
		panel.add(availableVoTable);

		Div buttonDiv = new Div();

		removeButton = new Button("");
		removeButton.setIcon(IconType.ARROW_LEFT);
		removeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				referenceList.removeVOs(availableVoTable.getCurrentSelectionList());
			}
		});
		buttonDiv.add(removeButton);

		addButton = new Button("");
		addButton.setIcon(IconType.ARROW_RIGHT);
		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				referenceList.addVOs(availableVoTable.getCurrentSelectionList());
			}
		});
		buttonDiv.add(addButton);

		panel.add(buttonDiv);

		selectedVoTable = new VOTable<VOTYPE>(columns);
		selectedVoTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		selectedVoTable.setWidth("25%");
		panel.add(selectedVoTable);

		referenceList.updateReferenceList(null);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public void onUpdate() {
		availableVoTable.setContent(referenceList.getAvailableVOs());
		selectedVoTable.setContent(referenceList.getSelectedVOs());
		addButton.setEnabled(referenceList.getAddButton().isEnabled());
		removeButton.setEnabled(referenceList.getRemoveButton().isEnabled());
	}

}
