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
package io.pelle.mango.client.web.test.sync;

import io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.web.modules.dictionary.IBaseDictionaryModule;
import io.pelle.mango.client.web.test.sync.controls.ControlGroupTest;
import io.pelle.mango.client.web.test.sync.controls.TextControlTest;

public abstract class BaseDictionaryModuleSyncTestUI {

	private IBaseDictionaryModule baseDictionaryModule;

	public BaseDictionaryModuleSyncTestUI(IBaseDictionaryModule baseDictionaryModule, String uiModuleId) {
		super();
		this.baseDictionaryModule = baseDictionaryModule;
	}

	public TextControlTest getTextControlTest(TextControlModel controlModel) {
		return new TextControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public ControlGroupTest getGroupControlTest(ControlGroupModel controlModel) {
		return new ControlGroupTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public boolean isInstanceOf(String moduleUrl) {
		return this.baseDictionaryModule.isInstanceOf(moduleUrl);
	}

	public void updateUrl(String moduleUrl) {
	}
}
