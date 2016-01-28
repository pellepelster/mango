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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.IMangoCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BooleanControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.EnumerationControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.FileControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.GroupControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.HierarchicalControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.IGwtControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.ReferenceControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.numbers.BigDecimalControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.numbers.IntegerControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.state.StateControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.text.TextControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.time.DateControlFactory;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

public class ControlHandler {

	private static ControlHandler instance;

	private static List<IGwtControlFactory<?, ?>> controlFactories = new ArrayList<IGwtControlFactory<?, ?>>();

	private ControlHandler() {
		super();
		controlFactories.add(new TextControlFactory());
		controlFactories.add(new IntegerControlFactory());
		controlFactories.add(new DateControlFactory());
		controlFactories.add(new BooleanControlFactory());
		controlFactories.add(new GroupControlFactory());
		controlFactories.add(new EnumerationControlFactory());
		controlFactories.add(new ReferenceControlFactory());
		controlFactories.add(new BigDecimalControlFactory());
		controlFactories.add(new HierarchicalControlFactory());
		controlFactories.add(new FileControlFactory());
		controlFactories.add(new StateControlFactory());
	}

	public static ControlHandler getInstance() {
		if (instance == null) {
			instance = new ControlHandler();
		}

		return instance;
	}

	public <ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>, VOType extends IBaseVO> Column<IBaseTable.ITableRow<VOType>, ?> createColumn(ControlType baseControl, boolean editable,
			ListDataProvider<IBaseTable.ITableRow<VOType>> listDataProvider, IMangoCellTable<VOType> mangoCellTable) {
		return getControlFactory(baseControl).createColumn(baseControl, editable, listDataProvider, mangoCellTable);
	}

	public <VOType extends IBaseVO> Column<VOType, ?> createColumn(IBaseControlModel baseControlModel) {
		return getControlFactory(baseControlModel).createColumn(baseControlModel);
	}

	public <VOType extends IBaseVO> Header<?> createHeader(IBaseControlModel baseControlModel) {
		return getControlFactory(baseControlModel).createHeader(baseControlModel);
	}

	public <ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>, VOType extends IBaseVO> Widget createControl(ControlType baseControl, LAYOUT_TYPE layoutType) {
		return getControlFactory(baseControl).createControl(baseControl, layoutType);
	}

	@SuppressWarnings("unchecked")
	private <ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>, VOType extends IBaseVO> IGwtControlFactory<ControlModelType, ControlType> getControlFactory(
			BaseDictionaryControl<ControlModelType, ?> baseControl) {

		for (IGwtControlFactory<?, ?> controlFactory : controlFactories) {
			if (controlFactory.supports(baseControl)) {
				return (IGwtControlFactory<ControlModelType, ControlType>) controlFactory;
			}
		}

		throw new RuntimeException("unsupported control type '" + baseControl.getModel().getClass().getName() + "'");
	}

	@SuppressWarnings("unchecked")
	private <ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>, VOType extends IBaseVO> IGwtControlFactory<ControlModelType, ControlType> getControlFactory(
			IBaseControlModel baseControlModel) {

		for (IGwtControlFactory<?, ?> controlFactory : controlFactories) {
			if (controlFactory.supports(baseControlModel)) {
				return (IGwtControlFactory<ControlModelType, ControlType>) controlFactory;
			}
		}

		throw new RuntimeException("unsupported control model '" + baseControlModel.getClass().getName() + "'");
	}

}
