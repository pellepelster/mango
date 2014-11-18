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
package io.pelle.mango.client.gwt.modules.dictionary.controls.text;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ITextControlModel;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.ReadonlyControl;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.TextControl;

import com.google.gwt.user.client.ui.Widget;

public class TextControlFactory extends BaseControlFactory<ITextControlModel, TextControl> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(TextControl textControl, LAYOUT_TYPE layoutType) {
		if (textControl.getModel().isReadonly()) {
			return new ReadonlyControl(textControl);
		} else {
			return new GwtTextControl(textControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl) {
		return baseControl instanceof TextControl;
	}

}
