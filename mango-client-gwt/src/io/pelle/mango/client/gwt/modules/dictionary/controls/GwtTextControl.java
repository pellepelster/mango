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
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.TextControl;

import com.google.gwt.user.client.ui.TextBox;

public class GwtTextControl extends TextBox implements IGwtControl
{

	public GwtTextControl(TextControl textControl)
	{
		super();
		new ControlHelper(this, textControl, this, true);

		ensureDebugId(DictionaryModelUtil.getDebugId(textControl.getModel()));
		setMaxLength(textControl.getModel().getMaxLength());
	}

	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof String)
			{
				super.setValue((String) content);
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			super.setValue("");
		}
	}

}
