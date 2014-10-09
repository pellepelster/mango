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

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.BooleanControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import com.google.gwt.user.client.ui.CheckBox;

public class GwtBooleanControl extends CheckBox implements IGwtControl, IControlUpdateListener {

	private BooleanControl control;

	public GwtBooleanControl(BooleanControl control) {
		this.control = control;
		new ControlHelper(this, control, this, true, false);
		ensureDebugId(DictionaryModelUtil.getDebugId(control.getModel()));

		control.addUpdateListener(this);
		onUpdate();
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof Boolean) {
				super.setValue((Boolean) content);
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}

		} else {
			super.setValue(null);
		}
	}

	@Override
	public void onUpdate() {
		setValue(control.getValue());
	}
}
