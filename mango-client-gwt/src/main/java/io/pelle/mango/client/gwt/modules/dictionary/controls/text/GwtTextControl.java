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

import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseControlWithHelp;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.TextControl;
import io.pelle.mango.client.web.util.DictionaryModelUtil;

import com.google.gwt.user.client.ui.TextBox;

public class GwtTextControl extends BaseControlWithHelp<TextBox> implements IGwtControl {

	public GwtTextControl(final TextControl textControl) {
		
		super(new TextBox(), textControl.getModel());

		new ControlHelper(getWidget(), textControl, this, true);
		getWidget().ensureDebugId(DictionaryModelUtil.getDebugId(textControl.getModel()));
		getWidget().setMaxLength(textControl.getModel().getMaxLength());
	}

	public void setContent(Object content) {
		if (content != null) {
			if (content instanceof String) {
				getWidget().setValue((String) content);
			} else {
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		} else {
			getWidget().setValue("");
		}
	}

}
