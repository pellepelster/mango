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
package io.pelle.mango.client.gwt;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.container.TabFolder;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

public class ColumnLayoutStrategy {

	private final LAYOUT_TYPE layoutType;

	public ColumnLayoutStrategy(LAYOUT_TYPE layoutType) {
		this.layoutType = layoutType;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createLayout(Panel parent, BaseContainerElement<?, ?> baseContainer) {

		if (!baseContainer.getControls().isEmpty()) {
			int colummCount = baseContainer.getColummCount();

			FlexTable flexTable = new FlexTable();
			flexTable.addStyleName(GwtStyles.FORM);
			parent.add(flexTable);

			int currentRow = 0;
			int currentColumn = 0;
			for (BaseDictionaryControl baseControl : baseContainer.getControls()) {
				Widget control = ControlHandler.getInstance().createControl(baseControl, layoutType);

				switch (layoutType) {
				case EDITOR:
					flexTable.setHTML(currentRow, currentColumn * 2, DictionaryModelUtil.getEditorLabel(baseControl.getModel()));
					break;
				case FILTER:
					flexTable.setHTML(currentRow, currentColumn * 2, DictionaryModelUtil.getFilterLabel(baseControl.getModel()));
					break;
				}
				flexTable.setWidget(currentRow, currentColumn * 2 + 1, control);

				if (currentColumn >= colummCount - 1) {
					currentColumn = 0;
					currentRow++;
				} else {
					currentColumn++;
					if (currentColumn > 0) {
						flexTable.getFlexCellFormatter().setStyleName(currentRow, currentColumn * 2, GwtStyles.COLUMN_LAYOUT_HORIZONTAL_SPACING);
					}
				}

			}
		}

		if (!baseContainer.getChildren().isEmpty()) {

			for (BaseContainerElement<?, ?> lBaseContainer : baseContainer.getChildren()) {

				IContainer<Panel> container = ContainerFactory.createContainer(lBaseContainer, this);
				parent.add(container.getContainer());

				if (container.getContainer() instanceof Panel && !(lBaseContainer instanceof BaseTableElement || lBaseContainer instanceof TabFolder)) {
					createLayout(container.getContainer(), lBaseContainer);
				}
			}
		}

	}
}
