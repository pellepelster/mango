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

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlGroup;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import com.google.gwt.user.client.ui.TextBox;

public class GwtGroupControl extends TextBox implements IGwtControl {

	public GwtGroupControl(ControlGroup control) {
		new ControlHelper(this, control, this, true, false);
		ensureDebugId(DictionaryModelUtil.getDebugId(control.getModel()));

	}

	@Override
	public void setContent(Object content) {
	}
}
