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

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IControlGroupModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlGroup;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class GroupControlFactory extends BaseControlFactory<IControlGroupModel, ControlGroup> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(ControlGroup control, LAYOUT_TYPE layoutType) {
		return new GwtGroupControl(control);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControlModel) {
		return baseControlModel instanceof ControlGroup;
	}

	@Override
	public <VOType extends IBaseVO> Column<ITableRow<VOType>, ?> createColumn(final ControlGroup control, boolean editable, ListDataProvider<ITableRow<VOType>> listDataProvider, IMangoCellTable<VOType> mangoCellTable) {
		return super.createColumn(control, editable, listDataProvider, mangoCellTable);
	}

}
