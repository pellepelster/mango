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
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.controls.BooleanControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class GwtBooleanFilterControl extends ListBox implements IGwtControl, IControlUpdateListener {

	private final BooleanControl control;

	public GwtBooleanFilterControl(final BooleanControl control) {
		super(false);

		this.control = control;
		control.addUpdateListener(this);
		ensureDebugId(DictionaryModelUtil.getDebugId(control.getModel()));

		new ControlHelper(this, control, this, false);

		addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				if (getSelectedIndex() == 0) {
					control.setValue(null);
				} else if (getSelectedIndex() == 1) {
					control.setValue(true);
				} else if (getSelectedIndex() == 2) {
					control.setValue(false);
				}
			}
		});

		clear();
		addItem("", "");
		addItem(MangoClientWeb.MESSAGES.trueText(), Boolean.TRUE.toString());
		addItem(MangoClientWeb.MESSAGES.falseText(), Boolean.FALSE.toString());

		onUpdate();
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof String) {
				setContent(Boolean.parseBoolean(content.toString()));
			} else if (content instanceof Boolean) {
				if ((Boolean) content) {
					setSelectedIndex(1);
				} else {
					setSelectedIndex(2);
				}
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			setSelectedIndex(0);
		}
	}

	@Override
	public void onUpdate() {
		setContent(control.getValue());
	}

}
