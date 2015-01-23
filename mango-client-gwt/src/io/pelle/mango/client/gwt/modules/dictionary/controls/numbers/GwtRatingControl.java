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
package io.pelle.mango.client.gwt.modules.dictionary.controls.numbers;

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IntegerControl;
import io.pelle.mango.gwt.commons.rating.FullRatingWidget;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class GwtRatingControl extends FullRatingWidget implements IGwtControl {

	public GwtRatingControl(final IntegerControl integerControl) {
		new ControlHelper(this, integerControl, this, false, false, false);
		ensureDebugId(DictionaryModelUtil.getDebugId(integerControl.getModel()));

		addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				integerControl.setValue(event.getValue());
			}
		});
	}

	@Override
	public void setContent(Object content) {
		if (content != null) {

			if (content instanceof Integer) {
				setValue((Integer) content);
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}

		} else {
			super.setValue(null);
		}
	}

}
