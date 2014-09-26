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
package io.pelle.mango.client.web.test.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IGroupControl;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.HierarchicalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BaseControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BigDecimalControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BooleanControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.ControlGroupTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.DateControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.EnumerationControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.HierarchicalControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.IntegerControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.ReferenceControlTest;
import io.pelle.mango.client.web.test.modules.dictionary.controls.TextControlTest;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public abstract class BaseDictionaryModuleTestUI {

	private IBaseDictionaryModule baseDictionaryModule;

	public BaseDictionaryModuleTestUI(IBaseDictionaryModule baseDictionaryModule, String uiModuleId) {
		super();
		this.baseDictionaryModule = baseDictionaryModule;
	}

	public <ElementType extends IBaseControl<Value, ?>, Value extends Object> BaseControlTest<ElementType, Value> getBaseControlTestElement(BaseControlModel<ElementType> baseControlModel) {
		return new BaseControlTest<ElementType, Value>(this.baseDictionaryModule.getElement(baseControlModel));
	}

	public TextControlTest getTextControlTest(TextControlModel controlModel) {
		return new TextControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public ControlGroupTest getControlGroupTest(ControlGroupModel controlModel) {
		return new ControlGroupTest((IGroupControl) this.baseDictionaryModule.getElement(controlModel));
	}

	public BigDecimalControlTest getBigDecimalControlTest(BigDecimalControlModel controlModel) {
		return new BigDecimalControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public BooleanControlTest getBooleanControlTest(BooleanControlModel controlModel) {
		return new BooleanControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public DateControlTest getDateControlTest(DateControlModel controlModel) {
		return new DateControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public EnumerationControlTest getEnumerationControlTest(EnumerationControlModel controlModel) {
		return new EnumerationControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public HierarchicalControlTest getHierarchicalControlTest(HierarchicalControlModel controlModel) {
		return new HierarchicalControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public IntegerControlTest getIntegerControlTest(IntegerControlModel controlModel) {
		return new IntegerControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTest<ReferenceVOType> getReferenceControlTest(ReferenceControlModel<ReferenceVOType> controlModel) {
		return new ReferenceControlTest<ReferenceVOType>(this.baseDictionaryModule.getElement(controlModel));
	}

	public boolean isInstanceOf(String moduleUrl) {
		return this.baseDictionaryModule.isInstanceOf(moduleUrl);
	}

	public void updateUrl(String moduleUrl) {
	}
}
