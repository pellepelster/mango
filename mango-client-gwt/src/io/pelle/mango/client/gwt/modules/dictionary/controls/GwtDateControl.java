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
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.util.GwtUtils;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.DateControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class GwtDateControl extends DateBox implements IGwtControl {

	private final DateControl dateControl;

	public GwtDateControl(final DateControl dateControl) {
		this.dateControl = dateControl;
		ensureDebugId(DictionaryModelUtil.getDebugId(dateControl.getModel()));

		String format = GwtUtils.getFormat(this.dateControl.getModel().getDateFormat()).getPattern();
		setFormat(new DefaultFormat(DateTimeFormat.getFormat(format)));

		new ControlHelper(this.getTextBox(), dateControl, this, true);

		addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				dateControl.setValue(event.getValue());
			}
		});

	}

	/** {@inheritDoc} */
	@Override
	public Widget getWidget() {
		return this;
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof Date) {
				super.setValue((Date) content);
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			// ignore null dates from unparsable inputs
			// super.setValue(null);
		}
	}
	
	@Override
	public void showMessages(IValidationMessages validationMessages) {
	}


}
