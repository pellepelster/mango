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
package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.modules.dictionary.container.ICustomComposite;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.container.IStateContainer;
import io.pelle.mango.client.base.modules.dictionary.model.containers.CustomCompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.FileListModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ReferenceListModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.StateModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.TabFolderModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.FileControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.StateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;
import io.pelle.mango.client.web.test.container.CustomCompositeTestContainer;
import io.pelle.mango.client.web.test.container.FileListTestcontainer;
import io.pelle.mango.client.web.test.container.ReferenceListTestContainer;
import io.pelle.mango.client.web.test.container.StateTestContainer;
import io.pelle.mango.client.web.test.container.TabFolderTestContainer;
import io.pelle.mango.client.web.test.controls.BooleanTestControl;
import io.pelle.mango.client.web.test.controls.ControlGroupTestControl;
import io.pelle.mango.client.web.test.controls.DateTestControl;
import io.pelle.mango.client.web.test.controls.DecimalTestControl;
import io.pelle.mango.client.web.test.controls.EnumerationTestControl;
import io.pelle.mango.client.web.test.controls.FileTestControl;
import io.pelle.mango.client.web.test.controls.IntegerTestControl;
import io.pelle.mango.client.web.test.controls.ReferenceTestControl;
import io.pelle.mango.client.web.test.controls.StateTestControl;
import io.pelle.mango.client.web.test.controls.TextTestControl;
import io.pelle.mango.client.web.test.util.FocusableTestWidget;

public abstract class BaseDictionaryModuleTestUI implements FocusableTestWidget {

	private IBaseDictionaryModule baseDictionaryModule;

	public BaseDictionaryModuleTestUI(IBaseDictionaryModule baseDictionaryModule, String uiModuleId) {
		super();
		this.baseDictionaryModule = baseDictionaryModule;
	}

	public TextTestControl getControl(TextControlModel controlModel) {
		return new TextTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public StateTestControl getControl(StateControlModel controlModel) {
		return new StateTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public FileTestControl getControl(FileControlModel controlModel) {
		return new FileTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public void onResize() {
	}

	public TabFolderTestContainer getContainer(TabFolderModel containerModel) {
		return new TabFolderTestContainer(baseDictionaryModule.getElement(containerModel));
	}

	@SuppressWarnings("unchecked")
	public <VOTYPE extends IBaseVO> ReferenceListTestContainer<VOTYPE> getContainer(ReferenceListModel<VOTYPE> containerModel) {
		return new ReferenceListTestContainer<VOTYPE>(baseDictionaryModule.getElement(containerModel));
	}

	public CustomCompositeTestContainer getContainer(CustomCompositeModel containerModel) {
		return new CustomCompositeTestContainer((ICustomComposite) baseDictionaryModule.getElement(containerModel));
	}

	public StateTestContainer getContainer(StateModel containerModel) {
		return new StateTestContainer((IStateContainer) baseDictionaryModule.getElement(containerModel));
	}

	public FileListTestcontainer getContainer(FileListModel<?> fileListModel) {
		return new FileListTestcontainer((IFileList) baseDictionaryModule.getElement(fileListModel));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <VOType extends IBaseVO> ReferenceTestControl<VOType> getControl(ReferenceControlModel<VOType> controlModel) {
		return new ReferenceTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public BooleanTestControl getControl(BooleanControlModel controlModel) {
		return new BooleanTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public DateTestControl getControl(DateControlModel controlModel) {
		return new DateTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public IntegerTestControl getControl(IntegerControlModel controlModel) {
		return new IntegerTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public DecimalTestControl getControl(BigDecimalControlModel controlModel) {
		return new DecimalTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public <ENUM_TYPE> EnumerationTestControl<ENUM_TYPE> getControl(EnumerationControlModel<ENUM_TYPE> controlModel) {
		return new EnumerationTestControl<ENUM_TYPE>(this.baseDictionaryModule.getElement(controlModel));
	}

	public ControlGroupTestControl getGroupControlTest(ControlGroupModel controlModel) {
		return new ControlGroupTestControl(this.baseDictionaryModule.getElement(controlModel));
	}

	public boolean isInstanceOf(String moduleUrl) {
		return this.baseDictionaryModule.isInstanceOf(moduleUrl);
	}

	public void updateUrl(String moduleUrl) {
	}

	@Override
	public void onFocusEnter() {
	}

	@Override
	public void onFocusLeave() {
	}
}
