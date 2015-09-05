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

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

public interface IGwtControlFactory<ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>> {

	<VOType extends IBaseVO> Column<IBaseTable.ITableRow<VOType>, ?> createColumn(ControlType baseControl, boolean editable, ListDataProvider<IBaseTable.ITableRow<VOType>> listDataProvider, IMangoCellTable<VOType> mangoCellTable);

	Widget createControl(ControlType baseControl, LAYOUT_TYPE layoutType);

	boolean supports(BaseDictionaryControl<?, ?> baseControl);

	boolean supports(IBaseControlModel baseControlModel);

	<VOType extends IBaseVO> Column<VOType, ?> createColumn(IBaseControlModel baseControlModel);

	<VOType extends IBaseVO> Header<?> createHeader(IBaseControlModel baseControlModel);

}