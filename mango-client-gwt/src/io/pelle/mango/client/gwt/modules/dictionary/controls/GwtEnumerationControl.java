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

import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IEnumerationControl;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.EnumerationControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class GwtEnumerationControl extends ListBox implements IGwtControl, IControlUpdateListener {

	private final EnumerationControl<?> control;

	public GwtEnumerationControl(final EnumerationControl<?> control) {
		super(false);

		this.control = control;
		control.addUpdateListener(this);
		ensureDebugId(DictionaryModelUtil.getDebugId(control.getModel()));

		new ControlHelper(this, control, this, false);

		addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				control.parseValue(getEnumForSelection());
			}
		});

		clear();
		addItem("", "");
		for (Map.Entry<String, String> enumEntry : control.getEnumerationMap().entrySet()) {
			addItem(enumEntry.getValue(), enumEntry.getKey());
		}

		onUpdate();
	}

	public static String getEnumForText(IEnumerationControl<?> enumarationControl, String enumText) {

		if (enumText == null || enumText.isEmpty()) {
			return null;
		}

		for (Map.Entry<String, String> enumEntry : enumarationControl.getEnumerationMap().entrySet()) {
			if (enumEntry.getValue().equals(enumText)) {
				return enumEntry.getKey();
			}
		}

		throw new RuntimeException("no enum found for text '" + enumText + "'");
	}

	private String getEnumForSelection() {
		String enumValue = getValue(getSelectedIndex());
		return enumValue;
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof String || content instanceof Enum<?>) {
				for (int i = 0; i < getItemCount(); i++) {
					if (getValue(i).equals(content.toString())) {
						setSelectedIndex(i);
					}
				}
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			super.setSelectedIndex(0);
		}
	}

	@Override
	public void onUpdate() {
		setContent(control.getValue());
	}
	
	@Override
	public void showMessages(IValidationMessages validationMessages) {
	}


}
