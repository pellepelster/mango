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

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ColumnLayoutStrategy
{
	private final LAYOUT_TYPE layoutType;

	public ColumnLayoutStrategy(LAYOUT_TYPE layoutType)
	{
		this.layoutType = layoutType;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createLayout(Panel parent, BaseContainerElement<?> baseContainer)
	{

		if (!baseContainer.getControls().isEmpty())
		{
			FlexTable flexTable = new FlexTable();
			flexTable.setCellSpacing(5);
			parent.add(flexTable);

			int row = 0;
			for (BaseDictionaryControl baseControl : baseContainer.getControls())
			{
				Widget control = ControlHandler.getInstance().createControl(baseControl, layoutType);

				switch (layoutType)
				{
					case EDITOR:
						flexTable.setHTML(row, 0, DictionaryModelUtil.getEditorLabel(baseControl.getModel()));
						break;
					case FILTER:
						flexTable.setHTML(row, 0, DictionaryModelUtil.getFilterLabel(baseControl.getModel()));
						break;
				}
				flexTable.setWidget(row, 1, control);

				row++;
			}
		}

		if (!baseContainer.getChildren().isEmpty())
		{

			for (BaseContainerElement<?> lBaseContainer : baseContainer.getChildren())
			{

				IContainer<Panel> container = ContainerFactory.createContainer(lBaseContainer);
				parent.add(container.getContainer());

				if (container.getContainer() instanceof Panel && !(lBaseContainer instanceof BaseTableElement))
				{
					createLayout(container.getContainer(), lBaseContainer);
				}
			}
		}

	}
}
